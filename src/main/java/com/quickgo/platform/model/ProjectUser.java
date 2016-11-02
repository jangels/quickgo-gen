package com.quickgo.platform.model;


import com.quickgo.platform.annotation.Alias;

import java.io.Serializable;

/**
 * @author huangjie
 * @since  2016-07-20
 */
@Alias("project_user")
public class ProjectUser implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String projectId;
    private String userId;
    private Long createTime;
    private String status;
    private String editable;
    private String commonlyUsed;

    public interface Status{
        String PENDING="PENDING";
        String ACCEPTED="ACCEPTED";
        String REFUSED="REFUSED";
    }

    public interface Editable {
        String YES="YES";
        String NO="NO";
    }
    public interface CommonlyUsed{
        String YES="YES";
        String NO="NO";
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommonlyUsed() {
        return commonlyUsed;
    }

    public void setCommonlyUsed(String commonlyUsed) {
        this.commonlyUsed = commonlyUsed;
    }
}
