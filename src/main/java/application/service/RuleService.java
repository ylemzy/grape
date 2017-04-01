package application.service;

import application.el.EsAnd;
import application.el.EsOr;
import application.elastic.NewsDocument;
import application.elastic.RuleProductDocument;
import application.elastic.repository.EsRuleProductRepository;
import application.elastic.repository.NewsRepository;
import com.googlecode.aviator.AviatorEvaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/8.
 */
@Service
public class RuleService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    EsRuleProductRepository esRuleProductRepository;

/*    @Autowired
    RestClient restClient;*/

    public Iterable<NewsDocument> findNewsWithRule(String productId){

        RuleProductDocument rule = esRuleProductRepository.findOne(productId);
        if (rule == null)
            return null;

        return newsRepository.search((QueryBuilder) AviatorEvaluator.execute(rule.getRule()));
    }

    public void saveRules(RuleProductDocument ruleProductDocument){
        esRuleProductRepository.save(ruleProductDocument);
    }


    /**通过rule再找到匹配的news
     * @return <String>productId = ruleId</String>, <object>array of news</object>
     */
    public Map<String, Iterable<NewsDocument>> findNewsWithRule(Iterable<RuleProductDocument> ruleProductDocumentIterable){

        AviatorEvaluator.addFunction(new EsAnd());
        AviatorEvaluator.addFunction(new EsOr());

        Map<String, Iterable<NewsDocument>> res = new HashMap<>();
        ruleProductDocumentIterable.forEach(r->{

            Iterable<NewsDocument> search = newsRepository.search((QueryBuilder) AviatorEvaluator.execute(r.getRule()));
            res.put(r.getId(), search);

        });
        return res;
    }
}
