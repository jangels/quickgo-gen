package com.qitoon.framework.view;


import com.qitoon.framework.param.Parameter;

/**
 * @author huangjie
 * @since  2016-06-13
 */
public class StringView extends ResultView {

    public StringView(String data) {
        setData(data);
    }

    @Override
    public void doRepresent(Parameter parameter) throws Exception {
        Object data = getData();
        if(data instanceof String){
            parameter.getResponse().getWriter().write((String)data);
        }
    }
}
