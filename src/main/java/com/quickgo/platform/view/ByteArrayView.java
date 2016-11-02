package com.quickgo.platform.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : huangjie
 * @since : 16/6/19
 */

public class ByteArrayView extends ResultView{
    public ByteArrayView(byte[] data) {
        super.setData(data);
    }
    private Map<String,String> headerMap = new HashMap<>();

    public ByteArrayView setHeader(String name,String value){
        headerMap.put(name,value);
        return this;
    }
    @Override
    public void doRepresent(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        Object data = getData();
        if(data instanceof byte[]){
            for(Map.Entry<String,String> entry:headerMap.entrySet()) {
                httpServletResponse.setHeader(entry.getKey(),entry.getValue());
            }
            httpServletResponse.setHeader("Pragma", "No-cache");
            httpServletResponse.setHeader("Cache-Control", "No-cache");
            httpServletResponse.setContentLength(((byte[]) data).length);
            httpServletResponse.getOutputStream().write((byte[]) data);
        }
    }
}
