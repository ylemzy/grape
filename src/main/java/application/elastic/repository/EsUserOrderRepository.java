package application.elastic.repository;

import application.elastic.UserOrderBaseDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/17.
 */
public interface EsUserOrderRepository extends CustomElasticsearchRepository<UserOrderBaseDocument, String> {
}
