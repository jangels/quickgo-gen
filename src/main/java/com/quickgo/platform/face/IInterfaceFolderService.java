package com.quickgo.platform.face;

import com.quickgo.platform.model.InterfaceFolder;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IInterfaceFolderService {

    List<InterfaceFolder> getFoldersByProjectId(String projectId);

    int create(InterfaceFolder interfaceFolder);

    int upadteFolder(InterfaceFolder interfaceFolder);

    int deleteFolder(String id);

    InterfaceFolder getById(String id);

    InterfaceFolder getByMid(String mid);



    List<InterfaceFolder> getFoldersByModuleIds(String[] moduleIds);


}
