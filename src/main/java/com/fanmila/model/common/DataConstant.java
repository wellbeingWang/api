package com.fanmila.model.common;

import java.util.HashMap;
import java.util.Map;


/**
 * 缓存常量
 * @author lscm
 *
 */
public final class DataConstant {

	//
	public static final String REQUEST_MONGODB_PORT = "5586";
	public static final String REQUEST_MONGODB_HOST = "10.30.105.150";
	
	
	@SuppressWarnings("serial")
	public final static Map<String, String> DMP_MYSQL_PROD = new HashMap<String, String>() {{    
	    put("host", "10.30.105.60");    
	    put("port", "3306");      
	    put("user", "b5m_dmp");     
	    put("password", "488ac336161b125");     
	    put("database", "b5m_dmp");   
	}};
	
	
	@SuppressWarnings("serial")
	public final static Map<String, String> DMP_MYSQL_TEST = new HashMap<String, String>() {{    
	    put("host", "172.16.11.15");    
	    put("port", "3306");      
	    put("user", "bi");     
	    put("password", "izene123");     
	    put("database", "b5m_dump");   
	}}; 
	
	
	
	private DataConstant() {
	}
	

}
