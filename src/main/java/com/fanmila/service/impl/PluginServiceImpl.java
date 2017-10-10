package com.fanmila.service.impl;

import com.fanmila.handlers.resolver.PluginHandlerResolver;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.BaseServiceFace;
import com.fanmila.service.task.ContextLogClickTask;
import com.fanmila.util.framework.ContextUtils;
import com.fanmila.util.framework.RequestUtils;
import com.fanmila.util.framework.ThreadPoolUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 
 * @email 
 * @date 
 * 
 * @return
 */
public class PluginServiceImpl extends BaseServiceFace {
	


	private static PluginHandlerResolver dbHandler = ContextUtils.getBean(PluginHandlerResolver.class);
	
	public String service() {
		URLHandlerContext context = new URLHandlerContext();
		
		context.setRequestData(getRequestJSON());

		boolean logFlag = false;
		try {

			context.setOurl(getOurl());
			context.setCurl(getCurl());
			context.setUuid(context.getRequestData().getString("uuid"));
			
			switch (context.getRequestData().getString("method")) {

			case "getGoods":
				dbHandler.getTaobaoGoodsInfo(context);
				break;
			case "matchRule":
				dbHandler.getMatchRule(context);
				logFlag = true;
				break;
			case "DhssMatchRule":
				dbHandler.getDhssMatchRule(context);
				logFlag = true;
				break;
			case "DhDownUrl":
				dbHandler.getDhDownUrl(context);
				logFlag = true;
				break;
			case "SsDownUrl":
				dbHandler.getSsDownUrl(context);
				logFlag = true;
				break;
			case "getTaobaoFavoritesGoods":
				dbHandler.getTaobaoFavoritesGoods(context);
			case "newMatchRule":
				dbHandler.getNewMatchRule(context);
				logFlag = true;
				break;
			case "popUp":
				dbHandler.getPopUpInfo(context);
				break;
			case "youhuihuodong":
				dbHandler.getYouhuiHuodong(context);
				break;
			default:
				break;
			}

			this.getResultJSON().put("result", context);
		}finally{
			if(logFlag) logClick(context);//记录日志到mongodb
		}
		setSucc();
		return getResult();
	}

	private void logClick(URLHandlerContext context) {
		try {/*
			Click click = new Click();
			click.setCreateDate(new Date());
			click.setMethod(context.getRequestData().getString("method"));
			click.setOurl(getOurl());
			click.setCurl(getCurl());
			click.setRequest(context.getRequestData());
			click.setResponse(context.getInfo());*/
			ThreadPoolUtils.getInstance().addTask(new ContextLogClickTask(context));
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
