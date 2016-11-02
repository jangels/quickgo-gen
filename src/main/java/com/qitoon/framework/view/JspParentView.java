package com.qitoon.framework.view;


import com.qitoon.framework.utils.ConfigUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: zhoujingjie
 * @Date: 16/4/24
 */
public class JspParentView extends ResultView {
    private String prefix;
    private String suffix;

    public JspParentView(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }


    @Override
    public void doRepresent(HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest) throws Exception {
        String template = getTemplate();
        if (template.length() == 0) {
            template =httpServletRequest.getPathInfo();
        }
        String path = prefix + template + suffix;
        path = path.replace("//", "/");


        Object data = getData();
        if (data != null) {
            if (data instanceof Map) {
                for(Object item:((Map) data).entrySet()){
                    Map.Entry entry = (Map.Entry) item;
                    httpServletRequest.setAttribute((String)entry.getKey(),entry.getValue());
                }
            }else{
                httpServletRequest.setAttribute("model",data);
            }
        }
        httpServletRequest.getRequestDispatcher(path).forward(httpServletRequest,httpServletResponse);
    }

    @Override
    public void handleException(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest,Throwable throwable) throws Exception {
        httpServletResponse.setStatus(503);
        httpServletRequest.setAttribute("exception",throwable);
        throwable.printStackTrace();
        if(ConfigUtils.getErrorPage()!=null) {
            httpServletRequest.getRequestDispatcher(ConfigUtils.getErrorPage()).forward(httpServletRequest, httpServletResponse);
        }
    }
}
