package com.quickgo.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.quickgo.platform.face.IDateSourceService;
import com.quickgo.platform.model.DateSource;
import com.quickgo.platform.utils.JsonResponse;
import com.quickgo.platform.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lichenchen
 * @since  2016-07-20
 */
@RestController
@RequestMapping("/dateSource")
public class DateSourceController  {
    @Autowired
    private IDateSourceService dateSourceService;

    /**
     * 保存
     * @param model
     * @return
     */
    @RequestMapping(value = "/save")
    public  Object save(DateSource model) {

         dateSourceService.save(model);
        return JsonResponse.success( model.getName() + "' 成功");
    }

    /**
     * 删除
     * @param model
     * @return
     */
    @RequestMapping(value = "/delete")
    public JSONObject delete(DateSource model) {
        dateSourceService.delete(model);
        return JsonResponse.success( "删除生成方案成功 ");
    }


    /**
     * 查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}")
    public DateSource get(@PathVariable String id) {
        if (StringUtils.isNotBlank(id)){
            return dateSourceService.get(id);
        }else{
            return new DateSource();
        }
    }
}
