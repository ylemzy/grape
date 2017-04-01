package application.api;

import application.elastic.TagSkuDocument;
import application.elastic.repository.EsSkuTagRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/22.
 */
@RestController
public class SkuTagApi {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    EsSkuTagRepository esSkuTagRepository;


    @RequestMapping(value = "/sku/tags", method = RequestMethod.POST)
    public List<TagSkuDocument> getSkuTags(@RequestBody String skuIds) {
        logger.info("Get /sku/tags {}", skuIds);
        List<String> query = JsonHelper.toList(skuIds);

        List<TagSkuDocument> tagSkuDocuments = new ArrayList<>();
        if (query.size() <= 0)
            return null;

        Iterable<TagSkuDocument> all = esSkuTagRepository.findAll(query);
        all.forEach(d->{
            tagSkuDocuments.add(d);
        });
        return tagSkuDocuments;
    }


    @RequestMapping(value = "/sku/tag", method = RequestMethod.POST)
    public TagSkuDocument getSkuTag(@RequestBody String skuId) {

        logger.info("Get /sku/tag {}", skuId);
        TagSkuDocument one = esSkuTagRepository.findOne(skuId);
        return one;
    }


    @RequestMapping(value = "/sku/tag/all", method = RequestMethod.POST)
    public List<TagSkuDocument> getAllSkuTag() {

        logger.info("Get /sku/tag/all");
        List<TagSkuDocument> tagSkuDocuments = new ArrayList<>();
        Iterable<TagSkuDocument> all = esSkuTagRepository.findAll();
        all.forEach(d->{

            if (tagSkuDocuments.size() < 3)
                tagSkuDocuments.add(d);

        });
        return tagSkuDocuments;
    }

}
