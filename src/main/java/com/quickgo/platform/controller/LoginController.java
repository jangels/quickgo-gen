package com.quickgo.platform.controller;


import com.quickgo.platform.face.ILoginService;
import com.quickgo.platform.face.ISendEmailService;
import com.quickgo.platform.model.EmailContent;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.utils.MemoryUtils;
import com.quickgo.platform.utils.StringUtils;
import com.quickgo.platform.utils.Validate;
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
    @Autowired
    private ISendEmailService sendEmailService;

    @RequestMapping("/login")
    public Object login(String  email, String password) {
        if(StringUtils.isEmpty(email)){
            return Result.returnInfo("用户名为空");
        }
        if(StringUtils.isEmpty(password)){
            return Result.returnInfo("密码为空");
        }
        password = Validate.password(password);
        User user = loginService.findUser(email, password);
        if(!email.equals(user.getEmail()) || !password.equals(user.getPassword())){
            return Result.returnInfo("用户名或密码错误");
        }
        if (user.getStatus().equals(User.Status.INVALID)) {
            return  Result.returnInfo("invalid status");
        }
        String token = MemoryUtils.token();
        MemoryUtils.putUser(token, user);
        return Result.returnInfo(new _HashMap<>().add("user",user).add("token",token));
    }

    @RequestMapping("/sendMail")
    public Object sendMail() {
        EmailContent emailContent = new EmailContent();
        emailContent.setContent("测试邮件，勿回复！！！");
        emailContent.setTitle("测试");
        sendEmailService.sendEmail(emailContent);
        return Result.OK;
    }



}
