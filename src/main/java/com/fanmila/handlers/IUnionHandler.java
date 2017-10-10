package com.fanmila.handlers;

import com.fanmila.model.URLHandlerContext;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;

import java.util.Map;

public interface IUnionHandler {


	public String getRedirectURL(URLHandlerContext context);
	public String getRedirectURL(CPSURLHandlerContext context);


	/**
	 * 返回该处理器适用于哪些网站
	 * 
	 * @author Larry Lang
	 * @date Jan 14, 2013
	 * 
	 * @return
	 */
	public String[] getApplyTO();

	public String getFeedback(URLHandlerContext context);

	/**
	 * 要加到跳转URL后面的参数
	 * 
	 * @author Larry Lang
	 * @date Jan 18, 2013
	 * 
	 * @return
	 */
	public Map<String, String> getExtraParams();

}
