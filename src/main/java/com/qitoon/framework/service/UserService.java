package com.qitoon.framework.service;

import com.qitoon.framework.dao.FindPasswordMapper;
import com.qitoon.framework.dao.UserMapper;
import com.qitoon.framework.face.IUserService;
import com.qitoon.framework.model.FindPassword;
import com.qitoon.framework.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FindPasswordMapper findPasswordMapper;

    public List<User> getUserInfo(){
        List<User> user = userMapper.findUserInfo();
        return user;
    }

    @Override
    public boolean checkEmail(String email){
        return userMapper.countEmail(email)>0?false:true;
    }

    @Override
    public int create(User user){
        return userMapper.insert(user);
    }

    @Override
    public String getUserIdByEmail(String email) {
        return userMapper.getUserIdByEmail(email);
    }

    @Override
    public List<User> getAllUserByUserId(String userId){
        return userMapper.getAllProjectByUserId(userId);
    }

    @Override
    public int findPassword(FindPassword findPassword) {
        return findPasswordMapper.insert(findPassword);
    }

    @Override
    public int updatePwd(User user) {
        return userMapper.updateById(user);
    }

}
