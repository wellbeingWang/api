package com.fanmila.handlers;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class PluginMatchRuleHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	private String rulekey  = "match_rule_20";
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfo = new JSONObject();
		try {
			//版本信息
			Map<String, String > matchRuleMap =baseRedisService.getMap(rulekey);
			if(matchRuleMap.isEmpty()) return null;
            String channel = matchRuleMap.get("channel");
			if(isHaveA(channel, context.getRequestData().getString("channel"))){
				responseInfo.put("succ", false);
				return null;
			}
			responseInfo.put("succ", true);
			String mr = matchRuleMap.get("mr");
			if(mr == null) return null;
			mr = mr.replaceAll("RDS_CHAR_DOLLAR", "\\$");
			responseInfo.put("info", JSONObject.parseObject(mr));

		} catch (Exception e) {
			responseInfo.put("succ", false);
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfo);
		}
		
		return durl;
	}


}
