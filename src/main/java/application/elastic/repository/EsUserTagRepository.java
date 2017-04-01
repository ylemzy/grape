package application.elastic.repository;

import application.elastic.TagUserDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/2/27.
 */
public interface EsUserTagRepository extends CustomElasticsearchRepository<TagUserDocument, String> {
}
