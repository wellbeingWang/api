package com.fanmila.service.impl;

import com.fanmila.handlers.resolver.CltHandlerResolver;
import com.fanmila.model.Click;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.BaseServiceFace;
import com.fanmila.service.task.APICLTClickTask;
import com.fanmila.util.framework.ContextUtils;
import com.fanmila.util.framework.RequestUtils;
import com.fanmila.util.framework.ThreadPoolUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 
 * @email 
 * @date 
 * 
 * @return
 */
public class CltServiceImpl extends BaseServiceFace {
	


	private static CltHandlerResolver dbHandler = ContextUtils.getBean(CltHandlerResolver.class);
	
	public String service() {
		URLHandlerContext context = new URLHandlerContext();
		
		context.setRequestData(getRequestJSON());
		boolean isLog = false;
		try {
			context.setUuid(context.getRequestData().getString("uuid"));
			context.setMid(context.getRequestData().getString("mid"));
			context.setSid(context.getRequestData().getString("sid"));
			context.setOurl(getOurl());
			context.setCurl(getCurl());
			
			switch (context.getRequestData().getString("method")) {

			case "update":
				dbHandler.getUpdateInfo(context);
				break;
			case "reason":
				dbHandler.getReasonInfo(context);
				isLog = true;
				break;
			case "getEncyptJs":
				dbHandler.getEncyptJs(context);
				break;
			case "getBHOJs":
				dbHandler.getBHOJs(context);
				break;
			case "getBKJson":
				dbHandler.getBKJson(context);
				break;
			case "getSCJUrl":
				dbHandler.getSCJUrl(context);
				break;

			case "popUp":
				dbHandler.getPopUp(context);
				break;
			default:
				break;
			}

			this.getResultJSON().put("result", context);
		}finally{
			if(isLog)
			    logClick(context);
		}
		setSucc();
		return getResult();
	}

	private void logClick(URLHandlerContext context) {
		try {
			Click click = new Click();
			click.setCreateDate(new Date());
			click.setMethod(context.getRequestData().getString("method"));
			click.setOurl(getOurl());
			click.setCurl(getCurl());
			click.setRequest(context.getRequestData());
			click.setResponse(context.getInfo());
			ThreadPoolUtils.getInstance().addTask(new APICLTClickTask(click));
		} catch (Exception e) {
			logger.logError(e);
		}
	}

	/**
	 * 获取referer
	 * @return
	 */
	private String getCurl() {
		HttpServletRequest request = RequestUtils.getInstance().getRequest();
		String referer = request.getHeader("referer");
		return StringUtils.trimToEmpty(referer);
	}

	/**
	 * 
	* @Title: getOurl 
	* @Description: 当前请求URL
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
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
