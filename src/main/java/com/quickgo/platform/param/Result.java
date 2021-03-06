package com.quickgo.platform.param;

import com.alibaba.fastjson.JSONObject;
import com.quickgo.platform.utils.LogTemplate;
import com.quickgo.platform.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.quickgo.platform.view.JsonResponse.FAIL_CODE;
import static com.quickgo.platform.view.JsonResponse.SUCCESS_CODE;

/**
 * @author : huangjie
 * @since : 16/5/4
 */
public class Result<T> {
    private int code;
    private T data;
    private String errorMsg;

    public static final int OK = 0;
    public static final int ERROR = -1;

    public Result() {
    }

    public Result(boolean result, T data) {
        if(result){
            this.code = OK;
            this.data = data;
        }else{
            this.code = ERROR;
            this.errorMsg = (String) data;
        }
    }
    public Result(int code, String errorMsg){
        this.code = code;
        this.errorMsg =errorMsg;
    }

    public static <T> Object returnInfo(T data) {
        Map<Object,Object> jo = new HashMap<>();
        jo.put("operateTime", System.currentTimeMillis());
        if (data == null) {
            jo.put("result", FAIL_CODE);
            jo.put("msg", "未查询到数据。");
        } else {
            jo.put("data", data);
            jo.put("result", SUCCESS_CODE);
        }
        return jo;
    }

    public static <T> Object returnInfo(T data,String msg) {
        Map<Object,Object> jo = new HashMap<>();
        jo.put("operateTime", System.currentTimeMillis());

        if (data == null) {
            jo.put("result", FAIL_CODE);
            jo.put("msg", msg);
        } else {
            if(StringUtils.isEmpty(msg)){
                jo.put("data", data);
                jo.put("msg", msg);
                jo.put("result", SUCCESS_CODE);

            }else {
                jo.put("data", data);
                jo.put("msg", msg);
                jo.put("result", FAIL_CODE);
            }
        }
        return jo;
    }

    public static Object fail(String msg) {
        LogTemplate.info(msg);
        JSONObject jo = new JSONObject();
        jo.put("result", FAIL_CODE);
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", msg);
        return jo;
    }
    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
