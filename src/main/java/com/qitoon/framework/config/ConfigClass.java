package com.qitoon.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "token",locations = "classpath:config.properties")
public class ConfigClass {
    private String expires;

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
