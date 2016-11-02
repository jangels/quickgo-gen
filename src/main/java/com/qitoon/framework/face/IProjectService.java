package com.qitoon.framework.face;

import com.qitoon.framework.model.Project;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IProjectService {

    List<Project> getProjects(String userId);

    Project getProject(String id);

    int createProject(Project project);

    int updateProject(Project project);

    int deleteProject(String id);

    String getProjectName(String projectId);

}
