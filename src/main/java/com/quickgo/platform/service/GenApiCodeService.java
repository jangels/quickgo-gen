package com.quickgo.platform.service;

import com.quickgo.platform.dao.GenTableColumnDao;
import com.quickgo.platform.dao.GenTableDao;
import com.quickgo.platform.exception.InvalidArgumentException;
import com.quickgo.platform.face.IGenApiCodeService;
import com.quickgo.platform.face.IInterfaceService;
import com.quickgo.platform.model.*;
import com.quickgo.platform.utils.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjie
 * on 2016/11/4.
 */
@Service
public class GenApiCodeService implements IGenApiCodeService {

    @Autowired
    private IInterfaceService interfaceService;
    @Autowired
    private GenSchemeService genSchemeService;
    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao;

    @Override
    public String findList(List<String> fids) {
        //根据分类Id查询所选表下面的所有接口
        List<Interface> interfaceList = interfaceService.getInterfacesByIds(fids);

        if(CollectionUtils.isEmpty(interfaceList)){
            throw  new InvalidArgumentException("参数无效");
        }
        // 获取gen_scheme表数据
        String tableId = interfaceList.get(0).getTableId();
        String projectId = interfaceList.get(0).getProjectId();
        GenScheme genScheme = genSchemeService.getGenScheme(tableId,projectId);
        // 整合数据
        List<Interface> interfaces = new ArrayList<>();
        for (Interface in: interfaceList){
            interfaces.add(in);
        }
        genScheme.setInterfaces(interfaces);
        // 查询主表及字段列
        GenTable genTable = genTableDao.get(interfaceList.get(0).getTableId());
        genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        genScheme.setGenTable(genTable);

        // 获取所有代码模板
        GenConfig config = GenUtils.getConfig();
        //TODO 模板跟项目关联，对应每一个项目都可以自定义一套模板
        // 获取模板列表
        List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);

        //根据模块生成代码
        StringBuilder result = new StringBuilder();
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
        for (GenTemplate tpl : templateList){
            String code = GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile());
            result.append(code);
        }
        return result.toString();
    }
}
