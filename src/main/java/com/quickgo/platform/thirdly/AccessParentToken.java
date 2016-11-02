package com.quickgo.platform.thirdly;

/**
 * @author : huangjie
 * @since : 16/9/2
 */
public class AccessParentToken {
    private String access_token;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public AccessParentToken(String access_token) {
        this.access_token = access_token;
    }

    public AccessParentToken() {
    }
}
