package com.qitoon.framework.controller;

import com.qitoon.framework.annotation.Post;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.utils.HttpUtils;
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

    @Post
    public Object post(Parameter parameter){
        String url= parameter.getParamString().get("url");
        List<NameValuePair> nvp = new ArrayList<>();
        return HttpUtils.post(url,nvp.toArray(new NameValuePair[nvp.size()]));
    }

}
