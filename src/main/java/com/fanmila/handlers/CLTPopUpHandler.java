package com.fanmila.handlers;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.cache.CacheConstant;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
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
public class CLTPopUpHandler extends CltApiURLHandler{
	
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
			//获取客户端传递参数Sys
            String productId = context.getRequestData().getString("productId");
			String channel = context.getRequestData().getString("channel");
			String reason = context.getRequestData().getString("reason");
			String type = context.getRequestData().getString("type");
			String version = context.getRequestData().getString("version");
			if(StringUtils.isBlank(productId)) return null;
			//活跃天数
			Integer activeDays = context.getRequestData().getInteger("activeDays");

			//获取后台配置的升级信息t
			String redisKey = CacheConstant.CLT_POP_UP  + productId;
			if("2".equals(type)) redisKey = CacheConstant.CLT_BOTTOM_RIGHT_POP_UP  + productId;
			Map<String, String> regularInfo = baseRedisService.getMap(redisKey);
			if(regularInfo==null || regularInfo.isEmpty()){
				return null;
			}
			//白名单限制
			if(!isHave(regularInfo.get("wchannel"), channel)
					|| !isHave(regularInfo.get("wversion"), version)){
				return null;
			}

			if(StringUtils.isBlank(regularInfo.get("an"))) regularInfo.put("an", "3");

			//客户端IP
			String ipString = context.getRequestData().getString("ip");
			JSONObject jsonObject = IPToolUtil.getCityofIp(ipString);
			String cityEn = jsonObject.getString("cityEn");

			if(!regularInfo.isEmpty() && regularInfo.containsKey("an")
					&& !isHaveA(regularInfo.get("channel"), channel)
					&& !isHaveA(regularInfo.get("version"), version)
					&& !isHaveA(regularInfo.get("cityEn"), cityEn)
					&& Integer.valueOf(regularInfo.get("an"))<activeDays){

				responseInfo.put("succ", true);

				JSONObject mrInfo = new JSONObject();
				if("2".equals(type)){
					mrInfo.put("nid",regularInfo.get("nid") );
					mrInfo.put("img",regularInfo.get("img") );
					mrInfo.put("url",regularInfo.get("url") );
					mrInfo.put("content",regularInfo.get("content") );
					mrInfo.put("title",regularInfo.get("title") );
					mrInfo.put("desc",regularInfo.get("desc") );
					mrInfo.put("more",regularInfo.get("more") );
				}else{
					mrInfo.put("type",regularInfo.get("type") );
					mrInfo.put("nid",regularInfo.get("nid") );
					mrInfo.put("width",regularInfo.get("width") );
					mrInfo.put("height",regularInfo.get("height") );
					mrInfo.put("url",regularInfo.get("url") );
					mrInfo.put("delay",regularInfo.get("delay") );
					mrInfo.put("poll",regularInfo.get("poll") );
				}

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
