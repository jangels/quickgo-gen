package com.qitoon.framework.model;


import com.qitoon.framework.annotation.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huangjie
 * @since  2016-07-20
 */
@Alias("team")
public class Team implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private String userId;
    private Date createTime;
    private String status;

    public interface Status{
        String VALID="VALID";
        String INVALID="INVALID";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
