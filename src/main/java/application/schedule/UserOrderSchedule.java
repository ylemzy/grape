package application.schedule;


import application.service.UserOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/2/15.
 */
@Service
public class UserOrderSchedule {


    @Autowired
    UserOrderService userOrderService;

    @Scheduled(cron = "${jobs.fetch.schedule.mergeUserOrder.cron}")
    public void scanOrder(){
        Timestamp to = new Timestamp(System.currentTimeMillis());
        long l = TimeUnit.DAYS.toMillis(1);
        Timestamp from = new Timestamp(System.currentTimeMillis() - l);
        userOrderService.saveUserOrder(from, to);
    }
}
