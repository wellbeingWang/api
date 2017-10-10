package com.fanmila.handlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.common.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class PluginDHSSMatchRuleHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	private String rulekey  = "fml_dh_match_rule_";

	private String downUrlkeyPre  = "fml_dh_account_";
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfoL = new JSONObject();
		JSONObject responseInfo = new JSONObject();
		responseInfoL.put("succ", false);
		try {
			String productId = context.getRequestData().getString("productId");
			if(StringUtils.isBlank(productId)) return null;
			//版本信息
			Map<String, String > matchRuleMap =baseRedisService.getMap(rulekey.concat(productId));
			if(matchRuleMap.isEmpty()) return null;
			//基础信息判断
			String basicRule = matchRuleMap.get("basic");
			JSONObject basicRuleJson = JSONObject.parseObject(basicRule);
			if(basicRuleJson.containsKey("ext_param"))
			    responseInfo.putAll(basicRuleJson.getJSONObject("ext_param"));
			if(basicRuleJson.isEmpty()) return null;
            String channel = basicRuleJson.containsKey("channel")?basicRuleJson.getString("channel"):"";
			String version = basicRuleJson.containsKey("version")?basicRuleJson.getString("version"):"";
			Integer activeDays = basicRuleJson.containsKey("an")?basicRuleJson.getInteger("an"):0;
			//渠道黑名单和活跃天数判断
			if(isHaveA(channel, context.getRequestData().getString("channel"))
					|| isHaveA(version, context.getRequestData().getString("version"))
					|| activeDays.compareTo(context.getRequestData().getInteger("activeDay"))>=0){
				return null;
			}

			//各个分网站判断
			matchRuleMap.remove("basic");
			for(String key : matchRuleMap.keySet()){
				JSONObject keyRule = JSONObject.parseObject(matchRuleMap.get(key));
				String channel2 = keyRule.containsKey("channel")?keyRule.getString("channel"):"";
				String version2 = keyRule.containsKey("version")?keyRule.getString("version"):"";
				Integer activeDays2 = keyRule.containsKey("an")?keyRule.getInteger("an"):0;
				if(!isHave(channel2, context.getRequestData().getString("channel"))
						|| !isHave(version2, context.getRequestData().getString("version"))
						|| activeDays2.compareTo(context.getRequestData().getInteger("activeDay"))>=0){
					continue;
				}
				String ipString = context.getRequestData().getString("ip");
				JSONObject jsonObject = IPToolUtil.getCityofIp(ipString);
				String cityEn = jsonObject.getString("cityEn");
				String cityEnSSRedis = keyRule.containsKey("cityEn_ss")?keyRule.getString("cityEn_ss"):"";
				String cityEnDHRedis = keyRule.containsKey("cityEn_dh")?keyRule.getString("cityEn_dh"):"";
				//IP 判断
				if (isHaveA(cityEnSSRedis, cityEn) && isHaveA(cityEnDHRedis, cityEn)) continue;
				JSONObject keyInfo = new JSONObject();

				String ruleSS = keyRule.containsKey("rule_ss")?keyRule.getString("rule_ss"):"";
				String ruleDH = keyRule.containsKey("rule_dh")?keyRule.getString("rule_dh"):"";
				if(StringUtils.isBlank(ruleSS)  && StringUtils.isBlank(ruleDH)) continue;

				JSONObject ruleJson = new JSONObject();

				if(!isHaveA(cityEnSSRedis, cityEn)){
					handleDHSSRuleInfo(ruleSS, ruleJson);
				}

				if(!isHaveA(cityEnDHRedis, cityEn)){
					handleDHRuleInfo(key, productId, context.getUuid(), ruleDH, ruleJson);
				}
				if(keyRule.containsKey("ext_param"))
					keyInfo.putAll(keyRule.getJSONObject("ext_param"));

				keyInfo.put("r",ruleJson);
				responseInfo.put(key, keyInfo);

			}
			responseInfoL.put("info", responseInfo);

			responseInfoL.put("succ", true);

		} catch (Exception e) {
			responseInfoL.put("succ", false);
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfoL);
		}
		
		return durl;
	}

	private void handleDHSSRuleInfo(String ruleInfo, JSONObject responseJson){
		if(StringUtils.isNotBlank(ruleInfo)) {
			ruleInfo = ruleInfo.replaceAll("RDS_CHAR_DOLLAR", "\\$");
			JSONObject ruleDHJson = (JSONObject)JSONObject.parse(ruleInfo);
			responseJson.putAll(ruleDHJson);
		}
	}

	private void handleDHRuleInfo(String key, String productId, String uuid, String ruleInfo, JSONObject responseJson){
		if(StringUtils.isNotBlank(ruleInfo)) {
			ruleInfo = ruleInfo.replaceAll("RDS_CHAR_DOLLAR", "\\$");
			JSONObject ruleDHJson = (JSONObject)JSONObject.parse(ruleInfo);
			String downUrlkey = downUrlkeyPre.concat(productId);
			//版本信息
			String urlRule =baseRedisService.hget(downUrlkey, key);
			if(StringUtils.isBlank(urlRule)) return;
			JSONArray urlRuleArray = JSONArray.parseArray(urlRule);
			int mod= UUIDUtils.moduuid(uuid, uuid.length()-4, uuid.length(), 100);
			String durl = "";
			for(Object jo : urlRuleArray){
				JSONObject urlAccount = (JSONObject)jo;
				Integer startRange = urlAccount.getInteger("start_range");
				Integer endRange = urlAccount.getInteger("end_range");
				if(mod>=startRange && mod< endRange){
					durl = urlAccount.getString("cps_url");
					break;
				}
			}
			ruleDHJson.put("a", durl);
			responseJson.putAll(ruleDHJson);
		}
	}


}
