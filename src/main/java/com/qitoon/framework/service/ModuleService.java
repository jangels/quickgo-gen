package com.qitoon.framework.service;

import com.qitoon.framework.dao.ModuleMapper;
import com.qitoon.framework.face.IModuleService;
import com.qitoon.framework.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ModuleService implements IModuleService {

    @Autowired
    private ModuleMapper moduleMapper;


    @Override
    public Module getById(String id) {
        return moduleMapper.getModuleById(id);
    }

    @Override
    public int createModule(Module module) {
        return moduleMapper.insert(module);
    }

    @Override
    public int updateModule(Module module) {
        return moduleMapper.updateById(module);
    }

    @Override
    public int deleteModule(String id) {
        return moduleMapper.deleteById(id);
    }

    @Override
    public List<Module> getModules(String projectId) {
        return moduleMapper.getModules(projectId);
    }

    @Override
    public List<Module> getModuleList(String[] moduleIdsArray) {
        return null;
    }
}
