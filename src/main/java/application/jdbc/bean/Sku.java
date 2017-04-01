package application.jdbc.bean;

import java.io.Serializable;

/**
 * Created by huangzebin on 2017/2/22.
 */
public class Sku implements Serializable{

    private static final long serialVersionUID = 8979487935695849352L;
    String skuId;
    String productId;


    String skuNamealt;
    int price;

    String skuType;

    String sku3;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSkuNamealt() {
        return skuNamealt;
    }

    public void setSkuNamealt(String skuNamealt) {
        this.skuNamealt = skuNamealt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getSku3() {
        return sku3;
    }

    public void setSku3(String sku3) {
        this.sku3 = sku3;
    }
}
