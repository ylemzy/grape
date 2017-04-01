package application.schedule;/**
 * Created by huangzebin on 2017/3/10.
 */

import application.service.EsInsureProductService;
import application.service.SkuService;
import application.service.UserOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SkuSchedule {
    private static final Logger logger = LogManager.getLogger();


    @Autowired
    UserOrderService userOrderService;

    @Autowired
    EsInsureProductService insureProductService;

    @Autowired
    SkuService skuService;

    @Scheduled(cron = "${jobs.fetch.schedule.mergeSku.cron}")
    public void mergeSku(){

        logger.info("Start merge sku");
        try {
            userOrderService.saveUserOrder(Timestamp.valueOf("2015-11-11 0:0:0"), Timestamp.valueOf("2017-11-11 23:0:0"));
            insureProductService.updateInsureProduct();
            skuService.saveSkuData();

            userOrderService.updateUserSku();
            userOrderService.updateUserAction();
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
}
