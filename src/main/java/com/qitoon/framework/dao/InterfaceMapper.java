package com.qitoon.framework.dao;

import com.qitoon.framework.model.Interface;

import java.util.List;


public interface InterfaceMapper extends BaseDao<Interface> {

     List<Interface> getInterfacesByProjectId(String projectId);

     List<Interface> getInterfacesByModuleIds(String[] moduleIds);

     List<Interface> getInterface(String folderId);


}
