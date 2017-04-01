package application.elastic.repository;


import application.elastic.RuleProductDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/3/8.
 */
public interface EsRuleProductRepository extends CustomElasticsearchRepository<RuleProductDocument, String> {
}
