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
public class EsOr extends AbstractVariadicFunction {
    @Override
    public AviatorObject variadicCall(Map<String, Object> map, AviatorObject... aviatorObjects) {
        AviatorQuery query = new AviatorQuery();

        for (AviatorObject aviatorObject : aviatorObjects) {
            switch (aviatorObject.getAviatorType()){
                case String:
                    List<QueryBuilder> build = LexemeQueryBuilder.build(((AviatorString) (aviatorObject)).getLexeme());
                    build.forEach(b->{
                        query.getBoolQueryBuilder().should(b);
                    });
                case JavaType:
                    if (aviatorObject instanceof AviatorQuery){
                        query.getBoolQueryBuilder().should(((AviatorQuery)aviatorObject).getBoolQueryBuilder());
                    }
                    break;
                default:
                    break;
            }
        }

        return query;
    }

    @Override
    public String getName() {
        return "or";
    }
}
