package application.elastic.repository;


import application.elastic.NewsDocument;
import application.elastic.repository.base.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/3/3.
 */
public interface NewsRepository extends CustomElasticsearchRepository<NewsDocument, String> {
}
