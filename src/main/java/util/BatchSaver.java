package util;

import application.elastic.repository.base.CustomElasticsearchRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/20.
 */
public class BatchSaver<R extends CustomElasticsearchRepository, T> {
    private static final Logger logger = LogManager.getLogger();

    List<T> list = new ArrayList<>();
    int batchSize = 0;
    int savedCount = 0;
    R customElasticsearchRepository;

    public BatchSaver(R customElasticsearchRepository, int batchSize){
        this.batchSize = batchSize;
        this.customElasticsearchRepository = customElasticsearchRepository;
    }

    public void save(T data){
        list.add(data);

        if (list.size() >= batchSize){
            saveAndClear();
        }
    }

    public void finish(){
        if (list.size() > 0){
            saveAndClear();
        }
        logger.info("Saved count = {}", savedCount);
    }

    private void saveAndClear(){
        customElasticsearchRepository.update(list);
        savedCount += list.size();
        list.clear();
    }
}
