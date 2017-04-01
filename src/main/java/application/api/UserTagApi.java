package application.api;

import application.elastic.TagUserDocument;
import application.elastic.UserSkusDocument;
import application.elastic.repository.EsUserSkusRepository;
import application.elastic.repository.EsUserTagRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangzebin on 2017/2/27.
 */
@RestController
public class UserTagApi {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    EsUserTagRepository esUserTagRepository;

    @Autowired
    EsUserSkusRepository esUserSkusRepository;


    @RequestMapping(value = "/user/tag", method = RequestMethod.POST)
    public TagUserDocument getUserTag(@RequestParam String openId) {
        logger.info("Get /user/tag {}=", openId);
        return esUserTagRepository.findOne(openId);
    }

    @RequestMapping(value = "/user/skus", method = RequestMethod.POST)
    public UserSkusDocument getUserSkus(@RequestParam String openId){
        logger.info("Get /user/skus {}=", openId);
        return esUserSkusRepository.findOne(openId);
    }

}

