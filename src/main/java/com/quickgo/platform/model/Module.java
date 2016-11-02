package com.quickgo.platform.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 模块
 * @author huangjie
 * @since  2016-07-13
 */

public class Module implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String host;
    private String description;
    private Long lastUpdateTime;
    private Long createTime;
    private String projectId;
    //请求头
    private String requestHeaders;
    //请求参数
    private String requestArgs;

    @Ignore
    private List<InterfaceFolder> folders=new ArrayList<>();

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

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<InterfaceFolder> getFolders() {
        return this.folders;
    }
    public void addInterfaceFolder(InterfaceFolder in){
        this.folders.add(in);
    }

    public void setFolders(List<InterfaceFolder> folders) {
        this.folders = folders;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getRequestArgs() {
        return requestArgs;
    }

    public void setRequestArgs(String requestArgs) {
        this.requestArgs = requestArgs;
    }

}
