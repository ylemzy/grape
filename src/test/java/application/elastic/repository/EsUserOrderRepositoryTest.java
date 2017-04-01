package application.elastic.repository;

import application.elastic.UserOrderBaseDocument;
import application.service.JpaUserOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by huangzebin on 2017/2/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsUserOrderRepositoryTest {

    @Autowired
    EsUserOrderRepository esUserOrderRepository;


    @Autowired
    JpaUserOrderService jpaUserOrderService;

    @Test
    public void test(){
        Map<String, UserOrderBaseDocument> userOrder = jpaUserOrderService.getUserOrder(
                Timestamp.valueOf("2015-11-11 0:0:0"), Timestamp.valueOf("2017-11-11 23:0:0"));
    }

}