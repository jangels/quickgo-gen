package com.quickgo.platform.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;


/**
 * 获取客户端信息工具类
 * @author 杨鹏兵
 * @Date 2015年5月19日
 */
public class WebUtils {
	private static final String LOCAL_HOST = "127.0.0.1";
	private static final String UNKNOWN = "unknown";
	/**
	 * 获取请求客户端的ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
	     String ipAddress=request.getHeader("x-forwarded-for");
	     if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
	    	 ipAddress = request.getRemoteAddr();
		     if(LOCAL_HOST.equals(ipAddress)){
		       //根据网卡取本机配置的IP
		       InetAddress inet=null;
			    try {
			    	inet = InetAddress.getLocalHost();
			    	ipAddress= inet.getHostAddress();
			    } catch (UnknownHostException e) {
			    	LogTemplate.error(e.getMessage(), e);
			    }
		    }
	        
	     }
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	     if(ipAddress!=null && ipAddress.length()>15 && ipAddress.indexOf(',') >= 0){ //"***.***.***.***".length() = 15
	    	 ipAddress = ipAddress.substring(0,ipAddress.indexOf(','));
	     }
	     if("0:0:0:0:0:0:0:1".equals(ipAddress)){
	    	 ipAddress = "127.0.0.1" ;
	     }
	     return ipAddress;
	  }
}
