package application.service;

import application.elastic.CombineDocument;
import application.elastic.UserActionDocument;
import application.elastic.repository.EsCombineDataRepository;
import application.elastic.repository.EsUserActionRepository;
import application.elastic.repository.base.ScrollCallback;
import org.apache.logging.log4j.LogManager;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by huangzebin on 2017/2/20.
 */
@Service
public class CombineDataService {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    @Autowired
    EsCombineDataRepository esCombineDataRepository;

    @Autowired
    EsUserActionRepository userActionRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    public void combineUserAction(){

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0,1000))
                .build();

        ScrollCallback<UserActionDocument> scrollCallback = page -> {
            List<CombineDocument> datas = new ArrayList<>();
            page.getContent().forEach(action -> {
                CombineDocument data = new CombineDocument();
                data.setId(action.getOpenid());
                data.put("url", action.getUlr());
                data.put("device", action.getDevice());
                datas.add(data);
            });

            esCombineDataRepository.update(datas);
        };

        userActionRepository.scroll(searchQuery, 5000L, false, scrollCallback);
    }


    public void scrollUserOrder(){
        logger.info("scroll User Order");
        int pageSize = 1000;
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("user-order")
                .withTypes("1")
                .withPageable(new PageRequest(0, pageSize))
                .build();

        String scrollId = elasticsearchTemplate.scan(searchQuery, pageSize, false);
        while (true){
            Page<Map> page = elasticsearchTemplate.scroll(scrollId, 5000L, Map.class);

            if (!page.hasContent())
                break;

            List<CombineDocument> sampleEntities = new ArrayList<>();
            page.getContent().forEach(map -> {
                CombineDocument data = new CombineDocument(map);
                Object weixinOpenid = map.get("weixinOpenid");
                if (weixinOpenid != null && !Strings.isNullOrEmpty(weixinOpenid.toString())){
                    data.setId(weixinOpenid.toString());
                    sampleEntities.add(data);
                }else{
                    logger.error("User Order: user id = {}, weixinOpenid is null", data.getId());
                }
            });

            esCombineDataRepository.update(sampleEntities);

        }
        elasticsearchTemplate.clearScroll(scrollId);
    }
}
