package application.elastic.repository;

import application.elastic.InsureProductDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/24.
 */
public interface EsInsureProductRepository extends CustomElasticsearchRepository<InsureProductDocument, String> {
}
