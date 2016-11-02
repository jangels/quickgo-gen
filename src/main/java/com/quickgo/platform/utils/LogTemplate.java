package com.quickgo.platform.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志打印工具类
 * @author 138829
 *
 */
public class LogTemplate {

	private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

	public static void info(String msg, Object... args){
		logger.info(msg, args);
	}
	
	public static void debug(String msg, Object... args){
		logger.debug(msg, args);
	}
	
	public static void error(String msg, Object... args){
		logger.error(msg, args);
	}
	
	public static void main(String[] args) {
	    String s = "hello {}, to {}";	
	    info(s, new Object[]{"xuhao", new Date()});
	}
	
}
