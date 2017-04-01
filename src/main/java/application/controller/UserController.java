package application.controller;

import application.AppProperties;
import application.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * Created by huangzebin on 2017/2/21.
 */
@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    UserActionService userActionService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    CombineDataService combineDataService;

    @Autowired
    EsInsureProductService insureProductService;

    @Autowired
    SkuService skuService;

    @Autowired
    SkuTagService skuTagService;

    @Autowired
    UserTagService userTagService;

    @RequestMapping("/index")
    public String index() {
        return "You known, for user-*";
    }

    @Autowired
    AppProperties appProperties;


    @RequestMapping("/task/combine")
    public String taskCombine() {
        logger.info("start taskCombine");

        try {
            userActionService.parseFilesByDirectory(appProperties.parseDataDirectory);

            combineDataService.combineUserAction();
            //combineDataService.scrollUserOrder();
        } catch (Exception e) {
            logger.error(e, e);
        }
        return "ok";
    }


    @RequestMapping("/task/sku")
    public String taskMergeUserSku() {
        logger.info("start taskCombine");

        try {
            userOrderService.saveUserOrder(Timestamp.valueOf("2015-11-11 0:0:0"), Timestamp.valueOf("2017-11-11 23:0:0"));
            insureProductService.updateInsureProduct();
            skuService.saveSkuData();

            userOrderService.updateUserSku();
            userOrderService.updateUserAction();
        } catch (Exception e) {
            logger.error(e, e);
        }
        return "ok";
    }


    @RequestMapping("/task/tag")
    public String taskAllSku() {
        logger.info("start updateAllSku");

        try {
            skuTagService.updateSkuTagFromSku();
            skuTagService.updateSkuTagAllFromUserSku();
            userTagService.updateUsertags();
        } catch (Exception e) {
            logger.error(e, e);
        }
        return "ok";
    }

    @RequestMapping("/task/all")
    public String taskAll() {
        logger.info("start taskAll");
        try {
            taskCombine();
            taskMergeUserSku();
            //taskAllSku();
        } catch (Exception e) {
            logger.error(e, e);
        }
        return "ok";
    }

}