package com.fanmila.handlers;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.cache.CacheConstant;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.AlloydingEncrypt;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.framework.MD5Utils;
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
public class CLTEncyptJsHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfo = new JSONObject();
		try {
			String productId = context.getRequestData().getString("productId");
			productId = StringUtils.isBlank(productId)?"1":productId;
			//获取后台配置的升级信息
			Map<String, String> dbInfo = baseRedisService.getMap(CacheConstant.CLT_ENCYPT_JS + productId);
			//获取客户端传递参数
			//版本信息
			String version = context.getRequestData().containsKey("version")?context.getRequestData().getString("version"):"";
			//用户标识uuid
			String uuid = context.getRequestData().containsKey("uuid")?context.getRequestData().getString("uuid"):"";
			String lastuuid = uuid.length()>1?uuid.substring(uuid.length()-1):"";
			//渠道信息
			String channel = context.getRequestData().containsKey("channel")?context.getRequestData().getString("channel"):"";
			String fileMd5 = context.getRequestData().containsKey("fileMd5")?context.getRequestData().getString("fileMd5"):"";
			String reason = context.getRequestData().containsKey("reason")?context.getRequestData().getString("reason"):"";
			String dbFileMd5 = MD5Utils.md5(dbInfo.get("file"));
			if(isHave(dbInfo.get("version"), version)
					&& isHave(dbInfo.get("channel"), channel)
					&& isHave(dbInfo.get("lastuuid"), lastuuid)
					&& (StringUtils.isBlank(dbInfo.get("reason"))||StringUtils.isBlank(reason)||dbInfo.get("reason").compareTo(reason)>0)
					&& !dbFileMd5.equals(fileMd5)){
				context.setStringInfo(dbFileMd5 + AlloydingEncrypt.cryptCltJsStr(dbInfo.get("file")));
			/*	responseInfo.put("fm", dbFileMd5);
				responseInfo.put("ej", AlloydingEncrypt.cryptCltJsStr(dbInfo.get("file")));*/
			}else{
				//responseInfo.put("isSuccess", true);
			}
		} catch (Exception e) {
			//responseInfo.put("isSuccess", false);
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfo);
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
		/*String uuid = "1234567890s";
		Calendar cal = Calendar.getInstance();
		System.out.println(cal);
		int hour =  cal.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour);
		System.out.println(uuid.substring(uuid.length()-1));
		System.out.println("1.0.1".compareTo("1.0.0"));*/
		
	}



}
