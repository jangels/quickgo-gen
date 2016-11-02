package com.quickgo.platform.thirdly;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quickgo.platform.model.User;
import com.quickgo.platform.thirdly.github.Email;
import com.quickgo.platform.thirdly.github.GithubException;
import com.quickgo.platform.utils.HttpUtils;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;

import java.util.List;


/**
 * @author : huangjie
 * @since : 16/9/2
 */
public class Github {


    public AccessParentToken getAccessToken(String client_id, String client_secret, String code, String redirect_uri){
        String url ="https://github.com/login/oauth/access_token";
        NameValuePair[] pairs = new NameValuePair[]{
           new NameValuePair("client_id",client_id),
           new NameValuePair("client_secret",client_secret),
           new NameValuePair("code",code),
           new NameValuePair("redirect_uri",redirect_uri)
        };
        String rs = HttpUtils.post(url,pairs,new Header("Accept","application/json"));
        AccessParentToken accessToken = JSON.parseObject(rs,AccessParentToken.class);
        if(accessToken == null || accessToken.getAccess_token() == null)
            throw new GithubException(rs);
        return accessToken;
    }

    public List<Email> getEmail(String accessToken){
        String url = "https://api.github.com/user/emails?access_token="+accessToken;
        String rs = HttpUtils.get(url);
        if(rs.contains("message"))
            throw new GithubException(rs);
        return JSON.parseObject(rs,new TypeReference<List<Email>>(){});
    }

    public User getUser(String accessToken){
        String url = "https://api.github.com/user?access_token="+accessToken;
        String rs = HttpUtils.get(url);
        if(rs.contains("message"))
            throw new GithubException(rs);
        return JSON.parseObject(rs,User.class);
    }



}
