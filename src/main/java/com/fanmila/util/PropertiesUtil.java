package com.fanmila.util;

import java.util.ResourceBundle;

/**
 * 从配置文件读取配置
 * @author Mar 13, 2013 4:32:59 PM
 * 
 */
public class PropertiesUtil {

	public static String getValue(String bundle, String key) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle(bundle);
			return rb.getString(key);
		} catch(Exception e) {
			return null;
		}
	}
	
	
}
