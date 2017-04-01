package application.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by huangzebin on 2017/2/22.
 */
@Document(indexName = "sku-detail", type = "1")
public class SkuDetailDocument {

    @Id
    String skuId;
    String productId;

    String skuNameAlt;
    int price;

    String skuType;

    String sku3;

    long updateTime;


    InsureProductDocument insureProductDocument;

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

    public String getSkuNameAlt() {
        return skuNameAlt;
    }

    public void setSkuNameAlt(String skuNameAlt) {
        this.skuNameAlt = skuNameAlt;
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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public InsureProductDocument getInsureProductDocument() {
        return insureProductDocument;
    }

    public void setInsureProductDocument(InsureProductDocument insureProductDocument) {
        this.insureProductDocument = insureProductDocument;
    }
}
