package com.qitoon.framework.view;


import com.qitoon.framework.exception.NotLoginException;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.utils.ConfigUtils;

/**
 * @author huangjie
 * @since  2016-06-07
 */
public class JspView extends JspParentView {
    public JspView(String prefix, String suffix) {
        super(prefix, suffix);
    }

    @Override
    public void handleException(Parameter parameter, Throwable throwable) throws Exception {
        if(throwable instanceof NotLoginException){
            String loginUrl = ConfigUtils.getProperty("login.url");
            if(loginUrl == null){
                loginUrl = parameter.getContextPath();
            }
            parameter.getResponse().sendRedirect(loginUrl);
        }else {
            super.handleException(parameter, throwable);
        }
    }
}
