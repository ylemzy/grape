package application.jdbc.dao;

import application.jdbc.bean.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by huangzebin on 2017/2/22.
 */
@Transactional
@Repository
public class SkuDaoImpl implements SkuDao{


    @Autowired
    JdbcTemplate jdbcTemplate;

    String queryAll = "SELECT sku_aid, prod_aid, sku_name_alt, sku_price_amt, sku_attr3, sku_type  from buy_sku ";

    String idCondition = "where prod_aid = ?";

    RowMapper<Sku> rowMapper = (resultSet, i) -> {
        Sku sku = new Sku();
        sku.setSkuId(resultSet.getString("sku_aid"));
        sku.setProductId(resultSet.getString("prod_aid"));
        sku.setSkuNamealt(resultSet.getString("sku_name_alt"));
        sku.setPrice(resultSet.getInt("sku_price_amt"));
        sku.setSku3(resultSet.getString("sku_attr3"));
        sku.setSkuType(resultSet.getString("sku_type"));
        return sku;
    };

    @Override
    public List<Sku> getAllSkus() {
        return jdbcTemplate.query(queryAll, rowMapper);
    }

    @Override
    public List<Sku> getSkuByProductId(String productId) {
        return jdbcTemplate.query(queryAll + idCondition, new Object[]{productId}, rowMapper);
    }
}
