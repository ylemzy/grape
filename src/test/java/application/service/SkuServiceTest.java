package application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by huangzebin on 2017/2/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkuServiceTest {

    @Autowired
    SkuService skuService;

    @Test
    public void test(){
        skuService.saveSkuData();
    }
}