package com.qitoon.framework.dao;

import com.qitoon.framework.model.Project;

import java.util.List;


public interface ProjectMapper extends BaseDao<Project> {

     List<Project> getProjectsByUserId(String userId);

     String getProjectName(String projectId);


}
