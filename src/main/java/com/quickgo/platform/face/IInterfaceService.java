package com.quickgo.platform.face;

import com.quickgo.platform.model.Interface;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IInterfaceService {

    List<Interface> getInterfacesByProjectId(String projectId);

    int create(Interface interfaces);

    int upadteInterface(Interface interfaces);

    int deleteInterface(String id);

    Interface getById(String id);

    List<Interface> getInterface(String folderId);

    List<Interface> getInterfaces(String moduleId);

    List<Interface> getInterfacesByModuleIds(String[] moduleIds);

    List<Interface> getInterfacesByIds(List<String> ids);



}
