package application.service;

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
public class CombineDocumentServiceTest {

    @Autowired
    CombineDataService combineDataService;

    @Test
    public void testScrollUserAction(){
        combineDataService.combineUserAction();
    }


    @Test
    public void testScrollUserOrder(){
        combineDataService.scrollUserOrder();
    }


}