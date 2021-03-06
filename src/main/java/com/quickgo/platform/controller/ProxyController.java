package com.quickgo.platform.controller;

import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.utils.HttpUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjie
 * @since  2016-07-18
 */
@RestController
@RequestMapping("/proxy")
public class ProxyController {


    public Object get(Parameter parameter){
        String url= parameter.getParamString().get("url");
        return HttpUtils.get(url);
    }

    public Object post(Parameter parameter){
        String url= parameter.getParamString().get("url");
        List<NameValuePair> nvp = new ArrayList<>();
        return HttpUtils.post(url,nvp.toArray(new NameValuePair[nvp.size()]));
    }

}
