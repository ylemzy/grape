package application.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;

/**
 * Created by huangzebin on 2017/2/8.
 */
@Document(indexName = "user-action", type = "1")
public class UserActionDocument {
    @Id
    String openid;

    String ip;

    String ulr;

    String useragent;

    Map<String, String> parameters;

    Device device;

    String netType;

    String microMessenger;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUlr() {
        return ulr;
    }

    public void setUlr(String ulr) {
        this.ulr = ulr;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getMicroMessenger() {
        return microMessenger;
    }

    public void setMicroMessenger(String microMessenger) {
        this.microMessenger = microMessenger;
    }
}
