package com.fanmila.handlers;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.cache.CacheConstant;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class CLTSCJUrlHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "";
		JSONObject responseInfo = new JSONObject();
		try {

			if(!context.getRequestData().containsKey("urlId")) return null;
			String urlId = context.getRequestData().getString("urlId");
			//获取后台配置的信息
			String urls = baseRedisService.hget(CacheConstant.CLT_SCJ_URL, urlId);
			if(StringUtils.isBlank(urls)) return null;
			String[] urlList = urls.split(",");
			int length = urlList.length;
			Random random = new Random();
			int i = random.nextInt(length);
			durl = urlList[i];

		} catch (Exception e) {
			//responseInfo.put("isSuccess", false);
			e.printStackTrace();
			return null;
		}finally{
			context.setStringInfo(durl);
		}
		
		return durl;
	}

	
	public static void main(String[] args) {

		/*Integer a = 12;
		String b = Integer.toBinaryString(12);
		for (String i : b.split("")){
			System.out.println(i);
		}
		System.out.println(b.split(""));*/

		Random random = new Random();
		int i = random.nextInt(3);
		System.out.println(i);
		/*String uuid = "1234567890s";
		Calendar cal = Calendar.getInstance();
		System.out.println(cal);
		int hour =  cal.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour);
		System.out.println(uuid.substring(uuid.length()-1));
		System.out.println("1.0.1".compareTo("1.0.0"));*/
		
	}



}
