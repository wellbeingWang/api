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
public class PluginSSDownUrlHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	private String rulekey  = "fml_ss_account_";
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfo = new JSONObject();
		responseInfo.put("succ", false);
		try {
			String productId = context.getRequestData().getString("productId");
			if(StringUtils.isBlank(productId)) return null;
			String domain = context.getRequestData().getString("domain");
			if(StringUtils.isBlank(domain)) return null;
			String key = rulekey.concat(domain).concat("_").concat(productId);
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
					String str=String.format(urlAccount.getString("cps_url"), context.getRequestData().getString("text"));
					responseInfo.put("curl", str);
					responseInfo.put("surl",urlAccount.containsKey("source_url")?urlAccount.getString("source_url"):"");
					responseInfo.put("succ", true);
					break;
				}
			}


		} catch (Exception e) {
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfo);
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
