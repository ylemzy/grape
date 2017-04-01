package application.jdbc.dao;

import application.jdbc.bean.Sku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.JsonHelper;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/2/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkuDaoImplTest {

    @Autowired
    SkuDao skuDao;

    @Test
    public void testGetAll(){
        List<Sku> allSkus = skuDao.getAllSkus();
        System.out.println(JsonHelper.toJSON(allSkus));
    }

    @Test
    public void testGetByProductId(){
        List<Sku> skuByProductId = skuDao.getSkuByProductId("1483543981390020");
        System.out.println(JsonHelper.toJSON(skuByProductId));
    }
}