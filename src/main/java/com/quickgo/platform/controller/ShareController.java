package com.quickgo.platform.controller;


import com.quickgo.platform.exception.Handler;
import com.quickgo.platform.face.*;
import com.quickgo.platform.model.*;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.service.ServiceTool;
import com.quickgo.platform.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/{id}/view")
    public Object get(@PathVariable("id")String id,String code, Share share){
//        Share share =null;// ServiceFactory.instance().getShare(id);
        AssertUtils.notNull(share,"该分享不存在或已删除");


        //如果需要密码
        if(StringUtils.isNotBlank(share.getPassword())){
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
     * @param share share
     * @return
     */
    @RequestMapping("/create")
    public Object create(String moduleId,String token,Share share){
        AssertUtils.notNull(share.getProjectId(),"项目id为空");
        ServiceTool.checkUserHasEditPermission(share.getProjectId(),token);

        share.setId(Validate.id());
        share.setModuleIds(moduleId);
        if(org.apache.commons.lang3.StringUtils.isBlank(share.getName())){
            share.setName(DateUtils.toStr(new Date()));
        }
        User user = MemoryUtils.getUser(token);
        share.setUserId(user.getId());
        if(!Share.ShareAll.YES.equals(share.getShareAll())){
            AssertUtils.notNull(moduleId,"模块id为空");
        }
        share.setCreateTime(new Date().getTime());
        int rs = shareService.create(share);
        AssertUtils.isTrue(rs>0,"操作失败");
        return rs;
    }

    /**
     * 删除
     * @param id id
     * @param token token
     * @return Object
     */
    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable("id")String id,String token){
        Share share = shareService.getShare(id);
        AssertUtils.notNull(share,"该数据不存在");
        ServiceTool.checkUserHasEditPermission(share.getProjectId(),token);
        //TODO
        int rs =0;// ServiceFactory.instance().delete(SqlUtils.getTableName(Share.class),id);
        AssertUtils.isTrue(rs>0,"操作失败");
        return share;
    }
    /**
     * 修改
     * @param id id
     * @param token token
     * @return Object
     */
    @RequestMapping("/update/{id}")
    public Object update(@PathVariable("id")String id,String token){
        Share share = shareService.getShare(id);
        AssertUtils.notNull(share,"该数据不存在");
        ServiceTool.checkUserHasEditPermission(share.getProjectId(),token);
        share.setId(id);
        int rs = shareService.updateShare(share);
        AssertUtils.isTrue(rs>0,"操作失败");
        return share;
    }


}
