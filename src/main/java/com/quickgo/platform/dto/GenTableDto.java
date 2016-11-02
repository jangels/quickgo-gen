package com.quickgo.platform.dto;

import com.quickgo.platform.model.GenConfig;
import com.quickgo.platform.model.GenTable;

import java.util.List;

/**
 * Created by 144435 on 2016-10-27.
 */
public class GenTableDto {
    private GenTable genTable;//表信息
    private GenConfig config;
    private List<GenTable> tableList;


    public List<GenTable> getTableList() {
        return tableList;
    }

    public void setTableList(List<GenTable> tableList) {
        this.tableList = tableList;
    }


    public GenTable getGenTable() {
        return genTable;
    }

    public void setGenTable(GenTable genTable) {
        this.genTable = genTable;
    }

    public GenConfig getConfig() {
        return config;
    }

    public void setConfig(GenConfig config) {
        this.config = config;
    }
}
