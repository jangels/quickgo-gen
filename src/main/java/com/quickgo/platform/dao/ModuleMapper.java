package com.quickgo.platform.dao;

import com.quickgo.platform.model.Module;

import java.util.List;


public interface ModuleMapper extends BaseDao<Module> {

     Module getModuleById(String id);

     List<Module> getModules(String projectId);

     List<Module> getModuleList(String[] moduleIdsArray);


}
