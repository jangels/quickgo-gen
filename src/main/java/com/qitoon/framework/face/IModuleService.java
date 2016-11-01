package com.qitoon.framework.face;

import com.qitoon.framework.model.Module;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IModuleService {

    Module getById(String id);

    int createModule(Module module);

    int updateModule(Module module);

    int deleteModule(String id);

    List<Module> getModules(String projectId);

    List<Module> getModuleList(String[] moduleIdsArray);

}
