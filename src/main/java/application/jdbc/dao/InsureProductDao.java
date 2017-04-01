package application.jdbc.dao;

import application.jdbc.bean.InsureProduct;

import java.util.List;

/**
 * Created by huangzebin on 2017/2/24.
 */
public interface InsureProductDao {
    List<InsureProduct> getInsureProduct();
}
