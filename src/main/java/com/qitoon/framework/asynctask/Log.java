package com.qitoon.framework.asynctask;


import com.qitoon.framework.model.ProjectLog;
import com.qitoon.framework.model.User;
import com.qitoon.framework.utils.MemoryUtils;
import com.qitoon.framework.utils.Validate;

import java.util.Date;

/**
 * @author huangjie
 * @since  2016-09-13
 */
public class Log implements AsyncTask<ProjectLog> {
    public static final String CREATE_PROJECT="project.create";
    public static final String UPDATE_PROJECT="project.update";
    public static final String DELETE_PROJECT="project.delete";
    public static final String TRANSFER_PROJECT="project.transfer";
    public static final String EXPORT_PROJECT="project.export";

    public static final String CREATE_FOLDER="folder.create";
    public static final String UPDATE_FOLDER="folder.update";
    public static final String DELETE_FOLDER="folder.delete";
    public static final String MOVE_FOLDER="folder.move";
    public static final String COPY_FOLDER="folder.copy";


    public static final String MOVE_INTERFACE="interface.move";
    public static final String COPY_INTERFACE="interface.copy";
    public static final String CREATE_INTERFACE="interface.create";
    public static final String UPDATE_INTERFACE="interface.update";
    public static final String DELETE_INTERFACE="interface.delete";


    public static final String CREATE_MODULE="module.copy";
    public static final String UPDATE_MODULE="module.update";
    public static final String DELETE_MODULE="module.delete";




    public static ProjectLog create(String token,String action,String name,String projectId){
        ProjectLog log = new ProjectLog();
        log.setId(Validate.id());
        User user = MemoryUtils.getUser(token);
        if(user!=null) {
            log.setUserId(user.getId());
        }
        log.setCreateTime(new Date());
        log.setAction(action);
        log.setLog(name);
        log.setProjectId(projectId);
        return log;
    }

    @Override
    public void push(ProjectLog log) {
        //TODO
//        ServiceFactory.instance().create(log);
    }
}
