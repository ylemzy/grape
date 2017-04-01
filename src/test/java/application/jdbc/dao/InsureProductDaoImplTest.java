package application.jdbc.dao;

import application.jdbc.bean.InsureProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.JsonHelper;

import java.util.List;

/**
 * Created by huangzebin on 2017/2/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InsureProductDaoImplTest {

    @Autowired
    InsureProductDao insureProductDao;

    @Test
    public void testGetInsureProductDao(){
        List<InsureProduct> insureProduct = insureProductDao.getInsureProduct();
        System.out.println(JsonHelper.toJSON(insureProduct));
    }
}