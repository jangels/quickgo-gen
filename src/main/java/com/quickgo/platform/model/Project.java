package com.quickgo.platform.model;


import java.io.Serializable;

/**
 * 项目
 * @author huangjie
 * @since  2016-07-13
 */
public class Project implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    //项目名称
    private String name;
    //简单描述
    private String description;

    private String teamId;

    private String userId;

    private String userName;
    //创建时间
    private Long createTime;
    //状态
    private String status;
    //权限
    private String permission;
    //环境 json
    private String environments;
    //详细说明
    private String details;
    //是否可编辑

    private String editable;
    //是否常用项目
    private String commonlyUsed;

    private DataBase dataBase;


    public interface Status{
        String VALID="VALID";
        String INVALID="INVALID";
    }

    public interface Permission{
        String PUBLIC="PUBLIC";
        String PRIVATE="PRIVATE";
    }
    public interface Action{
        String CREATE_PROJECT="project.create";
        String UPDATE_PROJECT="project.update";
        String DELETE_PROJECT="project.delete";
        String TRANSFER_PROJECT="project.transfer";
        String EXPORT_PROJECT="project.export";

        String CREATE_FOLDER="folder.create";
        String UPDATE_FOLDER="folder.update";
        String DELETE_FOLDER="folder.delete";
        String MOVE_FOLDER="folder.move";
        String COPY_FOLDER="folder.copy";


        String MOVE_INTERFACE="interface.move";
        String COPY_INTERFACE="interface.copy";
        String CREATE_INTERFACE="interface.create";
        String UPDATE_INTERFACE="interface.update";
        String DELETE_INTERFACE="interface.delete";


        String CREATE_MODULE="module.create";
        String UPDATE_MODULE="module.update";
        String DELETE_MODULE="module.delete";
    }

    public Project() {
        super();
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getEnvironments() {
        return environments;
    }

    public void setEnvironments(String environments) {
        this.environments = environments;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getCommonlyUsed() {
        return commonlyUsed;
    }

    public void setCommonlyUsed(String commonlyUsed) {
        this.commonlyUsed = commonlyUsed;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
