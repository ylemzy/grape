package application.schedule;/**
 * Created by huangzebin on 2017/3/10.
 */

import application.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NewsSchedule {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    NewsService newsService;

    @Scheduled(cron = "${jobs.fetch.schedule.mergeNews.cron}")
    public void updateNews(){

        logger.info("Start update news");
        try {
            newsService.linkRecommendProductToNews();
            /*newsService.writeToBusiness();
            newsService.keepLastFew();*/
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
}
