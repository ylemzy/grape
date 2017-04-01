package application.el;

import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorType;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Map;

/**
 * Created by huangzebin on 2017/3/9.
 */
public class AviatorQuery extends AviatorObject{

    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

    @Override
    public int compare(AviatorObject aviatorObject, Map<String, Object> map) {
        return 0;
    }

    @Override
    public AviatorType getAviatorType() {
        return AviatorType.JavaType;
    }

    @Override
    public Object getValue(Map<String, Object> map) {
        return getBoolQueryBuilder();
    }

    public BoolQueryBuilder getBoolQueryBuilder(){
        return boolQueryBuilder;
    }
}
