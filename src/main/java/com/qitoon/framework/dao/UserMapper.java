package com.qitoon.framework.dao;

import com.qitoon.framework.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseDao<User> {
     List<User> findUserInfo();

     User findUser(@Param(value = "email") String email,@Param(value = "password")  String password);

     int countEmail(String email);

     List<User> getAllProjectByUserId(String userId);
}
