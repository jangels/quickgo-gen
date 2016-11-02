package com.quickgo.platform.controller;


import com.quickgo.platform.asynctask.AsyncTaskBus;
import com.quickgo.platform.asynctask.Log;
import com.quickgo.platform.face.IInterfaceFolderService;
import com.quickgo.platform.face.IProjectUserService;
import com.quickgo.platform.model.InterfaceFolder;
import com.quickgo.platform.model.Project;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.utils.AssertUtils;
import com.quickgo.platform.utils.MemoryUtils;
import com.quickgo.platform.utils.Validate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author huangjie
 * @since  2016-07-14
 */
@RestController
@RequestMapping("/interfacefolder")
public class InterfaceFolderController {

    @Autowired
    private IInterfaceFolderService interfaceFolderService;
    @Autowired
    private IProjectUserService projectUserService;

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") String id,String token){
        AssertUtils.notNull(id,"无效的id");
        InterfaceFolder folder = interfaceFolderService.getById(id);
        AssertUtils.isTrue(projectUserService.checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(),folder.getProjectId()),"无操作权限");
        AssertUtils.notNull(folder,"该分类不存在或已删除");
        int rs = interfaceFolderService.deleteFolder(id);
        AssertUtils.isTrue(rs>0,"操作失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.DELETE_FOLDER,folder.getName(),folder.getProjectId()));
        AsyncTaskBus.instance().push(folder.getProjectId(), Project.Action.DELETE_FOLDER,folder.getId(),token);
        return Result.returnInfo(rs);
    }
    @RequestMapping("/{id}")
    public Object get(@PathVariable("id") String id){
        InterfaceFolder folder = interfaceFolderService.getById(id);
        AssertUtils.notNull(folder,"无效id");
        return Result.returnInfo(new _HashMap<>().add("folder",folder));
    }


    @RequestMapping("/create")
    public Object create(String token,InterfaceFolder folder) {
        folder.setCreateTime(new Date().getTime());
        folder.setId(Validate.id());
        AssertUtils.notNull(folder,"接口对象为空");
        AssertUtils.notNull(folder.getModuleId(),"missing moduleId");
        AssertUtils.notNull(folder.getProjectId(),"missing projectId");
        boolean isTrue = projectUserService.checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(),folder.getProjectId());
        AssertUtils.isTrue(isTrue,"无操作权限");
        int rs = interfaceFolderService.create(folder);
        AssertUtils.isTrue(rs>0,"操作失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.CREATE_FOLDER,folder.getName(),folder.getProjectId()));
        AsyncTaskBus.instance().push(folder.getProjectId(), Project.Action.CREATE_FOLDER,folder.getId(),token,folder.getModuleId());
        return Result.returnInfo(folder.getId());
    }

    @RequestMapping("/update/{id}")
    public Object update(@PathVariable("id") String id,String token,InterfaceFolder folder){
        InterfaceFolder temp = interfaceFolderService.getById(id);
        AssertUtils.notNull(temp,"无效id");
        boolean isTrue = projectUserService.checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(),temp.getProjectId());
        AssertUtils.isTrue(isTrue,"无操作权限");
        AssertUtils.notNull(folder,"参数丢失");
        int rs = interfaceFolderService.upadteFolder(folder);
        AssertUtils.isTrue(rs>0,"操作失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.UPDATE_FOLDER,folder.getName(),folder.getProjectId()));
        AsyncTaskBus.instance().push(temp.getProjectId(), Project.Action.UPDATE_FOLDER,temp.getId(),token);
        return Result.returnInfo(rs);
    }

    @RequestMapping("/save")
    public Object save(String id,String token, InterfaceFolder interfaceFolder){
        if(StringUtils.isEmpty(id))
            return create(id,interfaceFolder);
        return update(id,token,interfaceFolder);
    }


}
