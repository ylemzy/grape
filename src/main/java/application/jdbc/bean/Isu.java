package application.jdbc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by huangzebin on 2017/2/14.
 */
public class Isu implements Serializable{

    private static final long serialVersionUID = 5031557460630223857L;
    private String id;

    private String ordId;

    private String isuSsn;

    private Timestamp startTime;

    private Timestamp endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public String getIsuSsn() {
        return isuSsn;
    }

    public void setIsuSsn(String isuSsn) {
        this.isuSsn = isuSsn;
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
