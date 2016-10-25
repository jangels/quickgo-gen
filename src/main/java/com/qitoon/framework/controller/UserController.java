package com.qitoon.framework.controller;

import com.qitoon.framework.model.User;
import com.qitoon.framework.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public List<User> getUserInfo() {
        List<User> user = userService.getUserInfo();
        if(user!=null){
            logger.info("user.getAge():"+user.toString());
        }
        return user;
    }
}
