package com.quickgo.platform.model;

import java.io.Serializable;

/**
 * Created by huangjie
 * on 2016/11/1.
 */
public class CopyFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String action;
    private String type;
    private String moduleId;
    private String folderId;
    private String targetId;
    private String projectId;

    public CopyFolder() {
        super();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
