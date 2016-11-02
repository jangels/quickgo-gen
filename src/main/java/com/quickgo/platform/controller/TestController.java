package com.quickgo.platform.controller;


import com.quickgo.platform.annotation.Post;
import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.param._HashMap;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : huangjie
 * @since : 16/8/27
 */
@Ignore
@RequestMapping("/test")
public class TestController {

    @Post("/raw")
    public Object testRaw(Parameter parameter) throws IOException {
        Enumeration<String> names = parameter.getRequest().getHeaderNames();
        Map<String,String> headerMap = new HashMap<>();
        while (names!=null && names.hasMoreElements()){
            String name = names.nextElement();
            String value = parameter.getRequest().getHeader(name);
            headerMap.put(name,value);
        }
        String encoding=parameter.getRequest().getCharacterEncoding();
        if(encoding == null){
            encoding = "UTF-8";
        }
        String raw = IOUtils.toString(parameter.getRequest().getInputStream(),encoding);
        return new _HashMap<>()
                .add("headers",headerMap)
                .add("raw",raw);
    }

}
