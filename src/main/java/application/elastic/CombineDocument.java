package application.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzebin on 2017/2/20.
 */
@Document(indexName = "user-combine", type = "1")
public class CombineDocument extends HashMap<String, Object> {

    @Id
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CombineDocument(){}

    public CombineDocument(Map<String, Object> map){
        super(map);
    }

}
