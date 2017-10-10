package com.fanmila.handlers.cps;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.cache.CacheConstant;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
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
public class CPSGetRuleHandler extends CPSURLHandler implements IDbHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	@Override
	protected ClickType getClickType() {
		return null;
	}

	@Override
	public String getRedirectURL(CPSURLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfo = new JSONObject();
		try {
			//sid为必传参数
			/*if(!context.getRequestData().containsKey("sid")||StringUtils.isBlank(context.getRequestData().getString("sid"))){
				responseInfo.put("succ", 0);
				//responseInfo.put("isSuccess", false);
				//responseInfo.put("msg", "sid is null!");
				return null;
			}*/
			String sid = context.getRequestData().getString("sid");
			String channel = context.getRequestData().getString("channel");
			String reason = context.getRequestData().getString("reason");
			String version = context.getRequestData().getString("version");
			//活跃天数
			Integer activeDays = context.getRequestData().containsKey("activeDays")?context.getRequestData().getInteger("activeDays"):0;
			//获取后台配置的升级信息t
			Map<String, String> ruleInfo = baseRedisService.getMap(CacheConstant.CPS_GET_RULE + sid);
			if(isHaveA(ruleInfo.get("channel"), channel) || ruleInfo.get("an")== null || Integer.valueOf(ruleInfo.get("an"))>=activeDays)
				return null;
			//获取客户端传递参数Sys
			String referrer = context.getRequestData().getString("referrer");
			//referrer = "https://www.2345.com";
			String tdDomain = urlAnalyzerManager.getFullDomain(StringUtil.isBlank(referrer)?context.getCurl():referrer);
			Map<String, String> regularInfo = baseRedisService.getMap(CacheConstant.CPS_GET_REGULAR_RULE + tdDomain + "_" + sid);
			if(regularInfo==null || regularInfo.isEmpty()){
				tdDomain = urlAnalyzerManager.getDomain(StringUtil.isBlank(referrer)?context.getCurl():referrer);
				regularInfo = baseRedisService.getMap(CacheConstant.CPS_GET_REGULAR_RULE + tdDomain + "_" + sid);
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
				//responseInfo.put("v", ruleInfo.get("version"))an;
				ruleInfo.remove("channel");
				ruleInfo.remove("active_day");
				ruleInfo.remove("version");
				responseInfo.putAll(ruleInfo);
				regularInfo.remove("an");
				regularInfo.remove("channel");
				responseInfo.put("y", Integer.valueOf(regularInfo.get("y")));
				responseInfo.put("z", Integer.valueOf(regularInfo.get("z")));
				regularInfo.remove("y");
				regularInfo.remove("z");
				regularInfo.remove("cityEn");
				//
				JSONObject s = JSONObject.parseObject(regularInfo.get("zt"));
				if(s!=null && s.containsKey(channel)) responseInfo.put("zt", s.getLong(channel));
				else  responseInfo.put("zt", "");
				regularInfo.remove("zt");
				responseInfo.putAll(regularInfo);
				//responseInfo.put("x", regularInfo.get("x"));

			}else{
				//responseInfo.put("an", ruleInfo.get("active_day"));
			}

		} catch (Exception e) {
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			context.setResponseData(responseInfo);
		}
		
		return durl;
	}

	
	public static void main(String[] args) {
		Integer a = 12;
		String b = Integer.toBinaryString(12);
		for (String i : b.split("")){
			System.out.println(i);
		}
		System.out.println(b.split(""));
		
	}


	@Override
	public String getRedirectURL(URLHandlerContext context) {
		return null;
	}

	@Override
	public String[] getApplyTO() {
		return new String[0];
	}

	@Override
	public String getFeedback(URLHandlerContext context) {
		return null;
	}
}
