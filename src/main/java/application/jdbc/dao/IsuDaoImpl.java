package application.jdbc.dao;

import application.jdbc.bean.Isu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/14.
 */

@Transactional
@Repository
public class IsuDaoImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Isu> rowMapper = new RowMapper<Isu>() {
        @Override
        public Isu mapRow(ResultSet resultSet, int i) throws SQLException {
            Isu isu = new Isu();
            isu.setId(resultSet.getString("I.isu_aid"));
            isu.setOrdId(resultSet.getString("I.ord_aid"));
            isu.setIsuSsn(resultSet.getString("I.isu_ssn"));
            isu.setStartTime(resultSet.getTimestamp("I.isu_dt_start"));
            isu.setEndTime(resultSet.getTimestamp("I.isu_dt_end"));
            return isu;
        }
    };


    public List<Isu> getIsu(List<String> orderIds){

        String join = String.join(",", orderIds);
        return jdbcTemplate.query("select I.isu_aid, I.ord_aid, I.isu_ssn, I.isu_dt_start, I.isu_dt_end " +
                " from tc_acct_isu I where I.ord_aid in (" + join + ")", rowMapper);
    }

}
