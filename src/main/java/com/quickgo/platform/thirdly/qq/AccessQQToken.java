package com.quickgo.platform.thirdly.qq;

import com.quickgo.platform.thirdly.AccessParentToken;

/**
 * @author huangjie
 * @since  2016-07-28
 */
public class AccessQQToken extends AccessParentToken{
    private String refresh_token;
    private long expires_in;

    public AccessQQToken() {
    }

    public AccessQQToken(String access_token, String refresh_token, long expires_in) {
        super(access_token);
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
}
