package application.service;

import application.elastic.UserActionDocument;
import application.elastic.repository.EsUserActionRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/2/17.
 */
@Service
public class EsUserActionService {


    @Autowired
    private EsUserActionRepository userActionRepository;


    public UserActionDocument findUserAction(String id){
        UserActionDocument res = userActionRepository.findOne(id);
        return res;
    }

    public Iterable<UserActionDocument> findAll(){
        Iterable<UserActionDocument> res = userActionRepository.search(QueryBuilders.matchAllQuery());
        return res;
    }


}
