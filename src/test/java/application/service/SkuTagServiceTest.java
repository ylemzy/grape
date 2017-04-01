package application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by huangzebin on 2017/2/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkuTagServiceTest {

    @Autowired
    SkuTagService skuTagService;

    @Test
    public void fetchfromUserSkus() throws Exception {
        //14719539941570200 20
        skuTagService.fetchFromUserSku("14719539941570200");
    }

    @Test
    public void fetchfromSku() throws Exception {
        skuTagService.updateSkuTagFromSku();
    }

    @Test
    public void udpateAll(){
        skuTagService.updateSkuTagAllFromUserSku();
    }

}