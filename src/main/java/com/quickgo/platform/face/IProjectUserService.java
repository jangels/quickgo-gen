package com.quickgo.platform.face;

import com.quickgo.platform.model.ProjectUser;
import com.quickgo.platform.model.User;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IProjectUserService {

    boolean checkUserHasProjectPermission(String userId,String projectId);

    String getProjectEditable(String userId,String projectId);

    List<User> getUsersByProjectId(String projectId);

    int createProjectUser(ProjectUser projectUser);

    boolean checkProjectUserExists(String userId,String projectId);

    int deleteProjectUser(String projectId,String userId);

    int updateProjectUserEditable(String projectId, String userId, String editable);

    int updateCommonlyUsedProject(String projectId, String userId, String isCommonlyUsed);

}
