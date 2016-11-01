package com.qitoon.framework.dao;

import com.qitoon.framework.model.InterfaceFolder;

import java.util.List;


public interface InterfaceFolderMapper extends BaseDao<InterfaceFolder> {

     List<InterfaceFolder> getFoldersByProjectId(String projectId);

     List<InterfaceFolder> getFoldersByModuleIds(String[] moduleIds);


}
