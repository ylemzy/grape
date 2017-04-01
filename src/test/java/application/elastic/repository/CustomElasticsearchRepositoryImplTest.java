package application.elastic.repository;

import application.elastic.CombineDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by huangzebin on 2017/2/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomElasticsearchRepositoryImplTest {


    @Autowired
    EsCombineDataRepository esCombineDataRepository;

    @Test
    public void update() throws Exception {

        CombineDocument data1 = new CombineDocument();
        data1.put("price", "88");
        data1.put("shopAid", "abcd");
        data1.setId("o_ZzQs3uron1skRv73sDF0WpgHYc9");

        esCombineDataRepository.update(data1);


        CombineDocument data2 = new CombineDocument();
        data2.put("product", "milk");
        data2.put("num", "2");
        data2.setId("o_ZzQs3uron1skRv73sDF0WpgHYc9");

        esCombineDataRepository.update(data2);

    }

    @Test
    public void update1() throws Exception {

    }

}