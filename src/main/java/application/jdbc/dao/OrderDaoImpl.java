package application.jdbc.dao;

import application.jdbc.bean.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/14.
 */
@Transactional
@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String queryOrder = "select O.ord_aid, O.cust_aid, O.ord_date, O.ord_status, O.tax_title," +
            " D.sku_aid, D.item_count, D.price_list_org, D.sku_name_alt, D.sku_type " +
            "from tc_order O, tc_order_detail D WHERE O.ord_aid = D.ord_aid and O.ord_date BETWEEN ? and ?";

    @Override
    public List<Order> getOrder(Timestamp from, Timestamp to) {
        return jdbcTemplate.query(queryOrder, new Object[]{from, to}, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet resultSet, int i) throws SQLException {

                Order order = new Order();
                order.setId(resultSet.getString("O.ord_aid"));
                order.setUserId(resultSet.getString("O.cust_aid"));
                order.setOrdDate(resultSet.getTimestamp("O.ord_date"));
                //status

                order.setStatus(resultSet.getString("O.ord_status"));
                order.setTaxTitle(resultSet.getString("O.tax_title"));
                order.setSkuId(resultSet.getString("D.sku_aid"));
                order.setItemCount(resultSet.getInt("D.item_count"));
                order.setPrice(resultSet.getInt("D.price_list_org"));
                order.setSkuName(resultSet.getString("D.sku_name_alt"));
                order.setSkuType(resultSet.getString("D.sku_type"));
                return order;
            }
        });
    }
}
