/**
 * AppServerFilter.java
 *
 * 功  能：web过滤器
 * 类  名：AppServerFilter
 *
 *   ver     变更日       公司          作者              变更内容
 * ────────────────────────────────────
 *   V1.00   '12-05-21     iZENEsoft		wiley.wang       初版
 *
 * Copyright (c) 2009 iZENEsoft Business Software corporation All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.fanmila.service.listener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanmila.util.framework.ContextUtils;

/**
 * web过滤器
 * 
 * @author wiley.wang
 * @version V1.0 2012-05-21
 */
public class FanmilaFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String uri=request.getRequestURI();
		
		if(uri.endsWith(".jsp")||uri.endsWith(".html")){
			try{
				response.setContentType("text/html; charset=utf-8");
				ContextUtils.getInstance();
				FanmilaInterceptorAdapter b5mInterceptor=ContextUtils.getBean(FanmilaInterceptorAdapter.class);
				if(b5mInterceptor!=null){
					boolean isCheck=b5mInterceptor.preHandle(request, response,null);
					if(!isCheck)return;
				}
					
			}catch(Exception e){e.printStackTrace();}
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig cfg) throws ServletException {
	}

}
