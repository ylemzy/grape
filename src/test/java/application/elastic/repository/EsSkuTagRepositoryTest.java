package application.elastic.repository;

import application.elastic.TagSkuDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.JsonHelper;

/**
 * Created by huangzebin on 2017/2/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsSkuTagRepositoryTest {

    @Autowired
    EsSkuTagRepository esSkuTagRepository;

    @Test
    public void testSkuTag(){
        TagSkuDocument one = esSkuTagRepository.findOne("14659919988810200");
        System.out.println(JsonHelper.toJSON(one));
    }
}