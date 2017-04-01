package application.elastic.repository;

import application.elastic.UserActionDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/17.
 */
public interface EsUserActionRepository extends CustomElasticsearchRepository<UserActionDocument, String> {

}
