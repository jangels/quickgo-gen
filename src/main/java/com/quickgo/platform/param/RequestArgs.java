package com.quickgo.platform.param;

import java.io.Serializable;

/**
 * Created by huangjie
 * on 2016/11/4.
 */
public class RequestArgs implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private String type;
    private String require;
    private String description;
    private String defaultValue;
    private Object children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public Object getChildren() {
        return children;
    }

    public void setChildren(Object children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
