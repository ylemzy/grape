package application.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzebin on 2017/2/23.
 */
@Document(indexName = "user-skus", type = "1")
public class UserSkusDocument {

    @Id
    String openId;

    String url;

    String regSource;

    Device device;

    Map<String, String> parameters;

    UserOrderBaseDocument userOrderBaseDocument;

    List<SkuDetailDocument> skuDetailDocuments;


    public UserSkusDocument(){
        skuDetailDocuments = new ArrayList<>();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public UserOrderBaseDocument getUserOrderBaseDocument() {
        return userOrderBaseDocument;
    }

    public void setUserOrderBaseDocument(UserOrderBaseDocument userOrderBaseDocument) {
        this.userOrderBaseDocument = userOrderBaseDocument;
    }

    public List<SkuDetailDocument> getSkuDetailDocuments() {
        return skuDetailDocuments;
    }

    public void setSkuDetailDocuments(List<SkuDetailDocument> skuDetailDocuments) {
        this.skuDetailDocuments = skuDetailDocuments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
