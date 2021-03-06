package com.quickgo.platform.model;

import com.quickgo.platform.annotation.Ignore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author huangjie
 * @since  2016-10-20
 */
public class Share implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String userId;
    @Ignore
    private String username;
    private Long createTime;
    private String shareAll;
    private String moduleIds;
    private String password;
    private String projectId;
    @Ignore
    private List<Map<String,Object>> shareModules = new ArrayList<>();

    public Share() {
        super();
    }

    public interface ShareAll{
        String YES="YES";
        String NO="NO";
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

    public String getShareAll() {
        return shareAll;
    }

    public void setShareAll(String shareAll) {
        this.shareAll = shareAll;
    }

    public String getModuleIds() {
        return moduleIds;
    }
    public String[] getModuleIdsArray(){
        if(StringUtils.isNotBlank(moduleIds)){
            return moduleIds.split(",");
        }
        return new String[]{};
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Map<String, Object>> getShareModules() {
        return shareModules;
    }

    public void setShareModules(List<Map<String, Object>> shareModules) {
        this.shareModules = shareModules;
    }
}
