package application.service;

import application.elastic.UserOrderBaseDocument;
import application.elastic.repository.EsUserOrderRepository;
import application.elastic.repository.base.ScrollCallback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by huangzebin on 2017/2/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserOrderServiceTest {

    @Autowired
    UserOrderService userOrderService;

    @Test
    public void updateUserSku() throws Exception {
        userOrderService.updateUserSku();
    }

    @Autowired
    EsUserOrderRepository esUserOrderRepository;

    int count = 0;

    @Test
    public void testScrollUserOrder() {


        ScrollCallback<UserOrderBaseDocument> scrollCallback = page -> {
            count += page.getContent().size();

        };
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0, 1000))
                .build();
        esUserOrderRepository.scroll(searchQuery, 5000L, false, scrollCallback);

        System.out.println("total count = " + count);
    }

}