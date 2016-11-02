package com.quickgo.platform.view;


import com.quickgo.platform.exception.NotLoginException;
import com.quickgo.platform.utils.ConfigUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangjie
 * @since  2016-06-07
 */
public class JspView extends JspParentView {
    public JspView(String prefix, String suffix) {
        super(prefix, suffix);
    }

    @Override
    public void handleException(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Throwable throwable) throws Exception {
        if(throwable instanceof NotLoginException){
            String loginUrl = ConfigUtils.getProperty("login.url");
            if(loginUrl == null){
                loginUrl = httpServletRequest.getContextPath();
            }
            httpServletResponse.sendRedirect(loginUrl);
        }else {
            super.handleException(httpServletResponse,httpServletRequest, throwable);
        }
    }
}
