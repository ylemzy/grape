package application.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/2.
 */
@Document(indexName = "news", type = "1")
public class NewsDocument {

    @Id
    String id;

    String webId;

    String title;

    String mediaFrom;

    String publishTime;

    String url;

    String html;

    String descShort;

    List<String> keywords;

    String recommendProduct;

    String recommendProductName;

    String recommendRule;

    String firstImg;

    String htbxThumbnail;

    boolean show;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaFrom() {
        return mediaFrom;
    }

    public void setMediaFrom(String mediaFrom) {
        this.mediaFrom = mediaFrom;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void addKeyWord(String keyword){
        if (this.keywords == null)
            this.keywords = new ArrayList<>();
        this.keywords.add(keyword);
    }

    public String getRecommendProduct() {
        return recommendProduct;
    }

    public void setRecommendProduct(String recommendProduct) {
        this.recommendProduct = recommendProduct;
    }

    public String getRecommendProductName() {
        return recommendProductName;
    }

    public void setRecommendProductName(String recommendProductName) {
        this.recommendProductName = recommendProductName;
    }

    public String getRecommendRule() {
        return recommendRule;
    }

    public void setRecommendRule(String recommendRule) {
        this.recommendRule = recommendRule;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getDescShort() {
        return descShort;
    }

    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getHtbxThumbnail() {
        return htbxThumbnail;
    }

    public void setHtbxThumbnail(String htbxThumbnail) {
        this.htbxThumbnail = htbxThumbnail;
    }
}
