package com.quickgo.platform.model;

import java.io.Serializable;

/**
 * 项目操作记录
 * @author huangjie
 * @since  2016-07-13
 */
public class ProjectLog implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private Long createTime;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
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
