package com.quickgo.platform.config;

import com.quickgo.platform.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 跨域问题
 * @author 杨鹏兵
 * @Date 2015年5月19日
 */
@Configuration
public class CredentialsInterceptor implements HandlerInterceptor {
	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal StartTime");

	private static Logger logger = Logger.getLogger(CredentialsInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
	    response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods","GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		response.setHeader("Access-Control-Allow-Headers","*");
		String email =request.getParameter("email");
		String token =request.getParameter("token");
//		if(StringUtils.isEmpty(email)){
//			return false;
//		}
		logger.info("请求方法："+request.getMethod());
		logger.info("请求contextPath："+request.getContextPath());
		logger.info("请求URL地址："+request.getRequestURL());
		long beginTime = System.currentTimeMillis();//1、开始时间
		startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
		logger.info("开始计时:  "+ new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime));
		logger.info("请求URI地址: "+ request.getRequestURI());
	    return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
			logger.info("ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 打印JVM信息。
		if (logger.isDebugEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
			long endTime = System.currentTimeMillis(); 	//2、结束时间
			logger.info("计时结束： "+ new SimpleDateFormat("hh:mm:ss.SSS").format(endTime));
			logger.info("耗时： "+DateUtils.formatDateTime(endTime - beginTime));
			logger.info("URI:  "+request.getRequestURI());
			logger.info("最大内存: "+Runtime.getRuntime().maxMemory()/1024/1024);
			logger.info("已分配内存: "+Runtime.getRuntime().totalMemory()/1024/1024);
			logger.info("已分配内存中的剩余空间: "+Runtime.getRuntime().freeMemory()/1024/1024);
			logger.info("最大可用内存: "+(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
		}
		
	}


}
