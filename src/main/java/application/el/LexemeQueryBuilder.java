package application.el;/**
 * Created by huangzebin on 2017/3/15.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class LexemeQueryBuilder {
    private static final Logger logger = LogManager.getLogger();

    public static List<QueryBuilder> build(String lexeme){
        if (StringUtils.isEmpty(lexeme))
            return null;

        List<QueryBuilder> queryBuilderList = new ArrayList<>();
        String[] split = lexeme.split(";");

        for (String s : split) {
            String[] kv = s.split(":");
            Assert.isTrue(kv.length == 2);
            String key = StringUtils.trim(kv[0]);
            String[] values = kv[1].split(",");
            for (String value : values) {
                String trim = StringUtils.trim(value);
                if (!StringUtils.isEmpty(trim)){
                    queryBuilderList.add(QueryBuilders.matchPhraseQuery(key, trim));
                }

            }

        }

        return queryBuilderList;
    }
}
