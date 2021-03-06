package com.quickgo.platform.controller;


import com.quickgo.platform.annotation.Get;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.thirdly.AccessParentToken;
import com.quickgo.platform.thirdly.Github;
import com.quickgo.platform.thirdly.QQ;
import com.quickgo.platform.thirdly.Weibo;
import com.quickgo.platform.utils.ConfigUtils;
import com.quickgo.platform.view.JspView;
import com.quickgo.platform.view.ResultView;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;



/**
 * @author huangjie
 * @since  2016-06-03
 */
@RestController
@RequestMapping("callback")
public class CallbackController {
    private static Logger logger = Logger.getLogger(CallbackController.class);
    private Set<String> states = new HashSet<String>() {{
        add("login");
        add("relation");
    }};

    @Get(value = "qq", template = "/third-party")
    public Object qqCallback(Parameter parameter) {
        String code = parameter.getParamString().get("code");
        String state = parameter.getParamString().get("state");
        logger.info("callback qq -> code:"+code+" state:"+state);
        if (states.contains(state)) {
            QQ qq = new QQ();
            AccessParentToken accessToken = qq.getAccessToken(code, ConfigUtils.getProperty("qq.redirect_uri"));
            String openId = qq.getOpenid(accessToken.getAccess_token());
            return new _HashMap<>()
                    .add("openId", openId)
                    .add("type", "qq")
                    .add("state", state)
                    .add("accessToken", accessToken.getAccess_token());
        }

        return illegalView();
    }

    private ResultView illegalView() {
        JspView view = new JspView("/WEB-INF/jsp", ".jsp");
        view.setTemplate("/illegal");
        view.setData(new _HashMap<>().add("errorMsg", "非法请求"));
        return view;
    }



    @Get(value = "weibo", template = "/third-party")
    public Object weibo(Parameter parameter) {
        String code = parameter.getParamString().get("code");
        String state = parameter.getParamString().get("state");
        logger.info("callback weibo -> code:"+code+" state:"+state);
        if (states.contains(state)) {
            Weibo weibo = new Weibo();
            AccessParentToken accessToken = weibo.getAccessToken(ConfigUtils.getProperty("weibo.appkey"), ConfigUtils.getProperty("weibo.appsecret"), code, ConfigUtils.getProperty("weibo.redirect_uri"));
            return new _HashMap<>()
                    .add("type", "weibo")
                    .add("state", state)
                    .add("accessToken", accessToken.getAccess_token())
                    .add("uid", accessToken.getUid());
        }
        return illegalView();
    }


    @Get(value = "weibo/cancel")
    public Object weiboCancel(Parameter parameter) {
        logger.info("callback weibo cancel");
        return null;
    }



    @Get(value = "github", template = "/third-party")
    public Object github(Parameter parameter) {
        String code = parameter.getParamString().get("code");
        String state = parameter.getParamString().get("state");
        logger.info("callback github -> code:"+code+" state:"+state);
        if (states.contains(state)) {
            Github github = new Github();
            AccessParentToken accessToken = github.getAccessToken(ConfigUtils.getProperty("github.clientid"),ConfigUtils.getProperty("github.secret"),code,ConfigUtils.getProperty("github.redirect_uri"));
            User user = github.getUser(accessToken.getAccess_token());
            return new _HashMap<>()
                    .add("type", "github")
                    .add("gitid",user.getId())
                    .add("state", state)
                    .add("accessToken", accessToken.getAccess_token());
        }
        return illegalView();
    }


}
