package com.quickgo.platform.controller;


import com.quickgo.platform.asynctask.AsyncTaskBus;
import com.quickgo.platform.asynctask.Log;
import com.quickgo.platform.face.IModuleService;
import com.quickgo.platform.face.IProjectService;
import com.quickgo.platform.model.Module;
import com.quickgo.platform.model.Project;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.utils.AssertUtils;
import com.quickgo.platform.utils.MemoryUtils;
import com.quickgo.platform.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

/**
 * @author huangjie
 * @since  2016-05-25
 */
@RestController
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private IModuleService moduleService;
    @Autowired
    private IProjectService projectService;

    @RequestMapping("/{id}")
    public Object id(@PathVariable("id") String id){
        if(StringUtils.isEmpty(id)){
            return null;
        }
        Module module = moduleService.getById(id);
        AssertUtils.notNull(module,"无效id");
        return new _HashMap<>().add("module",module);
    }

    /**
     * 创建m模块
     * @param module 模块
     * @return  Object
     * @throws IOException  异常
     */
    @RequestMapping("/create")
    public Object createModule(String token,Module module) throws IOException {
        AssertUtils.notNull(module, "数据为空");
        AssertUtils.notNull(module.getName(), "分类名为空");
        AssertUtils.notNull(module.getProjectId(), "项目id为空");
        checkUserHasOperatePermission(module.getProjectId(),token);
        module.setId(Validate.id());
        module.setLastUpdateTime(new Date().getTime());
        module.setCreateTime(new Date().getTime());
        int rs = moduleService.createModule(module);
        AssertUtils.isTrue(rs > 0, "操作失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.CREATE_MODULE,module.getName(),module.getProjectId()));
        AsyncTaskBus.instance().push(module.getProjectId(), Project.Action.CREATE_MODULE,module.getId(),token);
        return module.getId();
    }

    /**
     * 更新module
     * @param id id
     * @return int
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public Object updateModule(@PathVariable("id") String id,String token,Module module) throws IOException {
        Module temp = moduleService.getById(id);
        AssertUtils.notNull(temp,"无效id");
        checkUserHasOperatePermission(temp.getProjectId(),token);
        if (module == null) {
            module = new Module();
        }
        module.setId(id);
        module.setLastUpdateTime(new Date().getTime());
        int rs = moduleService.updateModule(module);
        AssertUtils.isTrue(rs > 0, "操作失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.UPDATE_MODULE,module.getName(),module.getProjectId()));
        AsyncTaskBus.instance().push(temp.getProjectId(), Project.Action.UPDATE_MODULE,temp.getId(),token);
        return Result.returnInfo(rs);
    }

    /**
     * 删除module
     * @return Object
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Object deleteModule(@PathVariable("id") String id, String token) {
        Module module = moduleService.getById(id);
        AssertUtils.notNull(module,"无效id");
        this.checkUserHasOperatePermission(module.getProjectId(),token);
        AssertUtils.notNull(id, "id为空");
        int rs = moduleService.deleteModule(id);
        AssertUtils.isTrue(rs > 0, "操作失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.DELETE_MODULE,module.getName(),module.getProjectId()));
        AsyncTaskBus.instance().push(module.getProjectId(), Project.Action.DELETE_MODULE,module.getId(),token);
        return Result.returnInfo(rs);
    }

    private void checkUserHasOperatePermission(String projectId,String token){
        User user = MemoryUtils.getUser(token);
        AssertUtils.notNull(user,"无操作权限");
        Project project = projectService.getProject(projectId);
        AssertUtils.notNull(project,"项目不存在");
        AssertUtils.isTrue(user.getId().equals(project.getUserId()),"无操作权限");
    }



}
