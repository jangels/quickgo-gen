package com.qitoon.framework.thirdly.weibo;

import com.qitoon.framework.thirdly.AccessParentToken;

/**
 * @author : huangjie
 * @since : 16/9/2
 */
public class AccessWeiboToken extends AccessParentToken{
    private int expires_in;
    private String uid;

    public AccessWeiboToken() {
    }

    public AccessWeiboToken(String access_token, int expires_in, String uid) {
        super.setAccess_token(access_token);
        this.expires_in = expires_in;
        this.uid = uid;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
