package com.fanmila.service.listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fanmila.util.framework.ContextUtils;
import com.fanmila.util.framework.RequestUtils;
import com.fanmila.ctrl.core.AbstractBaseController;


public class FanmilaInterceptorAdapter extends HandlerInterceptorAdapter {
	

    private String controller = "/plugin.do";
    private String controllerExtension = "/extension.do";
/*    private String filterName = "method";
    private String filterUUID = "uuid";*/

	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String servletPath = request.getServletPath();
/*		String filterMethod = request.getParameter(filterName);
		String uuid = request.getParameter(filterUUID);*/
		if( controller.equals(servletPath)){
			return false;
		}else if(controllerExtension.equals(servletPath)){
			return false;
		}
		
		ContextUtils.getInstance()._setServlet(request, response);
		RequestUtils.getInstance().setRequest(request, false);
		if(handler!=null&&handler instanceof AbstractBaseController){
			//注入全部的request和response对象
			((AbstractBaseController)handler)._setServlet(request, response);
		}
		
		/*
		 * 以后可扩展权限
		 String uri=request.getRequestURI();
		if(request.getContextPath().length()>0) uri=uri.substring(request.getContextPath().length());
		//不希望拦截的URl在此过滤，直接返回即可
		if(uri.endsWith("/login.do")) return true;
		*/
		/** 生成日志　**/
		return true;
		
	}
}
