package application.service;

import application.AppProperties;
import application.elastic.UserSkusDocument;
import application.elastic.TagUserDocument;
import application.elastic.repository.EsUserSkusRepository;
import application.elastic.repository.EsUserTagRepository;
import application.elastic.repository.base.ScrollCallback;
import application.elastic.tag.filter.TagFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import util.BatchSaver;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by huangzebin on 2017/2/27.
 */

@Service
public class UserTagService {

    @Autowired
    AppProperties appProperties;

    @Autowired
    EsUserTagRepository esUserTagRepository;


    @Autowired
    EsUserSkusRepository esUserSkusRepository;

    public void updateUsertags(){

        BatchSaver<EsUserSkusRepository, TagUserDocument> batchSaver = new BatchSaver(esUserTagRepository, appProperties.esBatchSize);

        ScrollCallback<UserSkusDocument> scrollCallback = page -> {
            page.getContent().forEach(sku->{
                TagUserDocument tagUserDocument = TagFilter.filterUser(sku, null);
                batchSaver.save(tagUserDocument);
            });
            batchSaver.finish();
        };

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0, 1000))
                .build();
        esUserSkusRepository.scroll(searchQuery, 5000L, false, scrollCallback);
    }

}
