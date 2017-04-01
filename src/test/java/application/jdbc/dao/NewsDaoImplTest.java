package application.jdbc.dao;

import application.jdbc.bean.News;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/3/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsDaoImplTest {

    @Autowired
    NewsDaoImpl newsDao;

    @Test
    public void findNews() throws Exception {
        List<String> ids = new ArrayList<>();
        ids.add("14885402236647758");
        ids.add("14889567990679916");
        List<News> news = newsDao.findNews(ids);
        System.out.println(JsonHelper.toJSON(news));
    }

    @Test
    public void updateNews() throws Exception {
        List<String> ids = new ArrayList<>();
        ids.add("14885402236647758");
        ids.add("14889567990679916");
        List<News> news = newsDao.findNews(ids);
        news.get(0).setSaCnt(100);
        newsDao.updateNews(news);
        //System.out.println(JsonHelper.toJSON(news));

    }

}