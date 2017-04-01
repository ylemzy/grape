package application.jdbc.dao;

import application.jdbc.bean.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/9.
 */
@Transactional
@Repository
public class NewsDaoImpl {
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<News> rowMapper = (resultSet, i) -> {
        News news = new News();
        news.setNewsId(resultSet.getString("news_aid"));
        news.setSortNo(resultSet.getInt("sort_no"));
        news.setNewShow(resultSet.getInt("news_show"));
        news.setNewsType(resultSet.getString("news_type"));
        news.setNewsSource(resultSet.getString("news_source"));
        news.setContent(resultSet.getString("content"));
        news.setImg(resultSet.getString("img"));
        news.setTitle(resultSet.getString("title"));
        news.setDescShort(resultSet.getString("desc_short"));
        news.setSkuId(resultSet.getString("sku_aid"));
        news.setSaCnt(resultSet.getInt("sa_cnt"));
        news.setIsRecommend(resultSet.getInt("is_recommend"));
        news.setInfoType(resultSet.getInt("info_type"));
        news.setCreateTime(resultSet.getTimestamp("fp_cdate"));
        news.setCurrentUser(resultSet.getString("fp_cusr"));
        return news;
    };


    public List<News> findNews(List<String> ids){
        String join = String.join(",", ids);
        return jdbcTemplate.query("select * " +
                " from buy_news_top  where news_aid in (" + join + ")", rowMapper);
    }


    public void updateNews(List<News> newsList){
        String replace = "REPLACE INTO buy_news_top (news_aid , sort_no, news_show, news_type, news_source, content, img, title, desc_short, sku_aid, sa_cnt, is_recommend, info_type, fp_cusr) \n" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.batchUpdate(replace, new BatchPreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

                News news = newsList.get(i);
                preparedStatement.setString(1, news.getNewsId());
                preparedStatement.setInt(2, news.getSortNo());
                preparedStatement.setInt(3, news.getNewShow());
                preparedStatement.setString(4, news.getNewsType());
                preparedStatement.setString(5, news.getNewsSource());
                preparedStatement.setString(6, news.getContent());
                String img = news.getImg();
                //Assert.isTrue(img.contains(".jpg") || img.contains(".png"));
                preparedStatement.setString(7, img);
                preparedStatement.setString(8, news.getTitle());
                preparedStatement.setString(9, news.getDescShort());
                preparedStatement.setString(10, news.getSkuId());
                preparedStatement.setInt(11, news.getSaCnt());
                preparedStatement.setInt(12, news.getIsRecommend());
                preparedStatement.setInt(13, news.getInfoType());
                preparedStatement.setString(14, news.getCurrentUser());
            }

            @Override
            public int getBatchSize() {
                return newsList.size();
            }
        });
    }


    public List<News> queryActiveNews(){
        String query = "select * from buy_news_top where news_type = 1";
        return jdbcTemplate.query(query, rowMapper);
    }


}
