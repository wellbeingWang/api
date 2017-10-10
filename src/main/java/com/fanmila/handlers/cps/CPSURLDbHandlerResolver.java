package com.fanmila.handlers.cps;


import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.handlers.IUnionHandler;
import com.fanmila.util.framework.ContextUtils;
import com.fanmila.util.framework.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CPSURLDbHandlerResolver {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 *根据目标URL生成CPS引导URL，按以下步骤处理，只要一个步骤处理成功就返回
	 * 
	 * <pre>
	 * 	1、尝试用数据库配置接入处理器处理
	 * 	2、用默认处理器处理
	 * </pre>
	 * @param context
	 * @return
	 */
	public String getRedirectURL(CPSURLHandlerContext context) {
		//数据库定制cps规则先处理
		String durl = getRedirectURLFromDb(context);
		if(StringUtils.isNotBlank(durl)){
			//是否记录成功的cps转换日志 默认不积虑
			/*if(CpsServerConfig.isSaveDbPostGreLog){
				logClick(context);
			}
			CPSStaticLog.instance().addSuccess();*/
			
			return durl;
		}
		
		IUnionHandler defaultUnionHandler = ContextUtils.getApplicationContext().getBean(DefaultUnionHandler.class);
		
		durl = defaultUnionHandler.getRedirectURL(context);
		/*//记录错误日志
		logClick(context);
		CPSStaticLog.instance().addFailed();*/
		return durl;
	}


	public String getRedirectRule(CPSURLHandlerContext context){
		CPSGetRuleHandler  dbHandler = ContextUtils.getApplicationContext().getBean(CPSGetRuleHandler.class);
		return dbHandler.getRedirectURL(context);
	}


/*	*//**
	 * 是否不做cps处理
	 * @param context
	 * @return
	 *//*
	public boolean isBlackList(CPSURLHandlerContext context){
		String target =null;
		IUnionHandler blackListHandler = ContextUtils.getApplicationContext().getBean(BlacklistHandler.class);
		target= blackListHandler.getRedirectURL(context);
		if(null != target){
			context.setDurl(target);
			return true;
		}
		return false;
	}*/
	
	public boolean isIpPass(CPSURLHandlerContext context){
		return false;
	}
	
	
	private String getRedirectURLFromDb(CPSURLHandlerContext context) {
		if("LVR".equalsIgnoreCase(context.getSsub())){
			return null;
		}
		CPSURLDbHandler  dbHandler = ContextUtils.getApplicationContext().getBean(CPSURLDbHandler.class);
		return dbHandler.getRedirectURL(context);
	}
	
	private String getOurl() {
		HttpServletRequest request = RequestUtils.getInstance().getRequest();
		StringBuffer sb = request.getRequestURL();
		if (StringUtils.isNotBlank(request.getQueryString())) {
			sb.append("?");
			sb.append(request.getQueryString());
		}
		return sb.toString();
	}

}
