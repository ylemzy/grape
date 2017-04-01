package application.elastic;

import application.elastic.tag.Tags;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by huangzebin on 2017/2/22.
 */
@Document(indexName = "tag-sku", type = "1")
public class TagSkuDocument extends Tags {
    @Id
    String skuId;

    String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
