package com.quickgo.platform.dao;

import com.quickgo.platform.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseDao<User> {
     List<User> findUserInfo();

     User findUser(@Param(value = "email") String email,@Param(value = "password")  String password);

     int countEmail(String email);

     String getUserIdByEmail(String email);

     List<User> getAllProjectByUserId(String userId);
}
