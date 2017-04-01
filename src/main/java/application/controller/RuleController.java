package application.controller;

import application.elastic.NewsDocument;
import application.elastic.RuleLoader;
import application.elastic.RuleProductDocument;
import application.elastic.repository.EsRuleProductRepository;
import application.service.RuleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/8.
 */
@RestController
@RequestMapping("/rule")
public class RuleController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    EsRuleProductRepository esRuleProductRepository;

    @Autowired
    RuleService ruleService;

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public void updateRule(){
        logger.info("start update rule");
        RuleLoader ruleLoader = new RuleLoader();
        try {
            List<RuleProductDocument> load = ruleLoader.load();
            esRuleProductRepository.save(load);
        } catch (IOException e) {
            logger.error(e, e);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Object updateRule(@RequestParam String ruleId){
        Iterable<NewsDocument> newsWithRule = ruleService.findNewsWithRule(ruleId);
        List<NewsDocument> res = new ArrayList<>();
        CollectionUtils.addAll(res, newsWithRule.iterator());
        return res;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity setRule(@RequestParam String ruleId, @RequestBody RuleProductDocument rule){
        try{
            ruleService.saveRules(rule);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
