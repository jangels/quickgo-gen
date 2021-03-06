package com.quickgo.platform.thirdly;

import com.alibaba.fastjson.JSON;
import com.quickgo.platform.thirdly.qq.AccessQQToken;
import com.quickgo.platform.thirdly.qq.QQException;
import com.quickgo.platform.thirdly.qq.UserInfo;
import com.quickgo.platform.utils.ConfigUtils;
import com.quickgo.platform.utils.HttpUtils;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangjie
 * @since  2016-07-28
 */
public class QQ {
    private static Logger logger = Logger.getLogger("thirdly");
    public UserInfo getUserInfo(String openid, String accessToken){
        String rs = HttpUtils.get("https://graph.qq.com/user/get_user_info?openid="+openid+"&oauth_consumer_key="+ ConfigUtils.getQQAppId()+"&access_token="+accessToken+"&format=json");
        UserInfo userInfo= JSON.parseObject(rs,UserInfo.class);
        if(!userInfo.getRet().equals("0")){
            throw new QQException(rs);
        }
        return userInfo;
    }

    public AccessParentToken getAccessToken(String code, String redirectURI){
        String rs = HttpUtils.get("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="
                +ConfigUtils.getQQAppId()
                +"&client_secret="+ConfigUtils.getQQAppKey()
                +"&code="+code
                +"&redirect_uri="+redirectURI
        );
        logger.debug(rs);
        if(rs.contains("access_token")){
            Matcher matcher = Pattern.compile("access_token=([\\w|\\d]+)&expires_in=([\\d]+)&refresh_token=([\\w|\\d]+)").matcher(rs);
            if(matcher.find()){
                return new AccessQQToken(matcher.group(1),matcher.group(3),Long.parseLong(matcher.group(2)));
            }
            throw  new QQException(rs);
        }
        throw new QQException(rs);
    }

    public String getOpenid(String accessToken){
        String rs= HttpUtils.get("https://graph.qq.com/oauth2.0/me?access_token="+accessToken);
        if(rs.contains("openid")){
            Matcher matcher = Pattern.compile("openid\":\"([\\w|\\d]+)\"").matcher(rs);
            if(matcher.find()){
                return matcher.group(1);
            }
            throw  new QQException(rs);
        }
        throw new QQException(rs);
    }
}
