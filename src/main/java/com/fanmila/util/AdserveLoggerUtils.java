package com.fanmila.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
/**
 * Adserve日志组装类
 * @author feitian
 *
 */
public class AdserveLoggerUtils {
	
		//private static IPSeeker ipseeker = IPSeeker.getInstance("qqwry.dat");
		/**
		 * 媒体日志填充方法
		 * @param request 
		 * 
		 * @return
		 */
		public static Map<String, String> logFiller(boolean sign,
				HttpServletRequest request, JSONObject requestData, JSONObject adInfo) {
			Map<String, String> logEntry = new HashMap<String, String>();
			
			logEntry.put("sign", sign ? "1" : "0");
			
			String adId = adInfo != null && adInfo.getString("adId") != null ? adInfo.getString("adId") : "0";
			logEntry.put("ad_id", adId);
			
			String unoinName = adInfo != null && adInfo.getString("unionName") != null ? adInfo.getString("unionName") : "unknown";
			logEntry.put("union_name", unoinName);
			
			String referer = request.getHeader("Referer");
			if (referer == null) {
				logEntry.put("from", "unknow");
			} else {
				logEntry.put("from", referer);
			}
			
			String adcode = adInfo==null ? null : adInfo.getString("adcode");
			if(adcode==null || "".equals(adcode)) {
				adcode = request.getParameter("action");
				if (adcode == null) {
					adcode = request.getParameter("adcode");
				}
			}
			logEntry.put("adcode", adcode != null && !"".equals(adcode) ? adcode: "unknown");
	
			Cookie cookie = WebCookieComponent.getCookie("b5madexchange", request);
	
			logEntry.put("user_agent", request.getHeader("User-Agent"));
			if (cookie == null) {
				logEntry.put("cookie",
						request.getAttribute("tmpcookie") != null ? request
								.getAttribute("tmpcookie").toString() : "");
			} else {
				logEntry.put("cookie", cookie.getValue());
			}
			//String regionStr = ipseeker.getIPLocation(ip).getProvince();
			//logEntry.put("region", regionStr);
			return logEntry;
		}
	
}

