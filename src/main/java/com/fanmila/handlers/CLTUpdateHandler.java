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

import java.util.Calendar;
import java.util.Map;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class CLTUpdateHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfo = new JSONObject();
		int httpStatus = 0;
		try {
			//sid为必传参数
			/*if(!context.getRequestData().containsKey("sid")||StringUtils.isBlank(context.getRequestData().getString("sid"))){
				responseInfo.put("succ", 0);
				//responseInfo.put("isSuccess", false);
				//responseInfo.put("msg", "sid is null!");
				return null;
			}*/
			String productId = context.getRequestData().getString("productId");
			productId = StringUtils.isBlank(productId)?"1":productId;
			//获取后台配置的升级信息
			Map<String, String> lastUpdateInfo = baseRedisService.getMap(CacheConstant.CLT_UPDATE_INFO + productId);
			//获取客户端传递参数
			//版本信息
			String version = context.getRequestData().containsKey("version")?context.getRequestData().getString("version"):"";
			//操作系统
			String os = context.getRequestData().containsKey("os")?context.getRequestData().getString("os"):"";
			//用户标识uuid
			String uuid = context.getRequestData().containsKey("uuid")?context.getRequestData().getString("uuid"):"";
			String lastuuid = uuid.length()>1?uuid.substring(uuid.length()-1):"";
			//渠道信息
			String channel = context.getRequestData().containsKey("channel")?context.getRequestData().getString("channel"):"";
			//升级包类型 dat/exe
			String flag = context.getRequestData().containsKey("flag")?context.getRequestData().getString("flag"):"";
			String cversion = context.getRequestData().containsKey("cversion")?context.getRequestData().getString("cversion"):"";
			//升级包类型 dat/exe
			Integer activeDay = context.getRequestData().containsKey("activeDay")?context.getRequestData().getInteger("activeDay"):0;

			//cversion为空，为老版本，都不升级
			if(StringUtils.isBlank(cversion) && !version.equals("1.0.0") ){
				responseInfo.put("succ", 0);
				return null;
			}
			if(lastUpdateInfo.get("succ")==null) {
				responseInfo.put("succ", 0);
				return null;
			}
			Integer succ = Integer.valueOf(lastUpdateInfo.get("succ"));
			if(succ.compareTo(1)==0){
				version = cversion;
			}
			//请求的版本大于等于后台配置版本则升级，反正不升级
			if(StringUtils.isBlank(version)
					|| StringUtils.isBlank(lastUpdateInfo.get("version"))
					//|| version.compareTo(lastUpdateInfo.get("version"))>=0
					|| !versionCompareTo(version, lastUpdateInfo.get("version"))){
				responseInfo.put("succ", 0);
				return null;
			}
			if(isHave(lastUpdateInfo.get("os"), os)
					&& isHave(lastUpdateInfo.get("uuid"), lastuuid)
					&& activeDay >= Integer.valueOf(lastUpdateInfo.get("active_day"))
					&& isHave(lastUpdateInfo.get("channel"), channel)){
				//responseInfo.put("isSuccess", true);
				responseInfo.put("md5", lastUpdateInfo.get("md5"));
				responseInfo.put("succ", succ);
				responseInfo.put("version", lastUpdateInfo.get("version"));
				responseInfo.put("url", lastUpdateInfo.get("url"));
				try{
					//获取后台服务是否开启的判断信息
					Map<String, String> serviceOpneOrNot = baseRedisService.getMap(CacheConstant.CLT_SERVICE_OPEN + productId);
					//服务是否开启的判断
					if("1".equals(serviceOpneOrNot.get("flag"))
							&&!isHaveA(serviceOpneOrNot.get("version"), version)
							&&!isHaveA(serviceOpneOrNot.get("channel"), channel)){
						httpStatus = 202;
					}
				}catch (Exception e){

				}

				//responseInfo.put("succ", StringUtils.isBlank(lastUpdateInfo.get("succ"))? 1 : lastUpdateInfo.get("succ"));
				//responseInfo.put("msg", "success!");
			} else {
				//responseInfo.clear();
				responseInfo.put("succ", 0);
				//responseInfo.put("msg", "no update!");
			}
		} catch (Exception e) {
			responseInfo.put("succ", 0);
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfo);
			context.setHttpStatus(httpStatus);
		}
		
		return durl;
	}
	
	/**
	 * 
	* @Title: isHave 
	* @Description: 判断某字符串数组中是否包含指定字符串
	* @param @param strs
	* @param @param s
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
/*	public static boolean isHave(String[] strs, String s) {	
		if(StringUtils.isBlank(strs[0])&&strs.length==1) return true;
		for(int i=0;i<strs.length;i++) {		
			//循环查找字符串数组中的每个字符串中是否包含所有查找的内容		
			if(strs[i].equals(s)) {
				return true;
			}
		}
		return false;
     }
	*/

	

	
	public static void main(String[] args) {
		String uuid = "1234567890s";
		Calendar cal = Calendar.getInstance();
		System.out.println(cal);
		int hour =  cal.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour);
		System.out.println(uuid.substring(uuid.length()-1));
		System.out.println("1.0.1".compareTo("1.0.0"));
		
	}



}
