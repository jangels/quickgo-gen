package com.qitoon.framework.dao;

import com.qitoon.framework.model.Module;

import java.util.List;


public interface ModuleMapper extends BaseDao<Module> {

     Module getModuleById(String id);

     List<Module> getModules(String projectId);

     List<Module> getModuleList(String[] moduleIdsArray);


}
