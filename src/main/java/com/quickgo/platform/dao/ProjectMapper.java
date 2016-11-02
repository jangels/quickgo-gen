package com.quickgo.platform.dao;

import com.quickgo.platform.model.Project;

import java.util.List;


public interface ProjectMapper extends BaseDao<Project> {

     List<Project> getProjectsByUserId(String userId);

     String getProjectName(String projectId);


}
