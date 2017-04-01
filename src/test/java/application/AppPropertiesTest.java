package application;

import application.elastic.TagSkuDocument;
import application.elastic.repository.EsSkuTagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppPropertiesTest {

    @Autowired
    AppProperties sourceProperties;

    @Autowired
    EsSkuTagRepository esSkuTagRepository;


    @Test
    public void testSourse(){
        String parseDataDirectory = sourceProperties.parseDataDirectory;
        System.out.println(parseDataDirectory);
    }


    @Test
    public void test(){
        List<TagSkuDocument> tagSkuDocuments = new ArrayList<>();
        Iterable<TagSkuDocument> all = esSkuTagRepository.findAll();
        all.forEach(d->{
            tagSkuDocuments.add(d);
        });

        System.out.println(JsonHelper.toJSON(tagSkuDocuments));
    }
}