package com.qitoon.framework.dao;

import com.qitoon.framework.model.ProjectUser;
import com.qitoon.framework.model.User;
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
