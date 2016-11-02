package com.quickgo.platform.controller;


import com.quickgo.platform.face.ILoginService;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.utils.AssertUtils;
import com.quickgo.platform.utils.MemoryUtils;
import com.quickgo.platform.utils.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangjie
 * on  2016-10-23
 */
@RestController
public class LoginController {
    @Autowired
    private ILoginService loginService;
    private static Logger logger = Logger.getLogger(LoginController.class);
    @RequestMapping("/login")
    public Object login(String  email, String password) {
        AssertUtils.notNull(email, "用户名为空");
        AssertUtils.notNull(password, "密码为空");
        password = Validate.password(password);
        User user = loginService.findUser(email, password);
        if(!email.equals(user.getEmail()) || !password.equals(user.getPassword())){
            return Result.returnInfo(user,"用户名或密码错误");
        }
        AssertUtils.notNull(user, "用户名或密码错误");
        if (user.getStatus().equals(User.Status.INVALID)) {
            return new Result(Result.ERROR, "invalid status");
        }
        String token = MemoryUtils.token();
        MemoryUtils.putUser(token, user);
        return Result.returnInfo(new _HashMap<>().add("user",user).add("token",token));
    }




}