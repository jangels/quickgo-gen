package com.quickgo.platform.dao;

import com.quickgo.platform.model.Interface;

import java.util.List;


public interface InterfaceMapper extends BaseDao<Interface> {

     List<Interface> getInterfacesByProjectId(String projectId);

     List<Interface> getInterfacesByModuleIds(String[] moduleIds);

     List<Interface> getInterfacesByIds(List<String> ids);

     List<Interface> getInterface(String folderId);

     List<Interface> getInterfaces(String moduleId);


}
