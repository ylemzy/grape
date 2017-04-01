package application.service;


import application.elastic.OrderBase;
import application.elastic.UserOrderBaseDocument;
import application.jdbc.bean.Isu;
import application.jdbc.bean.Order;
import application.jdbc.bean.User;
import application.jdbc.dao.IsuDaoImpl;
import application.jdbc.dao.OrderDao;
import application.jdbc.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by huangzebin on 2017/2/14.
 */
@Service
public class JpaUserOrderService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    IsuDaoImpl isuDao;

    public Map<String, UserOrderBaseDocument> getUserOrder(Timestamp from, Timestamp to){
        //get order
        List<Order> orderList = orderDao.getOrder(from, to);

        Set<String> userIds = new HashSet<>();

        List<String> orderIds = new ArrayList<>();
        for (Order order : orderList) {
            userIds.add(order.getUserId());
            orderIds.add(order.getId());
        }

        List<String> ts = new ArrayList<>();
        ts.addAll(userIds);

        //get user
        List<User> userList = userDao.getUser(ts);

        //combine order and user
        Map<String, UserOrderBaseDocument> userOrderBases = new HashMap<>();
        for (User user : userList) {
            UserOrderBaseDocument userOrderBaseDocument = new UserOrderBaseDocument();
            userOrderBaseDocument.setUser(user);
            userOrderBases.put(user.getId(), userOrderBaseDocument);
        }

        //get order isu
        List<Isu> isuList = isuDao.getIsu(orderIds);
        Map<String, Isu> isuMap = new HashMap<>();
        for (Isu isu : isuList) {
            isuMap.put(isu.getOrdId(), isu);//assert one isu, one order
        }

        //map order isu to user
        for (Order order : orderList){
            UserOrderBaseDocument userOrderBaseDocument = userOrderBases.get(order.getUserId());

            try{
                Isu isu = isuMap.get(order.getId());
                OrderBase e = new OrderBase(order, isu.getIsuSsn());
                e.setStartTime(isu.getStartTime());
                e.setEndTime(isu.getEndTime());
                userOrderBaseDocument.getOrderBase().add(e);

            }catch (Exception e){
                logger.error("Error at user key {}", order.getUserId());
            }

            //userOrderBases.put(order.getUserId(), userOrderBaseDocument);
        }

        logger.info("The total user-order between {} - {} is {}", from, to, userOrderBases.size());

        return userOrderBases;
    }

}
