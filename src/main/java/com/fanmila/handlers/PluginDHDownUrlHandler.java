package com.fanmila.handlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.common.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class PluginDHDownUrlHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	private String rulekey  = "fml_dh_account_";
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "";
		try {
			String productId = context.getRequestData().getString("productId");
			if(StringUtils.isBlank(productId)) return null;
			if(StringUtils.isBlank(context.getCurl())) return null;
			String commonUrl = urlAnalyzerManager.getFullDomain(context.getCurl());
			String key = rulekey.concat(commonUrl).concat("_").concat(productId);
			//版本信息
			String urlRule =baseRedisService.getString(key);
			if(StringUtils.isBlank(urlRule)) return null;
			JSONArray urlRuleArray = JSONArray.parseArray(urlRule);
			int mod= UUIDUtils.moduuid(context.getUuid(), context.getUuid().length()-4, context.getUuid().length(), 100);
			for(Object jo : urlRuleArray){
				JSONObject urlAccount = (JSONObject)jo;
				Integer startRange = urlAccount.getInteger("start_range");
				Integer endRange = urlAccount.getInteger("end_range");
				if(mod>=startRange && mod< endRange){
					durl = urlAccount.getString("cps_url");
					break;
				}
			}


		} catch (Exception e) {
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			context.setStringInfo(durl);
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


}
