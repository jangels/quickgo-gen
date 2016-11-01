package com.qitoon.framework.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 获取客户端信息工具类
 * @author 杨鹏兵
 * @since  2015年5月19日
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
	 public static void listSort(List<Map> resultList) throws Exception{  
	        // resultList是需要排序的list，其内放的是Map  
	        Collections.sort(resultList,new Comparator<Map>() {  
		         public int compare(Map o1,Map o2) {  
		          //o1，o2是list中的Map，按照feed名称排序  
		             String s1 = (String) o1.get("title");  
		             String s2 = (String) o2.get("title");  
		            return s1.compareTo(s2) ;
		         }
	        });  
	         
	   } 
	public static void main(String[] args) throws Exception {
		List<Map> list = new ArrayList<>() ;
		Map map = new HashMap<>() ;
		map.put("title", "cong chun yan") ;
		map.put("value", "s") ;
		map.put("name", "从春燕") ;
		Map map2 = new HashMap<>() ;
		map2.put("title", "liu yi  min su hu yong hu 2") ;
		map2.put("value", "s") ;
		Map map3 = new HashMap<>() ;
		map3.put("title", "lu meng bin") ;
		map3.put("value", "s") ;
		Map map4 = new HashMap<>() ;
		map4.put("title", "gong xin na  lv you wei guan li yuan") ;
		map4.put("value", "s") ;
		Map map5 = new HashMap<>() ;
		map5.put("title", "hu geng yong  guan li yuan") ;
		map5.put("value", "s") ;
		Map map6 = new HashMap<>() ;
		map6.put("title", "shen zi long") ;
		map6.put("value", "s") ;
		Map map7 = new HashMap<>() ;
		map7.put("title", "zhang chao jie") ;
		map7.put("value", "s") ;
		Map map8 = new HashMap<>() ;
		map8.put("title", "an hu") ;
		map8.put("value", "s") ;
		map8.put("name", "黄杰") ;
		list.add(map) ;
		list.add(map2) ;
		list.add(map3) ;
		list.add(map4) ;
		list.add(map5) ;
		list.add(map6) ;
		list.add(map7) ;
		list.add(map8) ;
		System.out.println(list);
		listSort(list) ;
		System.out.println(list);
	}
}
