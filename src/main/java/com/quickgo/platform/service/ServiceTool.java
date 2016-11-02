package com.quickgo.platform.service;


import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.utils.AssertUtils;
import com.quickgo.platform.utils.MemoryUtils;

/**
 * @author huangjie
 * @since  2016-10-20
 */
public class ServiceTool {

    public static void checkUserHasEditPermission(String projectId,Parameter parameter){
        User user = MemoryUtils.getUser(parameter);
        AssertUtils.notNull(user,"无操作权限");
        boolean permission = true;//ServiceFactory.instance().checkUserHasProjectPermission(user.getId(),projectId);
        AssertUtils.isTrue(permission,"无操作权限");
    }
}
