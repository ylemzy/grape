package application.jdbc.dao;

import application.jdbc.bean.InsureProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/24.
 */
@Transactional
@Repository
public class InsureProductDaoImpl implements InsureProductDao{


    @Autowired
    JdbcTemplate jdbcTemplate;

    static final String query = "select prod_aid, cat_aid, buy_vendor, prod_name, seo_key, isu_age_max, isu_age_min, pc_slogan, pc_good_point, prod_tag from buy_prod_insure";

    RowMapper<InsureProduct> rowMapper = (resultSet, i) -> {
        InsureProduct insureProduct = new InsureProduct();
        insureProduct.setProductId(resultSet.getString("prod_aid"));
        insureProduct.setCatId(resultSet.getString("cat_aid"));
        insureProduct.setBuyVendor(resultSet.getString("buy_vendor"));
        insureProduct.setProductName(resultSet.getString("prod_name"));
        insureProduct.setSeoKey(resultSet.getString("seo_key"));
        insureProduct.setIsuAgeMin(resultSet.getInt("isu_age_min"));
        insureProduct.setIsuAgeMax(resultSet.getInt("isu_age_max"));
        insureProduct.setPcSlogan(resultSet.getString("pc_slogan"));
        insureProduct.setPcGoodPoint(resultSet.getString("pc_good_point"));
        insureProduct.setTag(resultSet.getString("prod_tag"));
        return insureProduct;
    };

    @Override
    public List<InsureProduct> getInsureProduct() {
        return jdbcTemplate.query(query, rowMapper);
    }
}
