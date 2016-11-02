package com.quickgo.platform.model;


import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjie
 * @since  2016-07-14
 */
public class InterfaceFolder implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private Long createTime;
    private String moduleId;
    private String projectId;
    @Ignore
    private List<Interface> children = new ArrayList<>();

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public List<Interface> getChildren() {
        return children;
    }

    public void setChildren(List<Interface> children) {
        this.children = children;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
