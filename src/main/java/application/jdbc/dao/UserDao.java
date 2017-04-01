package application.jdbc.dao;

import application.jdbc.bean.User;

import java.util.List;

/**
 * Created by huangzebin on 2017/2/13.
 */
public interface UserDao {

    User getUser(String id);


    List<User> getUser(List<String> ids);
}
