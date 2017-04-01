package application.elastic.repository;

import application.elastic.CombineDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/20.
 */
public interface EsCombineDataRepository extends CustomElasticsearchRepository<CombineDocument, String> {

}
