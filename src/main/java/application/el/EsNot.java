package application.el;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/9.
 */
public class EsNot extends AbstractVariadicFunction {

    @Override
    public String getName() {
        return "not";
    }

    @Override
    public AviatorObject variadicCall(Map<String, Object> map, AviatorObject... aviatorObjects) {
        AviatorQuery query = new AviatorQuery();

        for (AviatorObject aviatorObject : aviatorObjects) {
            switch (aviatorObject.getAviatorType()){
                case String:
                    List<QueryBuilder> build = LexemeQueryBuilder.build(((AviatorString) (aviatorObject)).getLexeme());
                    build.forEach(b->{
                        query.getBoolQueryBuilder().mustNot(b);
                    });
                case JavaType:
                    if (aviatorObject instanceof AviatorQuery){
                        query.getBoolQueryBuilder().mustNot(((AviatorQuery)aviatorObject).getBoolQueryBuilder());
                    }
                    break;
                default:
                    break;
            }
        }

        return query;
    }
}
