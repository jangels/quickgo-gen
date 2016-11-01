package com.qitoon.framework.controller;

import com.qitoon.framework.annotation.Get;
import com.qitoon.framework.face.IProjectService;
import com.qitoon.framework.model.Project;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.param._HashMap;
import com.qitoon.framework.utils.ConfigUtils;
import com.qitoon.framework.utils.MemoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author huangjie
 * @since  2016-07-20
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private IProjectService projectService;


    @Get(template = "/dashboard")
    public Object get(Parameter parameter) {
        // 获取团队
        String userId = MemoryUtils.getUser(parameter).getId();
        List<Project> projects = projectService.getProjects(userId);

        return new _HashMap<>().add("fileAccess", ConfigUtils.getFileAccessURL())
                .add("projects", projects);
    }
}
