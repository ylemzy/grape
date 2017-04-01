package application.elastic;


import application.jdbc.bean.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import util.IdcardUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/13.
 */
@Document(indexName = "user-order", type = "1")
public class UserOrderBaseDocument {

    @Id
    String id;

    String custPhone;

    String custSsn;

    String weixinOpenid;


    Timestamp regTime;

    String job;
    String gender;
    Integer age;
    String regSource;

    List<OrderBase> orderBase;

    public UserOrderBaseDocument() {
        orderBase = new ArrayList<>();
    }


    public void setUser(User user){
        this.setId(user.getId());
        this.setRegTime(user.getRegTime());
        this.setCustSsn(user.getCustSsn());
        this.setCustPhone(user.getCustPhone());
        this.setWeixinOpenid(user.getWeixinOpenid());
        this.setRegSource(user.getRegisterSource());
    }

    public List<OrderBase> getOrderBase() {
        return orderBase;
    }

    public void setOrderBase(List<OrderBase> orderBase) {
        this.orderBase = orderBase;
    }

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

        if (IdcardUtils.validateCard(custSsn)){
            this.setAge(IdcardUtils.getAge(custSsn));
            this.setGender(IdcardUtils.getGender(custSsn));
        }

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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }
}
