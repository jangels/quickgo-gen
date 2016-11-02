package com.quickgo.platform.controller;

import com.quickgo.platform.annotation.Get;
import com.quickgo.platform.face.IProjectService;
import com.quickgo.platform.model.Project;
import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.utils.ConfigUtils;
import com.quickgo.platform.utils.MemoryUtils;
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
