package application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by huangzebin on 2017/3/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {

    @Autowired
    NewsService newsService;

    @Test
    public void update() throws Exception {

    }

    @Test
    public void updaetNews() throws Exception {
        newsService.linkRecommendProductToNews();
    }

}