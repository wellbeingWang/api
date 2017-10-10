package com.fanmila.service.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fanmila.util.framework.ContextUtils;

/**
 * 第一时间把getServletContext的信息共享出来
 * 
 * @author Larry Lang
 * @date Jan 14, 2013
 * 
 */
public class FanmilaServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ContextUtils.getInstance()._setContextPath(event.getServletContext().getContextPath());
		ContextUtils.getInstance()._setRootPath(event.getServletContext().getRealPath("/"));
	}

}
