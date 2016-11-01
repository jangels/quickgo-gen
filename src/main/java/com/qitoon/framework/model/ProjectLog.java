package com.qitoon.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目操作记录
 * @author huangjie
 * @since  2016-07-13
 */
public class ProjectLog implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private Date createTime;
    private String log;
    private String action;
    private String projectId;

    public ProjectLog() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
