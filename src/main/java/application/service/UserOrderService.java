package application.service;

import application.elastic.SkuDetailDocument;
import application.elastic.UserActionDocument;
import application.elastic.UserOrderBaseDocument;
import application.elastic.UserSkusDocument;
import application.elastic.repository.*;
import application.elastic.repository.base.ScrollCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by huangzebin on 2017/2/21.
 */
@Service
public class UserOrderService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    JpaUserOrderService jpaUserOrderService;

    @Autowired
    EsUserOrderRepository esUserOrderRepository;

    @Autowired
    EsSkuRepository esSkuRepository;

    @Autowired
    EsUserSkusRepository esUserSkusRepository;

    @Autowired
    EsUserActionRepository esUserActionRepository;

    //时间间隔不要太长，数据多时需要换方式，防止内存爆了
    public void saveUserOrder(Timestamp from, Timestamp to){
        logger.info("Save user order between {} and {}", from, to);
        Map<String, UserOrderBaseDocument> userOrder = jpaUserOrderService.getUserOrder(from, to);
        esUserOrderRepository.save(userOrder.values());
    }

    public void updateUserAction(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0, 1000))
                .build();

        ScrollCallback<UserSkusDocument> skusScrollCallback = page -> {
            //id collection
            List<String> ids = new ArrayList<>();
            page.getContent().forEach(user -> ids.add(user.getOpenId()));

            Iterable<UserActionDocument> all = esUserActionRepository.findAll(ids);
            Map<String, UserActionDocument> actionMap = new HashMap<>();
            all.forEach(d-> actionMap.put(d.getOpenid(), d));

            page.getContent().forEach(user->{
                UserActionDocument userActionDocument = actionMap.get(user.getOpenId());
                if (userActionDocument != null){
                    user.setDevice(userActionDocument.getDevice());
                    user.setUrl(userActionDocument.getUlr());
                    user.setParameters(userActionDocument.getParameters());
                }
            });
            esUserSkusRepository.save(page.getContent());
        };

        esUserSkusRepository.scroll(searchQuery, 5000L, false, skusScrollCallback);
    }

    public void updateUserSku(){
        //Loop 1,search user-orders by condition and return page, get the sku ids,

        //loop1 page -> skus collection,  search skuids from loop1 by page able,
        ScrollCallback<UserOrderBaseDocument> scrollCallback = page -> {
            logger.info("search match_all total_page = {}, current_page = {}, page_size = {}", page.getTotalPages(), page.getNumber(), page.getSize());

            //id collection
            List<String> ids = new ArrayList<>();
            page.getContent().forEach(b -> b.getOrderBase().forEach(d -> ids.add(d.getSkuId())));

            Iterable<SkuDetailDocument> all = esSkuRepository.findAll(ids);
            Map<String, SkuDetailDocument> skuMap = new HashMap<>();
            all.forEach(d-> skuMap.put(d.getSkuId(), d));

            //merge data
            List<UserSkusDocument> userSkuseDocuments = new ArrayList<>();
            page.getContent().forEach(b -> {

                if (!Strings.isNullOrEmpty(b.getWeixinOpenid())){
                    UserSkusDocument userSkusDocument = new UserSkusDocument();
                    userSkusDocument.setOpenId(b.getWeixinOpenid());
                    userSkusDocument.setUserOrderBaseDocument(b);
                    List<SkuDetailDocument> userSkuDates = new ArrayList<>();
                    b.getOrderBase().forEach(d -> {
                        SkuDetailDocument skuDetailDocument = skuMap.get(d.getSkuId());
                        userSkuDates.add(skuDetailDocument);
                    });
                    userSkusDocument.setSkuDetailDocuments(userSkuDates);

                    userSkuseDocuments.add(userSkusDocument);
                }
            });

            //save
            esUserSkusRepository.save(userSkuseDocuments);
        };

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0, 1000))
                .build();
        esUserOrderRepository.scroll(searchQuery, 5000L, false, scrollCallback);
    }
}
