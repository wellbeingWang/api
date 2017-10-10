package com.fanmila.handlers.resolver;


import com.fanmila.handlers.*;
import com.fanmila.handlers.taobao.FavoritesItemHandler;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.util.framework.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PluginHandlerResolver {

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
	public String getTaobaoGoodsInfo(URLHandlerContext context) {

		PluginGetGoodsHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginGetGoodsHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getMatchRule(URLHandlerContext context) {

		PluginMatchRuleHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginMatchRuleHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getNewMatchRule(URLHandlerContext context) {

		PluginNewMatchRuleHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginNewMatchRuleHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getDhssMatchRule(URLHandlerContext context) {

		PluginDHSSMatchRuleHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginDHSSMatchRuleHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getDhDownUrl(URLHandlerContext context) {

		PluginDHDownUrlHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginDHDownUrlHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getSsDownUrl(URLHandlerContext context) {

		PluginSSDownUrlHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginSSDownUrlHandler.class);
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

	public String getTaobaoFavoritesGoods(URLHandlerContext context) {
		FavoritesItemHandler dbHandler = ContextUtils.getApplicationContext().getBean(FavoritesItemHandler.class);
		return dbHandler.getRedirectURL(context);
	}

	public String getPopUpInfo(URLHandlerContext context) {

		PluginPopUpHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginPopUpHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

	public String getYouhuiHuodong(URLHandlerContext context) {

		PluginYouhuiHuodongHandler dbHandler = ContextUtils.getApplicationContext().getBean(PluginYouhuiHuodongHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(durl==null) context.getHandlerInfo().setSuccess(false);

		return durl;
	}

}
