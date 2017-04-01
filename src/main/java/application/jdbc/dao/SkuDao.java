package application.jdbc.dao;

import application.jdbc.bean.Sku;

import java.util.List;

/**
 * Created by huangzebin on 2017/2/22.
 */
public interface SkuDao {

    List<Sku> getAllSkus();

    List<Sku> getSkuByProductId(String productId);
}
