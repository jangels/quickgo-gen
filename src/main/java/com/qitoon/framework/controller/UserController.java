package com.qitoon.framework.controller;


import com.qitoon.framework.face.IUserService;
import com.qitoon.framework.model.FindPassword;
import com.qitoon.framework.model.User;
import com.qitoon.framework.param.Message;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.param.Result;
import com.qitoon.framework.param._HashMap;
import com.qitoon.framework.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * create by huangjie
 * on  2016-05-31
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 修改
     * @param id id
     * @param token token
     * @param user user
     * @return Object
     */
    @RequestMapping("/{id}")
    @ResponseBody
    public Object update(@PathVariable("id") String id, String token,User user) {
        User temp = MemoryUtils.getUser(token);
        AssertUtils.isTrue(id.equals(temp.getId()), "无操作权限");
        int rs = userService.updatePwd(user);
        AssertUtils.isTrue(rs > 0, "操作失败");
        return Result.returnInfo(rs);
    }
    @RequestMapping("/register")
    public Object create(User user) {
        AssertUtils.notNull(user,"无效的注册信息");
        AssertUtils.isTrue(Validate.isEmail(user.getEmail()), "请输入有效的邮箱");
        // 检查账号是否已存在
        AssertUtils.isTrue(userService.checkEmail(user.getEmail()), Message.EMAIL_EXISTS);
        user.setPassword(Validate.password(user.getPassword()));
        user.setType(User.Type.USER);
        user.setCreatetime(new Date());
        user.setId(Validate.id());
        user.setStatus(User.Status.PENDING);
        int rs = userService.create(user);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        String token = MemoryUtils.token();
        user.setPassword(null);
        MemoryUtils.putUser(token, user);
        return Result.returnInfo(new _HashMap<>().add("user", user).add("token", token));
    }

    @RequestMapping("/search")
    public Object search(Parameter parameter) {
        String key = parameter.getParamString().get("key");
        if (key == null || key.trim().length() == 0)
            return null;
        User currentUser = MemoryUtils.getUser(parameter);
        List<User> users = null;// ServiceFactory.instance().searchUsers(key, currentUser.getId());
        return Result.returnInfo(new _HashMap<>().add("users", users));
    }

    /**
     *  获取项目下所有用户
     * @param token 参数
     * @return Object
     */
    @RequestMapping("/project_users")
    public Object getAllProjectUsers(String token) {
        User user = MemoryUtils.getUser(token);
        if(user==null) {
            return null;
        }
        List<User> users =userService.getAllUserByUserId(user.getId());
        return Result.returnInfo(new _HashMap<>().add("users", users)
                .add("fileAccess", ConfigUtils.getFileAccessURL()));
    }

    /**
     * 找回密码
     *
     * @param email  email
     * @return Object
     */
    @RequestMapping("/findpassword")
    public Object findPassword(String email ) {
        AssertUtils.notNull(email, "邮箱为空");
        AssertUtils.isTrue(Validate.isEmail(email), "邮箱格式错误");
        AssertUtils.isTrue(userService.checkEmail(email), "邮箱不存在");
        FindPassword fp = new FindPassword();
        fp.setIsUsed(0);
        fp.setEmail(email);
        fp.setCreateTime(new Date());
        fp.setId(Validate.id());
        int rs = userService.findPassword(fp);
        AssertUtils.isTrue(rs > 0, "操作失败");
        MailUtils.findPassword(fp.getId(), email);
        return Result.returnInfo(rs);
    }

    /**
     * 修改密码
     * @param token token
     * @param password password
     * @return Object
     */
    @RequestMapping("/password")
    public Object updatePassword(String token,String password) {
        User user = MemoryUtils.getUser(token);
        User temp = new User();
        temp.setId(user.getId());
        temp.setPassword(Validate.password(password));
        int rs =userService.updatePwd(user);
        AssertUtils.isTrue(rs > 0, "操作失败");
        return Result.returnInfo(rs);
    }

    /**
     * 找回密码2
     *
     * @param email
     * @return
     */
    @RequestMapping("/newpassword")
    public Object newPassword(String password,String email,String id) {
        AssertUtils.notNull(email, "邮箱为空");
        AssertUtils.notNull(id, "无效请求");
        AssertUtils.notNull(password, "密码为空");
        AssertUtils.isTrue(Validate.isEmail(email), "邮箱格式错误");
        int rs = 0;//userService.findPassword(id, email, password);
        AssertUtils.isTrue(rs > 0, "操作失败");
        return Result.returnInfo(rs);
    }

    /**
     * 发送邮箱验证码
     *
     * @param token token
     * @param email email
     * @return Object
     */
    @RequestMapping("email/captcha")
    public Object sendEmailCaptcha(String email,String token) {
        String code = Validate.code();
        AssertUtils.notNull(email, "邮箱为空");
        AssertUtils.isTrue(Validate.isEmail(email), "邮箱格式错误");
        MailUtils.sendCaptcha(code, email);
        MemoryUtils.put(token, "emailCaptcha", code);
        return true;
    }

    /**
     * 新邮件
     *
     * @param parameter
     * @return
     */
    @RequestMapping("email/new")
    public Object newEmail(Parameter parameter) {
        String code = parameter.getParamString().get("code");
        AssertUtils.notNull(code, "验证码为空");
        String email = parameter.getParamString().get("email");
        AssertUtils.notNull(email, "邮箱为空");
        AssertUtils.isTrue(Validate.isEmail(email), "邮箱格式错误");
        String token = parameter.getParamString().get("token");
        String captcha = (String) MemoryUtils.get(token, "emailCaptcha");
        AssertUtils.isTrue(code.equals(captcha), "验证码错误");
        // 检查邮箱是否存在
//        AssertUtils.isTrue(!ServiceFactory.instance().checkEmailExists(email), "该邮箱已存在");
        User user = MemoryUtils.getUser(parameter);
        User temp = new User();
        temp.setId(user.getId());
        temp.setEmail(email);
        int rs = 0;//ServiceFactory.instance().update(temp);
        AssertUtils.isTrue(rs > 0, "操作失败");
        MemoryUtils.put(token, "emailCaptcha", "");
        return Result.returnInfo(rs);
    }

    /**
     * 绑定第三方
     * @param parameter
     * @return
     */
   /* @Post("bind")
    public Object bind(Parameter parameter){
        AssertUtils.notNull(parameter,"type","accessToken");
        String type = parameter.getParamString().get("type");
        String accessToken = parameter.getParamString().get("accessToken");
        User user = MemoryUtils.getUser(parameter);
        Thirdparty thirdparty = new Thirdparty();
        thirdparty.setUserId(user.getId());
        switch (type){
            case "qq":
                String openId = parameter.getParamString().get("openId");
                AssertUtils.notNull(openId,"missing openId");
                thirdparty.setId(openId);
                thirdparty.setType(Thirdparty.Type.QQ);
                break;
            case "weibo":
                String uid = parameter.getParamString().get("uid");
                AssertUtils.notNull(uid,"missing uid");
                thirdparty.setId(uid);
                thirdparty.setType(Thirdparty.Type.WEIBO);
                break;
            case "github":
                String gitid = parameter.getParamString().get("gitid");
                AssertUtils.notNull(gitid,"missing gitid");
                thirdparty.setId(gitid);
                thirdparty.setType(Thirdparty.Type.GITHUB);
                break;
            default:
                throw new InvalidArgumentException("invalid type ");
        }
        int rs = ServiceFactory.instance().bindUserWithThirdParty(thirdparty);
        AssertUtils.isTrue(rs>0,"操作失败");
        switch (type){
            case "qq":
                user.setBindQQ(true);
                break;
            case "weibo":
                user.setBindWeibo(true);
                break;
            case "github":
                user.setBindGithub(true);
                break;
        }
        return new _HashMap<>()
                .add("user",user)
                ;
    }

    @Post("unbind/{type}")
    public Object unbind(Parameter parameter,@RequestParam("type")String type){
        User user = MemoryUtils.getUser(parameter);
        int rs = ServiceFactory.instance().unbindUserThirdPartyRelation(user.getId(),type);
        AssertUtils.isTrue(rs>0,"操作失败");
        switch (type.toLowerCase()){
            case "qq":
                user.setBindQQ(false);
                break;
            case "weibo":
                user.setBindWeibo(false);
                break;
            case "github":
                user.setBindGithub(false);
                break;
        }
        return new _HashMap<>()
                .add("user",user)
                ;
    }*/
}
