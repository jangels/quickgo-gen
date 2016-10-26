package com.qitoon.framework.controller;


import com.qitoon.framework.face.ILoginService;
import com.qitoon.framework.model.User;
import com.qitoon.framework.param.Result;
import com.qitoon.framework.param._HashMap;
import com.qitoon.framework.utils.AssertUtils;
import com.qitoon.framework.utils.MemoryUtils;
import com.qitoon.framework.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangjie
 * @since  2016-10-23
 */
@RestController
public class LoginController {
    @Autowired
    private ILoginService loginService;

    @RequestMapping("/login")
    public Object login(@RequestParam String email, @RequestParam String password) {
//        String email = parameter.getParamString().get("email");
//        String password = parameter.getParamString().get("password");
        AssertUtils.notNull(email, "用户名为空");
        AssertUtils.notNull(password, "密码为空");
        password = Validate.password(password);
        User user = loginService.findUser(email, password);
        AssertUtils.notNull(user, "用户名或密码错误");
        if (user.getStatus().equals(User.Status.INVALID)) {
            return new Result(Result.ERROR, "invalid status");
        }
        String token = MemoryUtils.token();
        MemoryUtils.putUser(token, user);
//        parameter.getRequest().getSession().setAttribute("user",user);
        return new _HashMap<>().add("token", token).add("user", user);
    }




}
