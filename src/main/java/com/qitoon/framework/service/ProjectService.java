package com.qitoon.framework.service;

import com.qitoon.framework.dao.ProjectMapper;
import com.qitoon.framework.face.IProjectService;
import com.qitoon.framework.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;


    @Override
    public List<Project> getProjects(String userId) {
        return projectMapper.getProjectsByUserId(userId);
    }

    @Override
    public Project getProject(String id) {
        return projectMapper.selectById(id);
    }

    @Override
    public int createProject(Project project) {
        return projectMapper.insert(project);
    }

    @Override
    public int updateProject(Project project) {
        return projectMapper.updateById(project);
    }

    @Override
    public int deleteProject(String id) {
        return projectMapper.deleteById(id);
    }

    @Override
    public String getProjectName(String projectId) {
        return projectMapper.getProjectName(projectId);
    }


}
