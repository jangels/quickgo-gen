package com.quickgo.platform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjie
 * @since  2016-09-07
 */
public class RequestResponseArgs implements Serializable{
    private static final long serialVersionUID = 1L;
    private String description;
    private String name;
    private String type;
    private String require;
    private String defaultValue;
    private List<RequestResponseArgs> children = new ArrayList<>();

    public RequestResponseArgs() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<RequestResponseArgs> getChildren() {
        return children;
    }

    public void setChildren(List<RequestResponseArgs> children) {
        this.children = children;
    }
}
