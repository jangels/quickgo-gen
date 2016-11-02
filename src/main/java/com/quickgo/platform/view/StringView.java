package com.quickgo.platform.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangjie
 * @since  2016-06-13
 */
public class StringView extends ResultView {

    public StringView(String data) {
        setData(data);
    }

    @Override
    public void doRepresent(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        Object data = getData();
        if(data instanceof String){
            httpServletResponse.getWriter().write((String)data);
        }
    }
}
