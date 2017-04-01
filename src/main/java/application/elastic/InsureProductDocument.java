package application.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by huangzebin on 2017/2/24.
 */
@Document(indexName = "product-insure", type = "1")
public class InsureProductDocument {

    @Id
    String productId;

    String catId;

    String buyVendor;

    String productName;

    int isuAgeMax;
    int isuAgeMin;

    String pcSlogan;
    String pcGoodPoint;
    String seoKey;
    String tag;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getBuyVendor() {
        return buyVendor;
    }

    public void setBuyVendor(String buyVendor) {
        this.buyVendor = buyVendor;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getIsuAgeMax() {
        return isuAgeMax;
    }

    public void setIsuAgeMax(int isuAgeMax) {
        this.isuAgeMax = isuAgeMax;
    }

    public int getIsuAgeMin() {
        return isuAgeMin;
    }

    public void setIsuAgeMin(int isuAgeMin) {
        this.isuAgeMin = isuAgeMin;
    }

    public String getPcSlogan() {
        return pcSlogan;
    }

    public void setPcSlogan(String pcSlogan) {
        this.pcSlogan = pcSlogan;
    }

    public String getPcGoodPoint() {
        return pcGoodPoint;
    }

    public void setPcGoodPoint(String pcGoodPoint) {
        this.pcGoodPoint = pcGoodPoint;
    }

    public String getSeoKey() {
        return seoKey;
    }

    public void setSeoKey(String seoKey) {
        this.seoKey = seoKey;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
