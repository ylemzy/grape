package application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/2/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsInsureProductServiceTest {

    @Autowired
    EsInsureProductService esInsureProductService;

    @Test
    public void updateInsureProduct() throws Exception {
        esInsureProductService.updateInsureProduct();
    }

}