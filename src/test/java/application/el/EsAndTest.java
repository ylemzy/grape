package application.el;

import com.googlecode.aviator.AviatorEvaluator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/3/15.
 */
public class EsAndTest {

    @Test
    public void test2(){
        String exp = "and(\"A:b\")";

        String query = "and(or('title:化疗,重疾'), or('title:少儿,儿童'))";

        AviatorEvaluator.addFunction(new EsAnd());
        AviatorEvaluator.addFunction(new EsOr());


        BoolQueryBuilder boolQueryBuilder = (BoolQueryBuilder)AviatorEvaluator.execute(query);

        System.out.println(boolQueryBuilder.toString());
    }
}