package application.jdbc.dao;

import application.jdbc.bean.Order;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/14.
 */
public interface OrderDao {

    List<Order> getOrder(Timestamp from, Timestamp to);
}
