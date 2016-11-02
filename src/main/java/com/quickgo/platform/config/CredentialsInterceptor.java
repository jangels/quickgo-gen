package com.quickgo.platform.config;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域问题
 * @author 杨鹏兵
 * @Date 2015年5月19日
 */

public class CredentialsInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(CredentialsInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
	    response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Headers","*");

		logger.info("请求方法："+request.getMethod());
		logger.info("请求contextPath："+request.getContextPath());
		logger.info("请求URL地址："+request.getRequestURL());
		logger.info("请求参数名称："+request.getParameterNames().toString());
		logger.info("请求路径："+request.getPathInfo());
		logger.info("请求URI地址："+request.getRequestURI());
	    return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}


}
