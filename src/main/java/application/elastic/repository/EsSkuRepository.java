package application.elastic.repository;

import application.elastic.SkuDetailDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/22.
 */

public interface EsSkuRepository extends CustomElasticsearchRepository<SkuDetailDocument, String> {
}
