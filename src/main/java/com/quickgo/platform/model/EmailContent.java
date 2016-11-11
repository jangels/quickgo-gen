package com.quickgo.platform.model;

import java.io.Serializable;

/**
 * Created by 146336
 * on 2016/11/11.
 */
public class EmailContent implements Serializable{

    private static final long serialVersionUID = 1L;

    private String title; // 主题

    private String content; // 内容


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
