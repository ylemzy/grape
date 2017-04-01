package application.service;

import application.AppProperties;
import application.elastic.InsureProductDocument;
import application.elastic.SkuDetailDocument;
import application.elastic.repository.EsInsureProductRepository;
import application.elastic.repository.EsSkuRepository;
import application.jdbc.bean.Sku;
import application.jdbc.dao.SkuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.BatchSaver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzebin on 2017/2/22.
 */
@Service
public class SkuService {

    @Autowired
    SkuDao skuDao;

    @Autowired
    EsSkuRepository skuRepository;

    @Autowired
    EsInsureProductRepository esInsureProductRepository;

    @Autowired
    AppProperties appProperties;

    public void saveSkuData(){
        List<Sku> allSkus = skuDao.getAllSkus();

        Iterable<InsureProductDocument> insureProductDatas = esInsureProductRepository.findAll();
        Map<String, InsureProductDocument> insureMap = new HashMap<>();
        insureProductDatas.forEach(d-> insureMap.put(d.getProductId(), d));

        BatchSaver<EsSkuRepository, SkuDetailDocument> batchSaver = new BatchSaver<>(skuRepository, appProperties.esBatchSize);
        allSkus.forEach(sku -> {
            SkuDetailDocument skuDetailDocument = new SkuDetailDocument();
            skuDetailDocument.setSkuId(sku.getSkuId());
            skuDetailDocument.setProductId(sku.getProductId());
            skuDetailDocument.setSkuNameAlt(sku.getSkuNamealt());
            skuDetailDocument.setPrice(sku.getPrice());
            skuDetailDocument.setSkuType(sku.getSkuType());
            skuDetailDocument.setSku3(sku.getSku3());
            skuDetailDocument.setUpdateTime(System.currentTimeMillis());

            skuDetailDocument.setInsureProductDocument(insureMap.get(sku.getProductId()));
            batchSaver.save(skuDetailDocument);
        });
        batchSaver.finish();
    }

   /* public List<Sku> getSkuByIds(String... skuIds){

        return skuRepository.search(QueryBuilders.idsQuery(skuIds), new PageRequest());
    }*/


}
