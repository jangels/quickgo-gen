package com.qitoon.framework.dao;

import com.qitoon.framework.model.User;

import java.util.List;


public interface UserMapper {
     List<User> findUserInfo();
}
