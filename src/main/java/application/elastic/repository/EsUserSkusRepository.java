package application.elastic.repository;

import application.elastic.UserSkusDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/23.
 */
public interface EsUserSkusRepository extends CustomElasticsearchRepository<UserSkusDocument, String> {
}
