package com.qitoon.framework.view;


import com.qitoon.framework.exception.InvalidArgumentException;
import com.qitoon.framework.exception.NotLoginException;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.param.Result;

/**
 * @author huangjie
 * @since  2016-05-24
 */
public class JsonView extends JsonParentView {
    @Override
    public void doRepresent(Parameter parameter) throws Exception {
        Object data = super.getData();
        if(!(data instanceof Result)){
            data = new Result<>(true,data);
            super.setData(data);
        }
        parameter.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        parameter.getResponse().setHeader("Access-Control-Request-Method", "GET, POST, DELETE, PUT, OPTIONS");
        super.doRepresent(parameter);
    }

    @Override
    public void handleException(Parameter parameter, Throwable e) throws Exception {
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
        doRepresent(parameter);
    }


}
