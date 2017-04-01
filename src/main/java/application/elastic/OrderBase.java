package application.elastic;



import application.jdbc.bean.Order;
import util.IdcardUtils;


import java.sql.Timestamp;

/**
 * Created by huangzebin on 2017/2/13.
 */
public class OrderBase {

    private String id;

    private String skuId;

    private String userId;

    private String detailId;

    private String status;

    private int price;

    private int itemCount;

    private Timestamp ordDate;

    private String taxTitle;

    private String skuType;

    private String skuName;

    private String isuSsn;

    private String gender;

    private Integer age;

    private Timestamp startTime;

    private Timestamp endTime;

    public OrderBase() {
    }

    public OrderBase(Order order, String isuSsn){
        this.id = order.getId();
        this.skuId = order.getSkuId();
        this.userId = order.getUserId();
        this.detailId = order.getDetailId();
        this.status = order.getStatus();
        this.price = order.getPrice();
        this.itemCount = order.getItemCount();
        this.ordDate = order.getOrdDate();
        this.taxTitle = order.getTaxTitle();
        this.skuType = order.getSkuType();
        this.skuName = order.getSkuName();
        this.isuSsn = isuSsn;

        if (IdcardUtils.validateCard(isuSsn)){
            this.setAge(IdcardUtils.getAge(isuSsn));
            this.setGender(IdcardUtils.getGender(isuSsn));
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Timestamp getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(Timestamp ordDate) {
        this.ordDate = ordDate;
    }

    public String getTaxTitle() {
        return taxTitle;
    }

    public void setTaxTitle(String taxTitle) {
        this.taxTitle = taxTitle;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getIsuSsn() {
        return isuSsn;
    }

    public void setIsuSsn(String isuSsn) {
        this.isuSsn = isuSsn;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
