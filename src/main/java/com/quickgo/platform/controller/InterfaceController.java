package com.quickgo.platform.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickgo.platform.asynctask.AsyncTaskBus;
import com.quickgo.platform.asynctask.Log;
import com.quickgo.platform.face.IInterfaceFolderService;
import com.quickgo.platform.face.IInterfaceService;
import com.quickgo.platform.face.IProjectUserService;
import com.quickgo.platform.model.Interface;
import com.quickgo.platform.model.InterfaceFolder;
import com.quickgo.platform.model.Project;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.RequestArgs;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.utils.AssertUtils;
import com.quickgo.platform.utils.MemoryUtils;
import com.quickgo.platform.utils.StringUtils;
import com.quickgo.platform.utils.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * create by huangjie
 * on 16/7/13
 */
@RestController
@RequestMapping("/interface")
public class InterfaceController {

    @Autowired
    private IInterfaceService interfaceService;
    @Autowired
    private IInterfaceFolderService interfaceFolderService;
    @Autowired
    private IProjectUserService projectUserService;


    @RequestMapping("/{id}")
    public Object get(@PathVariable("id") String id) {
        Interface in = interfaceService.getById(id);
        return Result.returnInfo(new _HashMap<>().add("interface", in));
    }

    /**
     * 新增
     *
     * @param in
     * @return
     */
    public Object createInterface(String token,Interface in) {
        Interface anInterface =  new Interface();
        BeanUtils.copyProperties(in,anInterface);
        AssertUtils.notNull(in.getModuleId(), "missing moduleId");
        AssertUtils.notNull(in.getProjectId(), "missing projectId");
        AssertUtils.notNull(in.getFolderId(), "missing folderId");
        AssertUtils.isTrue(projectUserService.
        checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(), in.getProjectId()), "无操作权限");
        in.setCreateTime(new Date().getTime());
        in.setStatus(Interface.Status.ENABLE);
        in.setId(Validate.id());
        //解析参数
        int rs = interfaceService.create(this.analyticParameter(in));
        AssertUtils.isTrue(rs > 0, "增加失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.CREATE_INTERFACE, in.getName(), in.getProjectId()));
        AsyncTaskBus.instance().push(in.getProjectId(), Project.Action.CREATE_INTERFACE, in.getId(), token, in.getFolderId());
        return Result.returnInfo(in.getId());
    }

    /**
     * 更新
     *
     * @param id id
     * @param in Interface
     * @return Object
     */
    @RequestMapping("/update/{id}")
    public Object update(@PathVariable String id,String token, Interface in) {
        AssertUtils.notNull(id, "missing id");
        Interface temp = interfaceService.getById(id);
        AssertUtils.notNull(temp, "接口不存在或已删除");
        Interface anInterface = new Interface();
        BeanUtils.copyProperties(in, anInterface);
        in.setId(id);
        AssertUtils.isTrue(projectUserService.checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(), temp.getProjectId()), "无操作权限");
        in.setLastUpdateTime(new Date().getTime());
        int rs = interfaceService.upadteInterface(this.analyticParameter(in));
        AssertUtils.isTrue(rs > 0, "修改失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.UPDATE_INTERFACE, temp.getName(), temp.getProjectId()));
        AsyncTaskBus.instance().push(temp.getProjectId(), Project.Action.UPDATE_INTERFACE, id, token);
        return Result.returnInfo(rs);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @param token token
     * @return Object
     */
    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable("id") String id, String token) {
        AssertUtils.notNull(id, "missing id");
        Interface temp = interfaceService.getById(id);
        AssertUtils.notNull(temp, "接口不存在或已删除");
        AssertUtils.isTrue(projectUserService.checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(), temp.getProjectId()), "无操作权限");
        int rs = interfaceService.deleteInterface(id);
        AssertUtils.isTrue(rs > 0, "删除失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.DELETE_INTERFACE, temp.getName(), temp.getProjectId()));
        AsyncTaskBus.instance().push(temp.getProjectId(), Project.Action.DELETE_INTERFACE, id, token);
        return Result.returnInfo(rs);
    }

    /**
     * 根据文件夹名称删除
     * @param moduleId
     * @param folderId
     * @param parameter
     * @return
     */
   /* @Delete("{moduleId}/folder/{folderId}")
    public int deleteFolder(@RequestParam("moduleId")String moduleId,@RequestParam("folderId")String folderId,Parameter parameter){
        int rs = ServiceFactory.instance().deleteInterface(moduleId,folderId);
        AssertUtils.isTrue(rs>0,"删除失败");
        return rs;
    }
*/

    /**
     * 保存
     *
     * @param in
     * @return
     */
    @RequestMapping("/save")
    public Object save(String id,String token, Interface in) {
        if (StringUtils.isEmpty(id)) {
            return createInterface(token,in);
        }
        return update(id, token,in);
    }

    @RequestMapping(value = "/getInterfacesByMid/{moduleId}")
    public Object getInterfaces(@PathVariable String moduleId,String token){
        User user = MemoryUtils.getUser(token);
        if(null==user){
            return Result.fail("无权限，请登录");
        }
        if(StringUtils.isEmpty(moduleId)){
            return Result.fail("moduleId 为空");
        }
        InterfaceFolder interfaceFolder = interfaceFolderService.getByMid(moduleId);
        if(null==interfaceFolder){
            return Result.fail("module 数据为空");
        }
        List<Interface> interfaces = interfaceService.getInterfaces(interfaceFolder.getModuleId());
        return Result.returnInfo(new _HashMap<>().add("interfaces", interfaces).add("interfaceFolder",interfaceFolder));
    }

    //解析参数
    private Interface analyticParameter(Interface in) {
        if (StringUtils.isNotEmpty(in.getRequestArgs()) || StringUtils.isNotEmpty(in.getTableName())) {
            //将入参json转化为对象
            ObjectMapper mapper = new ObjectMapper();
            List<RequestArgs> beanList = null;
            try {
                beanList = mapper.readValue(in.getRequestArgs(), new TypeReference<List<RequestArgs>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (CollectionUtils.isEmpty(beanList)) {
                return in;
            } else {
                return this.convertParam(in, beanList);
            }
        }
        return in;
    }

    private Interface convertParam(Interface in,  List<RequestArgs> beanList) {
        StringBuilder inputParam = new StringBuilder();
        StringBuilder putParam = new StringBuilder();
        //提取入参
        for (RequestArgs requesArgs : beanList) {
            if (requesArgs.getType().contains("object")) {
                //  首字母转化为大写
                inputParam.append(StringUtils.toUpperCaseFirstOne(in.getTableName()));
                inputParam.append(" " + StringUtils.toUnderScoreCase(in.getTableName()) + ",");
                putParam.append(" "+StringUtils.toUnderScoreCase(in.getTableName()));
            } else {
                inputParam.append("String " + requesArgs.getName() + ",");
                putParam.append(" "+requesArgs.getName());
            }
        }
        in.setInputParam(inputParam.deleteCharAt(inputParam.length()-1).toString());
        in.setTableName(StringUtils.toCapitalizeCamelCase(in.getTableName()));
        in.setPutParam(putParam.toString());
        return in;
    }


}
