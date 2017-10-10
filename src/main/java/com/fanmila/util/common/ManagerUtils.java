package com.fanmila.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


public class ManagerUtils {

	/**
	 * 是否为管理员
	 */
	public static boolean isNotManager(HttpServletRequest request){
		if( !"POST".equals(request.getMethod()) ) return true;
		
		String key = request.getParameter("mkey1");
		if(StringUtils.isBlank(key)) return true;
		
		String dateKey = new SimpleDateFormat("yyyyMMdd").format(new Date());	
		String mkey = "fanmila@izene"+dateKey;
		return !mkey.equals(key);
	}
}
