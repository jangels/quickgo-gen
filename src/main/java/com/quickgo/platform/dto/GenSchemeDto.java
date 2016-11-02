package com.quickgo.platform.dto;

import com.quickgo.platform.model.GenConfig;
import com.quickgo.platform.model.GenScheme;
import com.quickgo.platform.model.GenTable;

import java.util.List;

/**
 * Created by 144435 on 2016-10-27.
 */
public class GenSchemeDto {
    private GenScheme genScheme;//表信息
    private GenConfig config;
    private List<GenTable> tableList;


    public GenScheme getGenScheme() {
        return genScheme;
    }

    public void setGenScheme(GenScheme genScheme) {
        this.genScheme = genScheme;
    }

    public void setConfig(GenConfig config) {
        this.config = config;
    }

    public GenConfig getConfig() {
        return config;
    }



    public List<GenTable> getTableList() {
        return tableList;
    }

    public void setTableList(List<GenTable> tableList) {
        this.tableList = tableList;
    }


}
