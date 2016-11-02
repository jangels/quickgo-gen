package com.quickgo.platform.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


/**
 * 日志打印工具类
 * @author 138829
 *
 */
public class LogTemplate {

	private static final Logger LOG = LogManager.getLogger(LogTemplate.class);
	public static void info(String msg, Object... args){
		LOG.info(msg, args);
	}
	
	public static void debug(String msg, Object... args){
		LOG.debug(msg, args);
	}
	
	public static void error(String msg, Object... args){
		LOG.error(msg, args);
	}
	
	public static void main(String[] args) {
	    String s = "hello {}, to {}";	
	    info(s, new Object[]{"xuhao", new Date()});
	}
	
}
