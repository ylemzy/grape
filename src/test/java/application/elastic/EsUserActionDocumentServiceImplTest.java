package application.elastic;

import application.service.EsUserActionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.JsonHelper;

/**
 * Created by huangzebin on 2017/2/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsUserActionDocumentServiceImplTest {

    @Autowired
    EsUserActionService esUserActionService;

    @Test
    public void findUserAction() throws Exception {
        UserActionDocument userActionDocument = esUserActionService.findUserAction("o_ZzQs1kv2Two_rEruBsL5URe9sA");
        System.out.println(JsonHelper.toJSON(userActionDocument));
    }
}