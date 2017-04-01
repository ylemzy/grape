package application.controller;/**
 * Created by huangzebin on 2017/3/10.
 */

import application.elastic.NewsDocument;
import application.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.PageUtil;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/news")
public class NewsController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    NewsService newsService;


    @RequestMapping(value = "/update")
    public ResponseEntity newsUpdate(){

        try {
            newsService.linkRecommendProductToNews();
            return ResponseEntity.ok("update news");
        } catch (Exception e) {
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/newest")
    public void keepNewest(){
        try {
            newsService.keepLastFew();
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getNewestNewsRecommend(
                                                    @RequestParam int pageNum,
                                                    @RequestParam int pageSize){

        try{
            Page<NewsDocument> newsDocuments = newsService.searchLinkedByShow(PageUtil.pageRequest(pageNum, pageSize));

            if (newsDocuments.getTotalElements() == 0){
                logger.info("No news found!");
                return ResponseEntity.noContent().build();
            }

            newsDocuments.getContent().forEach(doc->{
                doc.setHtml(null);
                doc.setRecommendRule(null);
            });

            return ResponseEntity.ok(PageUtil.toPageInfo(newsDocuments));
        }catch (Exception e){
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/get")
    public ResponseEntity getNews(@RequestParam String newsId){

        try{
            NewsDocument news = newsService.getNews(newsId);
            if (news != null){
                news.setRecommendRule(null);
            }
            return ResponseEntity.ok(news);
        }catch (Exception e){
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/show")
    public ResponseEntity setShow(@RequestParam String newsId, @RequestParam  boolean show){
        try{
            newsService.setShow(newsId, show);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @RequestMapping(value = "/enable")
    public ResponseEntity activeWeb(@RequestParam String webId, @RequestParam boolean enable){
        try{
            newsService.enableShow(webId, enable);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
