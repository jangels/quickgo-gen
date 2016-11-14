package com.quickgo.platform.controller;


import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.param._HashMap;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : huangjie
 * @since : 16/8/27
 */
@RestController
@RequestMapping("/test")
public class TestController {

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

//    @Autowired
//    @Qualifier("primaryJdbcTemplate")
//    JdbcTemplate jdbcTemplate1;
//
//    @Autowired
//    @Qualifier("secondaryJdbcTemplate")
//    JdbcTemplate jdbcTemplate2;
//
//    @RequestMapping("/test1")
//    public String test1(){
//        String id= "1dzCPEJfx";
//        String sql = "SELECT id FROM USER WHERE id ="+"'"+id+"'";
////        String sql = "SELECT * FROM USER ";
//        Map<String, Object> list = jdbcTemplate1.queryForMap(sql);
//        return Arrays.asList(list).toString();
//    }
//
//    @RequestMapping("/test2")
//    public String test2(){
//        String id= "1dzCPEJfx";
//        String sql = "SELECT id FROM USER WHERE id ="+"'"+id+"'";
////        String sql = "SELECT * FROM USER ";
//        Map<String, Object> list = jdbcTemplate2.queryForMap(sql);
//        return Arrays.asList(list).toString();
//    }
}
