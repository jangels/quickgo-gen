package com.qitoon.framework.service;

import com.qitoon.framework.dao.UserMapper;
import com.qitoon.framework.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zl on 2015/8/27.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUserInfo(){
        List<User> user = userMapper.findUserInfo();
        //User user=null;
        return user;
    }

}
