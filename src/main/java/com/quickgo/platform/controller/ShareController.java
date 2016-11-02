package com.quickgo.platform.controller;


import com.quickgo.platform.annotation.Post;
import com.quickgo.platform.exception.Handler;
import com.quickgo.platform.face.*;
import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.service.ServiceTool;
import com.quickgo.platform.model.*;
import com.quickgo.platform.utils.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huangjie
 * @since  2016-10-20
 */
@RestController
@RequestMapping("/share")
public class ShareController {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private IInterfaceFolderService interfaceFolderService;
    @Autowired
    private IInterfaceService interfaceService;
    @Autowired
    private IShareService shareService;

    @Ignore
//    @Get("/{id}/view")
    @RequestMapping("/{id}/view")
    public Object get(@PathVariable("id")String id, Parameter parameter){
        Share share =null;// ServiceFactory.instance().getShare(id);
        AssertUtils.notNull(share,"该分享不存在或已删除");


        String code = parameter.getParamString().get("code");

        //如果需要密码
        if(org.apache.commons.lang3.StringUtils.isNotBlank(share.getPassword())){
            //表示输入了密码
             if(org.apache.commons.lang3.StringUtils.isNotBlank(code)){
                if(!code.equals(share.getPassword())){
                    return new Result<>(-3,"密码错误");
                }
             }else{ //表示需要输入密码
                 return new _HashMap<>().add("share",true).add("needPassword",true);
             }
        }

        //获取项目
        Project project = projectService.getProject(share.getProjectId());
        if(project == null)
            return new _HashMap<>().add("project", "");
        //获取模块
        List<Module> modules = null;
        List<InterfaceFolder> folders = null;
        List<Interface> interfaces = null;
        if(Share.ShareAll.YES.equals(share.getShareAll())) {
            modules = moduleService.getModules(share.getProjectId());
            //获取该项目下所有文件夹
            folders = interfaceFolderService.getFoldersByProjectId(share.getProjectId());
            //获取该项目下所有接口
            interfaces = interfaceService.getInterfacesByProjectId(project.getId());
        }else{
            String[] moduleIds = share.getModuleIdsArray();
            modules = moduleService.getModuleList(moduleIds);
            folders = interfaceFolderService.getFoldersByModuleIds(moduleIds);
            interfaces = interfaceService.getInterfacesByModuleIds(moduleIds);
        }

        Map<String, List<InterfaceFolder>> folderMap = ResultUtils.listToMap(folders, new Handler<InterfaceFolder>() {
            @Override
            public String key(InterfaceFolder item) {
                return item.getModuleId();
            }
        });
        for (Module module : modules) {
            List<InterfaceFolder> temp = folderMap.get(module.getId());
            if (temp != null) {
                module.setFolders(temp);
            }
        }

        Map<String, List<Interface>> interMap = ResultUtils.listToMap(interfaces, new Handler<Interface>() {
            @Override
            public String key(Interface item) {
                return item.getFolderId();
            }
        });
        for (InterfaceFolder folder : folders) {
            List<Interface> temp = interMap.get(folder.getId());
            if (temp != null) {
                folder.setChildren(temp);
            }
        }
        return new _HashMap<>()
                .add("modules", modules)
                .add("project", project)
                .add("share",true)
                ;
    }

    /**
     * 新增
     * @param parameter
     * @return
     */
    @Post
    public Object create(Parameter parameter){
        String moduleId = parameter.getParamString().get("moduleId");

        String projectId = parameter.getParamString().get("projectId");
        AssertUtils.notNull(projectId,"项目id为空");
        ServiceTool.checkUserHasEditPermission(projectId,parameter);

        Share share = new Share();
        share.setId(Validate.id());
        share.setModuleIds(moduleId);
        share.setName(parameter.getParamString().get("name"));
        if(org.apache.commons.lang3.StringUtils.isBlank(share.getName())){
            share.setName(DateUtils.toStr(new Date()));
        }
        share.setPassword(parameter.getParamString().get("password"));
        User user = MemoryUtils.getUser(parameter);
        share.setUserId(user.getId());
        share.setShareAll(parameter.getParamString().get("shareAll"));
        if(!Share.ShareAll.YES.equals(share.getShareAll())){
            AssertUtils.notNull(moduleId,"模块id为空");
        }
        share.setCreateTime(new Date());
        share.setProjectId(projectId);
        int rs = shareService.create(share);
        AssertUtils.isTrue(rs>0,"操作失败");
        return rs;
    }

    /**
     * 删除
     * @param id
     * @param parameter
     * @return
     */
    @Delete("/{id}")
    public Object delete(@RequestParam("id")String id, Parameter parameter){
        Share share = shareService.getShare(id);
        AssertUtils.notNull(share,"该数据不存在");
        ServiceTool.checkUserHasEditPermission(share.getProjectId(),parameter);
        //TODO
        int rs =0;// ServiceFactory.instance().delete(SqlUtils.getTableName(Share.class),id);
        AssertUtils.isTrue(rs>0,"操作失败");
        return share;
    }
    /**
     * 修改
     * @param id
     * @param parameter
     * @return
     */
    @Post("/{id}")
    public Object update(@RequestParam("id")String id, Parameter parameter){
        Share share = shareService.getShare(id);
        AssertUtils.notNull(share,"该数据不存在");
        ServiceTool.checkUserHasEditPermission(share.getProjectId(),parameter);
        share = BeanCopy.convert(Share.class,parameter.getParamString());
        share.setId(id);
        int rs = shareService.updateShare(share);
        AssertUtils.isTrue(rs>0,"操作失败");
        return share;
    }


}
