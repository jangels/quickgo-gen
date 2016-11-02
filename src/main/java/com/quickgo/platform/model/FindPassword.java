package com.quickgo.platform.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : huangjie
 * on 16/8/21
 */

public class FindPassword implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String email;
    private Integer isUsed;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
