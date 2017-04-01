package application.elastic;

import application.elastic.tag.Tags;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by huangzebin on 2017/2/23.
 */
@Document(indexName = "tag-user", type = "1")
public class TagUserDocument extends Tags{

    @Id
    String userId; //openId


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
