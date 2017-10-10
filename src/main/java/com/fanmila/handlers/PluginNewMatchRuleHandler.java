package com.fanmila.handlers;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.cache.CacheConstant;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import org.apache.commons.lang.StringUtils;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class PluginNewMatchRuleHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfo = new JSONObject();
		responseInfo.put("succ", false);
		try {
            String productId = context.getRequestData().getString("productId");
			String channel = context.getRequestData().getString("channel");
			String reason = context.getRequestData().getString("reason");
			String version = context.getRequestData().getString("version");
			if(StringUtils.isBlank(productId)) return null;
			//活跃天数
			Integer activeDays = context.getRequestData().getInteger("activeDays");
			//获取后台配置的升级信息t
			Map<String, String> ruleInfo = baseRedisService.getMap(CacheConstant.CPS_GET_RULE + productId);
			if(!ruleInfo.containsKey("an")) ruleInfo.put("an", "3");
			if(isHaveA(ruleInfo.get("channel"), channel)
					|| Integer.valueOf(ruleInfo.get("an"))>=activeDays
					|| isHaveA(ruleInfo.get("version"), version))
				return null;
			//获取客户端传递参数Sys
			String referrer = context.getRequestData().getString("referrer");
			//referrer = "https://www.2345.com";
			String fullDomain = urlAnalyzerManager.getFullDomain(StringUtil.isBlank(referrer)?context.getCurl():referrer);
			String tdDomain = urlAnalyzerManager.getDomain(StringUtil.isBlank(referrer)?context.getCurl():referrer);
			Map<String, String> regularInfo = baseRedisService.getMap(CacheConstant.NEW_MATCH_RULE + fullDomain + "_" + productId);
			if(regularInfo==null || regularInfo.isEmpty()){
				regularInfo = baseRedisService.getMap(CacheConstant.NEW_MATCH_RULE + tdDomain + "_" + productId);
			}
			if(regularInfo==null || regularInfo.isEmpty()){
				return null;
			}
			if(!regularInfo.containsKey("an")) regularInfo.put("an", "3");

			//客户端IP
			String ipString = context.getRequestData().getString("ip");
			JSONObject jsonObject = IPToolUtil.getCityofIp(ipString);
			String cityEn = jsonObject.getString("cityEn");

			if(!regularInfo.isEmpty() && regularInfo.containsKey("an")
					&& isHave(regularInfo.get("channel"), channel)
					&& isHave(regularInfo.get("version"), version)
					&& !isHaveA(regularInfo.get("cityEn"), cityEn)
					&& Integer.valueOf(regularInfo.get("an"))<activeDays
					&& (StringUtils.isBlank(regularInfo.get("reason"))||StringUtils.isBlank(reason)||regularInfo.get("reason").compareTo(reason)>0)){

				responseInfo.put("succ", true);
				String mr = regularInfo.get("mr");
				if(mr == null) {
					mr = baseRedisService.getString(CacheConstant.SITE_MATCH_RULE + fullDomain);
					if(mr==null ){
						mr = baseRedisService.getString(CacheConstant.SITE_MATCH_RULE + tdDomain);
					}
					if(mr==null)
					    return null;
				}
				mr = mr.replaceAll("RDS_CHAR_DOLLAR", "\\$");
				JSONObject mrInfo = new JSONObject();
                if(StringUtils.isNotBlank(mr))
				    mrInfo =  JSONObject.parseObject(mr);

				JSONObject s = JSONObject.parseObject(regularInfo.get("zt"));
				if(s!=null && s.containsKey(channel)) mrInfo.put("zt", s.getLong(channel));
				else  mrInfo.put("zt", "");

				responseInfo.put("info", mrInfo);
				//responseInfo.put("x", regularInfo.get("x"));

			}else{
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfo);
		}
		
		return durl;
	}


}
