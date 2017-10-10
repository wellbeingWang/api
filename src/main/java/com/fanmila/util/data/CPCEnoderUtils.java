package com.fanmila.util.data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author janwen
 * May 7, 2013 4:43:20 PM
 * 
 */
public class CPCEnoderUtils {

	
	/**
	 * encode字符串
	   @author janwen
	 * @param toencode
	 * @return
	 */
	public static String encodeString(String toencode){
		if(StringUtils.isNotBlank(toencode)){
			try {
				return URLEncoder.encode(toencode, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return toencode;
			}
		}
		return toencode;
	}
	
	
	/**
	 * decode string
	   @author janwen
	 * @param todecode
	 * @return
	 */
	public static String deccodeString(String todecode){
		if(StringUtils.isNotBlank(todecode)){
			try {
				return URLDecoder.decode(todecode, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return todecode;
			}
		}
		return todecode;
	}
	
	public static void main(String[] args) {
		System.out.println(iso2UTF8("ss中文"));
	}
	
	static final Log logger = LogFactory.getLog(CPCEnoderUtils.class);
	
	public static String iso2UTF8(String str){
		try {
			logger.info(new String(str.getBytes("UTF-8"),"UTF-8"));
			return new String(str.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
}
