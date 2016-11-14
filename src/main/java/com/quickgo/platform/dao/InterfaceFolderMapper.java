package com.quickgo.platform.dao;

import com.quickgo.platform.model.InterfaceFolder;

import java.util.List;


public interface InterfaceFolderMapper extends BaseDao<InterfaceFolder> {

     List<InterfaceFolder> getFoldersByProjectId(String projectId);

     List<InterfaceFolder> getFoldersByModuleIds(String[] moduleIds);

     InterfaceFolder getByMid(String mid);

}
