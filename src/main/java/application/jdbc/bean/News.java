package application.jdbc.bean;

import application.elastic.NewsDocument;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by huangzebin on 2017/3/9.
 */
public class News implements Serializable{
    private static final long serialVersionUID = -404889811348548626L;
    String newsId;
    int sortNo;
    int newShow;
    String img;
    String newsType;
    String newsSource;
    String content;
    String title;
    String descShort;
    String skuId;
    int saCnt;
    int isRecommend;
    int infoType;
    Timestamp createTime;
    String currentUser;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getNewShow() {
        return newShow;
    }

    public void setNewShow(int newShow) {
        this.newShow = newShow;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescShort() {
        return descShort;
    }

    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }

    public int getSaCnt() {
        return saCnt;
    }

    public void setSaCnt(int saCnt) {
        this.saCnt = saCnt;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public static News make(NewsDocument newsDocument){
        RandomUtils.nextInt(1000);
        News news = new News();
        news.setNewsId("" + System.currentTimeMillis() + RandomUtils.nextInt(10000));
        news.setSortNo(1);
        news.setNewShow(2);//2 暂时针对移动端
        news.setNewsType("1");
        news.setNewsSource(newsDocument.getMediaFrom());
        news.setContent(newsDocument.getHtml());
        news.setTitle(newsDocument.getTitle());
        news.setDescShort(newsDocument.getDescShort());

        if (Strings.isBlank(newsDocument.getFirstImg())){
            news.setImg("sys_img/14793744574242458_M_B.JPG");
        }else{
            news.setImg(newsDocument.getFirstImg());
        }

        news.setSkuId(newsDocument.getRecommendProduct());
        news.setSaCnt(0);
        news.setIsRecommend(2);
        news.setInfoType(1);
        news.setCurrentUser("spider");
        return news;
    }
}
