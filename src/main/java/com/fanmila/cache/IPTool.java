package com.fanmila.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanmila.util.IPToolUtil;

public class IPTool {
	private static Logger logger = LoggerFactory.getLogger(IPTool.class);
	
	

	public IPTool() {
		super();
		// TODO Auto-generated constructor stub
	}


	public static void downloadIpFile() {
		// TODO Auto-generated method stub
		if(!IPToolUtil.downIPFile()){
			for(int i=1 ;i<=5; i++){
				if(IPToolUtil.downIPFile()){
					break;
				}else{
					logger.error("**************Download　Error,Try agine :time is  "+i);
				}
			}
		}
		
	}

}
