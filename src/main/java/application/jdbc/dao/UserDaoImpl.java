package application.jdbc.dao;

import application.jdbc.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/13.
 */
@Transactional
@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setCustPhone(resultSet.getString("U.cust_phone_uk"));
            user.setCustSsn(resultSet.getString("U.cust_ssn"));
            user.setWeixinOpenid(resultSet.getString("U.sns_weixin_openid"));
            user.setId(resultSet.getString("U.cust_aid"));
            user.setRegTime(resultSet.getTimestamp("U.register_date"));
            user.setRegisterSource(resultSet.getString("U.register_source"));
            return user;
        }
    };

    @Override
    public User getUser(String id) {

        return jdbcTemplate.queryForObject("select U.cust_phone_uk, U.cust_ssn, U.sns_weixin_openid, U.cust_aid, U.register_date, U.register_source from uc_user U,  where U.cust_aid = ?",
                new Object[]{id}, rowMapper);
    }

    @Override
    public List<User> getUser(List<String> ids) {
        String join = String.join(",", ids);
        return jdbcTemplate.query("select U.cust_phone_uk, U.cust_ssn, U.sns_weixin_openid, U.cust_aid, U.register_date, U.register_source" +
                " from uc_user U where U.cust_aid in (" + join + ")" + " ORDER BY FIELD(U.cust_aid, " + join + ")", rowMapper);
    }
}
