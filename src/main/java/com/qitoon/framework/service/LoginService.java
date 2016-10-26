package com.qitoon.framework.service;

import com.qitoon.framework.dao.UserMapper;
import com.qitoon.framework.face.ILoginService;
import com.qitoon.framework.model.User;
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
