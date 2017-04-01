package application.service;

import application.AppProperties;
import application.elastic.InsureProductDocument;
import application.elastic.NewsDocument;
import application.elastic.RuleProductDocument;
import application.elastic.repository.EsInsureProductRepository;
import application.elastic.repository.EsRuleProductRepository;
import application.elastic.repository.EsSkuRepository;
import application.elastic.repository.NewsRepository;
import application.elastic.repository.base.ScrollCallback;
import application.jdbc.bean.News;
import application.jdbc.dao.NewsDaoImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import util.ImageDownloadUtil;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by huangzebin on 2017/3/9.
 */
@Service
public class NewsService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    RuleService ruleService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    SkuService skuService;

    @Autowired
    EsSkuRepository esSkuRepository;

    @Autowired
    EsInsureProductRepository esInsureProductRepository;

    @Autowired
    AppProperties appProperties;

    @Autowired
    EsRuleProductRepository esRuleProductRepository;

    @Autowired
    NewsDaoImpl newsDao;

    private void linkToNews(Collection<InsureProductDocument> insureProductDocuments) {

        Map<String, InsureProductDocument> productDocumentMap = new HashMap<>();
        insureProductDocuments.forEach(product -> productDocumentMap.put(product.getProductId(), product));

        Iterable<RuleProductDocument> rules = esRuleProductRepository.findAll(productDocumentMap.keySet());
        Map<String, RuleProductDocument> ruleMap = new HashMap<>();
        rules.forEach(rule -> ruleMap.put(rule.getId(), rule));

        Map<String, Iterable<NewsDocument>> newsMap = ruleService.findNewsWithRule(rules);//productId, news

        Collection<NewsDocument> updates = new ArrayList<>();
        newsMap.forEach( (k, v) -> {
            try {
                v.forEach(newsDocument -> {
                            newsDocument.setRecommendProduct(k);
                            newsDocument.setRecommendProductName(productDocumentMap.get(k).getProductName());
                            newsDocument.setRecommendRule(ruleMap.get(k).getRule());
                            newsDocument.setShow(true);
                            updates.add(newsDocument);
                        }
                );
            } catch (Exception e) {
                logger.error(e, e);
            }
        });

        if (!CollectionUtils.isEmpty(updates)) {
            newsRepository.save(updates);
        }

        ImageDownloadUtil.runDownload(updates, newsRepository);

    }

/*    private void downloadImage(Collection<NewsDocument> documents){
        documents.forEach(doc->{
            try {
                String localUrl = ImageDownloadUtil.downloadToSystemFile(doc.getFirstImg(), "news", 240, 216);
                if (StringUtils.isNotEmpty(localUrl)){
                    doc.setHtbxThumbnail(localUrl);
                }
            } catch (Exception e) {
                logger.error(e, e);
            }
        });
    }*/

    public void linkRecommendProductToNews() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0, 1000))
                .build();

        ScrollCallback<InsureProductDocument> scrollCallback = page -> linkToNews(page.getContent());

        esInsureProductRepository.scroll(searchQuery, 5000L, false, scrollCallback);
    }

    public Page<NewsDocument> searchLinkedByShow(Pageable page){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(termQuery("show", true))
                .withPageable(page)
                .build();

        return newsRepository.search(searchQuery);
    }


    public NewsDocument getNews(String id){
        return newsRepository.findOne(id);
    }


    public void setShow(String id, boolean show){
        NewsDocument one = newsRepository.findOne(id);
        one.setShow(show);
        newsRepository.update(one);
    }

    public void enableShow(String webId, boolean enable){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("webId", webId))
                .build();
        ScrollCallback<NewsDocument> scrollCallback = page -> {
            page.forEach(doc-> doc.setShow(enable));

            newsRepository.save(page.getContent());
        };
        newsRepository.scroll(searchQuery, 5000L, false, scrollCallback);
    }

    /*public void writeToBusiness() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(existsQuery("recommendProduct"))
                .withPageable(new PageRequest(0, 1000))
                .build();
        ScrollCallback<NewsDocument> scrollCallback = page -> {

            try {
                List<NewsDocument> updateWriteStatuList = new ArrayList<>();
                page.getContent().forEach(d -> {
                    if (!d.isWriteBusiness()) {
                        logger.info(JsonHelper.toJSON(d));
                        d.setWriteBusiness(true);
                        updateWriteStatuList.add(d);
                    }
                });

                if (!CollectionUtils.isEmpty(updateWriteStatuList)) {
                    write(updateWriteStatuList);
                    newsRepository.save(updateWriteStatuList);
                }

            } catch (Exception e) {
                logger.error(e, e);
            }

        };
        newsRepository.scroll(searchQuery, 5000L, false, scrollCallback);
    }*/

    /*public void rewriteBusiness() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(termQuery("writeBusiness", true))
                .withPageable(new PageRequest(0, 1000))
                .build();
        ScrollCallback<NewsDocument> scrollCallback = page -> {
            try {
                List<NewsDocument> writeList = new ArrayList<>();
                page.getContent().forEach(d -> {
                    if (d.isWriteBusiness()) {
                        writeList.add(d);
                    }
                });

                if (!CollectionUtils.isEmpty(writeList)) {
                    write(writeList);
                }

            } catch (Exception e) {
                logger.error(e, e);
            }

        };
        newsRepository.scroll(searchQuery, 5000L, false, scrollCallback);
    }*/

    public void keepLastFew(){
        List<News> newses = newsDao.queryActiveNews();

        Map<String, List<News>> aggs = new HashMap<>();
        newses.forEach(news ->{
            List<News> agg = aggs.get(news.getNewsSource());
            if (agg == null){
                agg = new ArrayList<>();
                aggs.put(news.getNewsSource(), agg);
            }

            agg.add(news);
        });

        if (aggs.size() == 0)
            return;

        //desc order by create date
        aggs.values().forEach(
                agg-> agg.sort((o1, o2) -> {
                    long time1 = o1.getCreateTime().getTime();
                    long time2 = o2.getCreateTime().getTime();
                    if (time1 == time2)
                        return 0;
                    else
                        return time1 > time2 ? -1 : 1;
                })
        );

        int avg = appProperties.newsMostNewestLimit / aggs.size();

        List<News> willDelete = new ArrayList<>();
        aggs.values().forEach(agg->{
            for (int i = avg; i < agg.size(); ++i){
                agg.get(i).setNewsType("2");
                willDelete.add(agg.get(i));
            }
        });

        newsDao.updateNews(willDelete);
    }

   /* private void write(List<NewsDocument> newsDocumentList) {
        List<News> result = new ArrayList<>();

        newsDocumentList.forEach(newsDoc -> {
            News news = News.make(newsDoc);
            try {
                String img = UploadUtil.uploadImage(news.getImg(), null);
                if (StringUtils.isNotEmpty(img))
                    news.setImg(img);
                result.add(news);
            } catch (Exception e) {
                logger.error(e, e);
            }
        });
        newsDao.linkRecommendProductToNews(result);
    }*/
}
