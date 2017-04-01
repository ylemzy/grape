package application.service;

import application.AppProperties;
import application.elastic.InsureProductDocument;
import application.elastic.repository.EsInsureProductRepository;
import application.jdbc.bean.InsureProduct;
import application.jdbc.dao.InsureProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.BatchSaver;

import java.util.List;

/**
 * Created by huangzebin on 2017/2/24.
 */
@Service
public class EsInsureProductService {

    @Autowired
    EsInsureProductRepository esInsureProductRepository;

    @Autowired
    InsureProductDao insureProductDao;

    @Autowired
    AppProperties appProperties;

    public void updateInsureProduct(){
        List<InsureProduct> insureProduct = insureProductDao.getInsureProduct();
        BatchSaver<EsInsureProductRepository, InsureProductDocument> batchSaver = new BatchSaver<>(esInsureProductRepository, appProperties.esBatchSize);
        for (InsureProduct product : insureProduct) {
            InsureProductDocument data = new InsureProductDocument();
            data.setProductId(product.getProductId());
            data.setCatId(product.getCatId());
            data.setBuyVendor(product.getBuyVendor());
            data.setProductName(product.getProductName());
            data.setSeoKey(product.getSeoKey());
            data.setIsuAgeMin(product.getIsuAgeMin());
            data.setIsuAgeMax(product.getIsuAgeMax());
            data.setPcSlogan(data.getPcSlogan());
            data.setPcGoodPoint(data.getPcGoodPoint());
            data.setTag(product.getTag());
            batchSaver.save(data);
        }
        batchSaver.finish();
    }
}
