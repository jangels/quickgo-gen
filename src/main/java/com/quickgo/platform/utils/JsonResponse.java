package com.quickgo.platform.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户返回JSON结果集
 */
public class JsonResponse{
    public static final String SUCCESS_CODE="200";//成功的返回码
    public static final String FAIL_CODE="201";//失败的返回码
    public static final String ERROR_CODE="203";//错误的返回码
    /**
     * 处理结果
     * @param msg
     * @param data
     * @return
     */
    
    public static <T> JSONObject returnInfo(String msg, T data){

        JSONObject jo = new JSONObject();
        jo.put("operateTime" ,System.currentTimeMillis());
      
        if(data == null)
        {
          jo.put("result",FAIL_CODE);
          jo.put("msg", "未查询到数据。");
        }else
        {
        	 jo.put("data",data);
        	 jo.put("result",SUCCESS_CODE);
        	 jo.put("msg", msg);
        }
        return jo;
    }
    
    /**
     * 处理结果
     * @param msg
     * @param data
     * @return
     */
    
    public static <T> JSONObject returnInfoPage(String total, T data){

        JSONObject jo = new JSONObject();
        jo.put("operateTime" ,System.currentTimeMillis());
      
        if(data == null)
        {
          jo.put("result",FAIL_CODE);
          jo.put("msg", "未查询到数据。");
        }else
        {
        	 jo.put("data",data);
        	 jo.put("result",SUCCESS_CODE);
        	 jo.put("msg", "查询成功");
        	 jo.put("totalCount", total) ;
        }
        return jo;
    }
    
    /**
     * 处理结果
     * @param msg
     * @param data
     * @return
     */
    public static <T> JSONObject success(String msg, T data){

        JSONObject jo = new JSONObject();
        jo.put("operateTime" ,System.currentTimeMillis());
        jo.put("msg", msg);
        if(data == null)
        {
          jo.put("result",FAIL_CODE);
        }else
        {
        	 jo.put("data",data);
        	 jo.put("result",SUCCESS_CODE);
        }
        return jo;
    }
    
    public static <T> JSONObject success(T data){

        JSONObject jo = new JSONObject();
        jo.put("result",SUCCESS_CODE);
        jo.put("operateTime" ,System.currentTimeMillis());
        jo.put("msg", "成功");
        jo.put("data",data);
        return jo;
    }
    
    public static <T> JSONObject successPage(T data,String total){

        JSONObject jo = new JSONObject();
        jo.put("result",SUCCESS_CODE);
        jo.put("operateTime" ,System.currentTimeMillis());
        jo.put("msg", "成功");
        jo.put("data",data);
        jo.put("total",total) ;
        return jo;
    }
    public static <T> JSONObject success(String msg){

        JSONObject jo = new JSONObject();
        jo.put("result",SUCCESS_CODE);
        jo.put("operateTime" ,System.currentTimeMillis());
        jo.put("msg", msg);
        return jo;
    }
    public static JSONObject fail(String msg) {
    	LogTemplate.info(msg);
        JSONObject jo = new JSONObject();
        jo.put("result",FAIL_CODE);
        jo.put("operateTime" ,System.currentTimeMillis());
        jo.put("msg", msg);
        return jo;
    }

    
    public static JSONObject fail(String msg,String data) {
    	LogTemplate.info(msg + "--" + data);
        JSONObject jo = new JSONObject();
        jo.put("result",FAIL_CODE);
        jo.put("operateTime" ,System.currentTimeMillis());
        jo.put("msg", msg);
        jo.put("data",data);
        return jo;
    }
    public static JSONObject error(String msg,Exception e) {
    	LogTemplate.error(msg,e);
        JSONObject jo = new JSONObject();
        jo.put("result",ERROR_CODE);
        jo.put("operateTime" ,System.currentTimeMillis());
        jo.put("msg", msg);
        return jo;
    }

//    /**
//     * 处理结果data为listde to string的数据
//     * @param error
//     * @param data
//     * @return
//     */
//    public static <T> JSONObject returnInfoWithString(ErrorInfo error, String data,PageInfo<T> pageInfo){
//        JSONObject jo = new JSONObject();
//        jo.put("result", error.getCode());
//        jo.put("operateTime" ,new Date().getTime());
//        jo.put("msg",error.getMsg());
//        if(error.isSuccess()){
//            jo.put("data",data);
//        }else{
//            jo.put("data",null);
//        }
//        jo.put("pageSize",pageInfo.getPageSize());
//        jo.put("totalCount",pageInfo.getTotalCount());
//        jo.put("totalPage",pageInfo.getTotalPage());
//        return jo;
//    }





//    /**
//     * 处理结果data为listde to string的数据
//     * @param error
//     * @param data
//     * @return
//     */
//    public static <T> JSONObject returnInfoWithString(ErrorInfo error, String data){
//        JSONObject jo = new JSONObject();
//        jo.put("result", error.getCode());
//        jo.put("operateTime" ,new Date().getTime());
//        jo.put("msg", error.getMsg());
//        if(error.isSuccess()){
//            jo.put("data",data);
//        }else{
//            jo.put("data",null);
//        }
//        return jo;
//    }


    
   


}