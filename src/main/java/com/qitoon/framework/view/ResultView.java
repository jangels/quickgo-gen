package com.qitoon.framework.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangjie
 * @since  2016/10/22
 */

public abstract class ResultView {

    private Object data;
    private String template;

    public abstract void doRepresent(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception;

    public void handleException(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest,Throwable e) throws Exception {
        e.printStackTrace();
    }

    public ResultView() {
    }

    public ResultView(Object data, String template) {
        this.data = data;
        this.template = template;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
