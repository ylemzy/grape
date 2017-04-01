package application.service;

import application.AppProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.Settings;

/**
 * Created by huangzebin on 2017/2/20.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserActionDocumentServiceTest {
    @Autowired
    UserActionService userActionService;

    @Autowired
    AppProperties appProperties;

    @Test
    public void test(){
        userActionService.parseFilesByDirectory(appProperties.parseDataDirectory);
        //System.out.println(parserService);
    }
}