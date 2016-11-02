package com.quickgo.platform.view;


import com.quickgo.platform.exception.InvalidArgumentException;
import com.quickgo.platform.exception.NotLoginException;
import com.quickgo.platform.param.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangjie
 * @since  2016-05-24
 */
public class JsonView extends JsonParentView {
    @Override
    public void doRepresent(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        Object data = super.getData();
        if(!(data instanceof Result)){
            data = new Result<>(true,data);
            super.setData(data);
        }
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Request-Method", "GET, POST, DELETE, PUT, OPTIONS");
        super.doRepresent(httpServletResponse,httpServletRequest);
    }

    @Override
    public void handleException(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Throwable e) throws Exception {
        if(e instanceof NotLoginException){
            super.setData(new Result<>(-2,"会话已过期"));
        }else {
            String err= e.getMessage();
            if(!(e instanceof InvalidArgumentException)) {
                e.printStackTrace();
                err="系统错误";
            }
            super.setData(new Result<>(false, err));
        }
        doRepresent(httpServletResponse,httpServletRequest);
    }


}
