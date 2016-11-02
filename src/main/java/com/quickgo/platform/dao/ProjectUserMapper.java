package com.quickgo.platform.dao;

import com.quickgo.platform.model.ProjectUser;
import com.quickgo.platform.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProjectUserMapper extends BaseDao<ProjectUser> {

     int checkProjectUser(@Param("userId") String userId, @Param("projectId") String projectId);

     String getProjectEditAble(@Param("userId") String userId, @Param("projectId") String projectId);

     List<User> getUsersByProjectId(String projectId);

     int deleteProjectUser( @Param("projectId") String projectId,@Param("userId") String userId);

     int updateProjectUserEditable(@Param("userId") String userId, @Param("projectId") String projectId,@Param("editable")String editable);

     int updateCommonlyUsedProject(@Param("userId") String userId, @Param("projectId") String projectId,@Param("isCommonlyUsed") String isCommonlyUsed);
}
