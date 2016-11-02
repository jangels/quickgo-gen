package com.quickgo.platform.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * dependency: com.alibaba.fastjson
 * @author : zhoujingjie
 * @Date: 16/4/22
 */
public class JsonParentView extends ResultView {
    @Override
    public void doRepresent(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception{
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        JSON.writeJSONStringTo(super.getData(),httpServletResponse.getWriter(), SerializerFeature.WriteDateUseDateFormat);
    }
}
