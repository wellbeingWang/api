package com.fanmila.handlers.resolver;


import com.fanmila.handlers.*;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.util.framework.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CltHandlerResolver {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 
	* @Title: getUpdateInfo 
	* @Description: 获取客户端更新信息
	* @param @param context
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getUpdateInfo(URLHandlerContext context) {

		CLTUpdateHandler dbHandler = ContextUtils.getApplicationContext().getBean(CLTUpdateHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getReasonInfo(URLHandlerContext context) {

		CLTReasonHandler dbHandler = ContextUtils.getApplicationContext().getBean(CLTReasonHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getEncyptJs(URLHandlerContext context) {

		CLTEncyptJsHandler dbHandler = ContextUtils.getApplicationContext().getBean(CLTEncyptJsHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getBHOJs(URLHandlerContext context) {

		CLTBHOJsHandler dbHandler = ContextUtils.getApplicationContext().getBean(CLTBHOJsHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}
	public String getBKJson(URLHandlerContext context) {

		CLTBKJsonHandler dbHandler = ContextUtils.getApplicationContext().getBean(CLTBKJsonHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getSCJUrl(URLHandlerContext context) {

		CLTSCJUrlHandler dbHandler = ContextUtils.getApplicationContext().getBean(CLTSCJUrlHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getPopUp(URLHandlerContext context) {

		CLTPopUpHandler dbHandler = ContextUtils.getApplicationContext().getBean(CLTPopUpHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}


}
