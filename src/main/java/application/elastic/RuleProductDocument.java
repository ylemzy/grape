package application.elastic;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;

/**
 * Created by huangzebin on 2017/3/8.
 */
@Document(indexName = "rule-product", type = "1")
public class RuleProductDocument {
    @Id
    String id;

    String name;

    String rule;

    Map query;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Map getQuery() {
        return query;
    }

    public void setQuery(Map query) {
        this.query = query;
    }
}
