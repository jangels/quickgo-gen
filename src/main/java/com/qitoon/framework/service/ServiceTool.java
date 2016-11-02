package com.qitoon.framework.service;


import com.qitoon.framework.model.User;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.utils.AssertUtils;
import com.qitoon.framework.utils.MemoryUtils;

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
