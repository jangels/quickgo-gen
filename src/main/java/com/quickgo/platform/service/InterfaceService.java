package com.quickgo.platform.service;

import com.quickgo.platform.dao.InterfaceMapper;
import com.quickgo.platform.face.IInterfaceService;
import com.quickgo.platform.model.Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InterfaceService implements IInterfaceService {

    @Autowired
    private InterfaceMapper interfaceMapper;



    @Override
    public List<Interface> getInterfacesByProjectId(String projectId) {
        return interfaceMapper.getInterfacesByProjectId(projectId);
    }

    @Override
    public int create(Interface interfaces) {
        return interfaceMapper.insert(interfaces);
    }

    @Override
    public int upadteInterface(Interface interfaces) {
        return interfaceMapper.updateById(interfaces);
    }

    @Override
    public int deleteInterface(String id) {
        return interfaceMapper.deleteById(id);
    }

    @Override
    public Interface getById(String id) {
        return interfaceMapper.selectById(id);
    }

    @Override
    public List<Interface> getInterface(String folderId) {
        return interfaceMapper.getInterface(folderId);
    }

    @Override
    public List<Interface> getInterfacesByModuleIds(String[] moduleIds) {
        return interfaceMapper.getInterfacesByModuleIds(moduleIds);
    }
}
