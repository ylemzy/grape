package application.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

/**
 * Created by huangzebin on 2017/2/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserOrderScheduleTest {

    @Autowired
    UserOrderSchedule userOrderSchedule;

    @Test
    public void testSaveUserOrder(){

    }
}