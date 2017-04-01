package application.elastic;


import application.el.AviatorQuery;
import application.el.EsAnd;
import application.el.EsOr;
import com.google.common.base.Charsets;
import com.googlecode.aviator.AviatorEvaluator;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import util.JsonHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/8.
 */
public class RuleLoader {

    public List<RuleProductDocument> load() throws IOException {
        InputStream ruleStream = RuleLoader.class.getResourceAsStream("/rule_product");
        String s = IOUtils.toString(ruleStream, Charsets.UTF_8);
        List<RuleProductDocument> rules = JsonHelper.toList(s, RuleProductDocument.class);
        AviatorEvaluator.addFunction(new EsAnd());
        AviatorEvaluator.addFunction(new EsOr());
        rules.forEach(rule->{
            Object execute = AviatorEvaluator.execute(rule.getRule());
            rule.setQuery(JsonHelper.toMap(execute.toString()));
        });
        return rules;
    }
}
