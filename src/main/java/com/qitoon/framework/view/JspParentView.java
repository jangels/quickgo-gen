package com.qitoon.framework.view;


import com.qitoon.framework.param.Parameter;
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
    public void doRepresent(Parameter parameter) throws Exception {
        String template = getTemplate();
        if (template.length() == 0) {
            template = parameter.getPath();
        }
        String path = prefix + template + suffix;
        path = path.replace("//", "/");

        HttpServletRequest request = parameter.getRequest();

        Object data = getData();
        if (data != null) {
            if (data instanceof Map) {
                for(Object item:((Map) data).entrySet()){
                    Map.Entry entry = (Map.Entry) item;
                    request.setAttribute((String)entry.getKey(),entry.getValue());
                }
            }else{
                request.setAttribute("model",data);
            }
        }
        parameter.getRequest().getRequestDispatcher(path).forward(parameter.getRequest(), parameter.getResponse());
    }

    @Override
    public void handleException(Parameter parameter, Throwable throwable) throws Exception {
        HttpServletResponse response = parameter.getResponse();
        response.setStatus(503);
        parameter.getRequest().setAttribute("exception",throwable);
        throwable.printStackTrace();
        if(ConfigUtils.getErrorPage()!=null) {
            parameter.getRequest().getRequestDispatcher(ConfigUtils.getErrorPage()).forward(parameter.getRequest(), response);
        }
    }
}
