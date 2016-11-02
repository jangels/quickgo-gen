package com.quickgo.platform.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoujingjie
 * @date 2016-06-13
 */
public class HeadView extends ResultView {
    @Override
    public void doRepresent(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        httpServletResponse.setStatus(200);
    }
}
