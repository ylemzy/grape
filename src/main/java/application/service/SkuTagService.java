package application.service;

import application.elastic.SkuDetailDocument;
import application.elastic.TagSkuDocument;
import application.elastic.repository.EsSkuRepository;
import application.elastic.repository.EsSkuTagRepository;
import application.elastic.repository.EsUserSkusRepository;
import application.elastic.repository.base.ScrollCallback;
import application.elastic.tag.Tag;
import application.elastic.tag.TagType;
import application.elastic.tag.filter.TagFilter;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import util.BatchSaver;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by huangzebin on 2017/2/24.
 */
@Service
public class SkuTagService {

    @Autowired
    EsSkuRepository esSkuRepository;

    @Autowired
    EsUserSkusRepository esUserSkusRepository;

    @Autowired
    EsSkuTagRepository esSkuTagRepository;

    public void fetchFromUserSku(String skuId){
        TermQueryBuilder query = QueryBuilders.termQuery("skuDetails.skuId", skuId);
        TermsBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("userOrderBase.orderBase.age");
        Aggregations aggregation = esUserSkusRepository.aggregation(query, ageAgg);
        Terms termsAgg = aggregation.get("ageAgg");

        TagSkuDocument data = esSkuTagRepository.findOne(skuId);

        if (data == null){
            data = new TagSkuDocument();
            data.setSkuId(skuId);
        }

        List<Tag> ageTags = new ArrayList<>();
        termsAgg.getBuckets().forEach(t->{
            ageTags.add(TagFilter.build(TagType.AGE).put("age", t.getKey().toString()).put("count", t.getDocCount()));
        });
        data.addTag(ageTags);

        esSkuTagRepository.save(data);
    }

    public void updateSkuTagAllFromUserSku(){

        ScrollCallback<TagSkuDocument> tagsCallback = page -> {
            page.getContent().forEach(d->{
                fetchFromUserSku(d.getSkuId());
            });

        };

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0, 1000))
                .build();

        esSkuTagRepository.scroll(searchQuery, 5000L, false, tagsCallback);
    }


    public void updateSkuTagFromSku(){

        BatchSaver<EsSkuTagRepository, TagSkuDocument> batchSaver = new BatchSaver<>(esSkuTagRepository, 1000);
        ScrollCallback<SkuDetailDocument> skuDataScrollCallback = page -> {
            page.getContent().forEach(d->{
                TagSkuDocument tagSkuDocument = TagFilter.filterSku(d, null);
                batchSaver.save(tagSkuDocument);
            });
            batchSaver.finish();
        };

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(new PageRequest(0, 1000))
                .build();

        esSkuRepository.scroll(searchQuery, 5000L, false, skuDataScrollCallback);
    }

}
