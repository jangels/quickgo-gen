package com.quickgo.platform.service;

import com.quickgo.platform.dao.InterfaceFolderMapper;
import com.quickgo.platform.face.IInterfaceFolderService;
import com.quickgo.platform.model.InterfaceFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InterfaceFolderService implements IInterfaceFolderService {

    @Autowired
    private InterfaceFolderMapper interfaceFolderMapper;


    @Override
    public List<InterfaceFolder> getFoldersByProjectId(String projectId) {
        return interfaceFolderMapper.getFoldersByProjectId(projectId);
    }

    @Override
    public int create(InterfaceFolder interfaceFolder) {
        return interfaceFolderMapper.insert(interfaceFolder);
    }

    @Override
    public int upadteFolder(InterfaceFolder interfaceFolder) {
        return interfaceFolderMapper.updateById(interfaceFolder);
    }

    @Override
    public int deleteFolder(String id) {
        return interfaceFolderMapper.deleteById(id);
    }

    @Override
    public InterfaceFolder getById(String id) {
        return interfaceFolderMapper.selectById(id);
    }

    @Override
    public InterfaceFolder getByMid(String mid) {
        return interfaceFolderMapper.getByMid(mid);
    }

    @Override
    public List<InterfaceFolder> getFoldersByModuleIds(String[] moduleIds) {
        return interfaceFolderMapper.getFoldersByModuleIds(moduleIds);
    }
}
