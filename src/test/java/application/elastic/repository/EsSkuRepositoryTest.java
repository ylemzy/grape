package application.elastic.repository;

import application.elastic.SkuDetailDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;
import util.JsonHelper;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by huangzebin on 2017/2/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsSkuRepositoryTest {

    @Autowired
    EsSkuRepository esSkuRepository;

    @Test
    public void test(){

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(termQuery("skuId", "14749766505580200"))
                .withPageable(new PageRequest(0, 1000))
                .build();


        Page<SkuDetailDocument> skuPage = esSkuRepository.search(searchQuery);
        System.out.println(JsonHelper.toJSON(skuPage.getContent()));
    }



}