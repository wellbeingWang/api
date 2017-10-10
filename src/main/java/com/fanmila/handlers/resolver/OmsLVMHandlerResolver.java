package com.fanmila.handlers.resolver;


import com.fanmila.handlers.oms.Lv1DhConfigHandler;
import com.fanmila.handlers.oms.OmsLVMResponseHandler;
import com.fanmila.model.URLHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fanmila.util.framework.ContextUtils;

@Component
public class OmsLVMHandlerResolver {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 *根据活动代码跳转到指定广告链接

	 * </pre>
	 * @param context
	 * @return
	 */
	public String getLVMInfo(URLHandlerContext context) {
		OmsLVMResponseHandler dbHandler = ContextUtils.getApplicationContext().getBean(OmsLVMResponseHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(StringUtils.isNotBlank(durl)){
			
			return durl;
		}
		

		//durl = defaultHandler.getRedirectURL(context);
		return durl;
	}
	
	public String getLV1DhConfig(URLHandlerContext context) {
		Lv1DhConfigHandler dbHandler = ContextUtils.getApplicationContext().getBean(Lv1DhConfigHandler.class);
		String durl = dbHandler.getRedirectURL(context);
		if(StringUtils.isNotBlank(durl)){
			
			return durl;
		}
		
		
		return durl;
	}

}
