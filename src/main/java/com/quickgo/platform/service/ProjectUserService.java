package com.quickgo.platform.service;

import com.quickgo.platform.dao.ProjectUserMapper;
import com.quickgo.platform.face.IProjectUserService;
import com.quickgo.platform.model.ProjectUser;
import com.quickgo.platform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectUserService implements IProjectUserService {

    @Autowired
    private ProjectUserMapper projectUserMapper;


    @Override
    public boolean checkUserHasProjectPermission(String userId, String projectId) {
        return projectUserMapper.checkProjectUser(userId,projectId)>1?false:true;
    }

    @Override
    public String getProjectEditable(String userId, String projectId) {
        return projectUserMapper.getProjectEditAble(userId,projectId);
    }

    @Override
    public List<User> getUsersByProjectId(String projectId) {
        return projectUserMapper.getUsersByProjectId(projectId);
    }

    @Override
    public int createProjectUser(ProjectUser projectUser) {
        return projectUserMapper.insert(projectUser);
    }

    @Override
    public boolean checkProjectUserExists(String userId, String projectId) {
        return this.checkUserHasProjectPermission(userId,projectId);
    }

    @Override
    public int deleteProjectUser(String projectId, String userId) {
        return projectUserMapper.deleteProjectUser(projectId,userId);
    }

    @Override
    public int updateProjectUserEditable(String projectId, String userId, String editable) {
        return projectUserMapper.updateProjectUserEditable(projectId,userId,editable);
    }

    @Override
    public int updateCommonlyUsedProject(String projectId, String userId, String isCommonlyUsed) {
        return projectUserMapper.updateCommonlyUsedProject(projectId,userId,isCommonlyUsed);
    }


}
