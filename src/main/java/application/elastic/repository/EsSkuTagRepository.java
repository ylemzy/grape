package application.elastic.repository;

import application.elastic.TagSkuDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/22.
 */
public interface EsSkuTagRepository extends CustomElasticsearchRepository<TagSkuDocument, String> {
}
