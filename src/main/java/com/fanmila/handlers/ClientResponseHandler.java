package com.fanmila.handlers;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.common.CacheConstant;
import com.fanmila.model.omslvm.LVMChannelFilterModel;
import com.fanmila.model.omslvm.LVMFilterModel;
import com.fanmila.model.omslvm.LVMMerchantFilterModel;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.SerializeUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.common.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

/**
 * 处理
 * @author zhenyuanzi
 *
 */
@Component
public class ClientResponseHandler extends CltApiURLHandler{
	

	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);
	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		JSONObject responseJson = new JSONObject();

		return responseJson.toJSONString();
	}



}
