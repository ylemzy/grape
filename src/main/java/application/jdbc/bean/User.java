package application.jdbc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by huangzebin on 2017/2/13.
 */

public class User implements Serializable{

    private static final long serialVersionUID = -2735220811655543566L;
    String custPhone;

    String custSsn;

    String weixinOpenid;

    String id;

    String registerSource;

    Timestamp regTime;


    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustSsn() {
        return custSsn;
    }

    public void setCustSsn(String custSsn) {
        this.custSsn = custSsn;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }
}
