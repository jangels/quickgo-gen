package com.quickgo.platform.service;

import com.quickgo.platform.dao.UserMapper;
import com.quickgo.platform.face.ILoginService;
import com.quickgo.platform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService implements ILoginService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public User findUser(String email, String password) {
        return userMapper.findUser(email,password);
    }
}
