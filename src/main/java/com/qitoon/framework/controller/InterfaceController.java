package com.qitoon.framework.controller;


import com.qitoon.framework.asynctask.AsyncTaskBus;
import com.qitoon.framework.asynctask.Log;
import com.qitoon.framework.face.IInterfaceService;
import com.qitoon.framework.face.IProjectUserService;
import com.qitoon.framework.model.Interface;
import com.qitoon.framework.model.Project;
import com.qitoon.framework.param.Result;
import com.qitoon.framework.param._HashMap;
import com.qitoon.framework.utils.AssertUtils;
import com.qitoon.framework.utils.MemoryUtils;
import com.qitoon.framework.utils.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author : huangjie
 * @since : 16/7/13
 */
@RestController
@RequestMapping("/interface")
public class InterfaceController {

    @Autowired
    private IInterfaceService interfaceService;
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
//    @RequestMapping("/create")
    public Object createInterface(String token,Interface in) {
        Interface anInterface =  new Interface();
        BeanUtils.copyProperties(in,anInterface);
        AssertUtils.notNull(in.getModuleId(), "missing moduleId");
        AssertUtils.notNull(in.getProjectId(), "missing projectId");
        AssertUtils.notNull(in.getFolderId(), "missing folderId");
        AssertUtils.isTrue(projectUserService.
        checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(), in.getProjectId()), "无操作权限");
        in.setLastUpdateTime(new Date());
        in.setCreateTime(new Date());
        in.setStatus(Interface.Status.ENABLE);
        in.setId(Validate.id());
        int rs = interfaceService.create(in);
        AssertUtils.isTrue(rs > 0, "增加失败");
        AsyncTaskBus.instance().push(Log.create(token, Log.CREATE_INTERFACE, in.getName(), in.getProjectId()));
        AsyncTaskBus.instance().push(in.getProjectId(), Project.Action.CREATE_INTERFACE, in.getId(), token, in.getFolderId());
        return Result.returnInfo(in.getId());
    }

    /**
     * 更新
     *
     * @param id        id
     * @param in
     * @return
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
        in.setLastUpdateTime(new Date());
        in.setCreateTime(null);
        int rs = interfaceService.upadteInterface(in);
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
        if (org.apache.commons.lang3.StringUtils.isEmpty(id)) {
            return createInterface(token,in);
        }
        return update(id, token,in);
    }
}
