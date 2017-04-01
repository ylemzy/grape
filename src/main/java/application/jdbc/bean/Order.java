package application.jdbc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by huangzebin on 2017/2/14.
 */
public class Order implements Serializable{
    private static final long serialVersionUID = -3992826996766729528L;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
