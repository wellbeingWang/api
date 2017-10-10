package com.fanmila.handlers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fanmila.model.URLHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fanmila.util.common.URLUtils;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.google.common.collect.Maps;

public abstract class DmpURLHandler implements IUnionHandler {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	protected URLAnalyzerManager urlAnalyzerManager;
	
	private String adType; 

	private Map<String, String> extraParams;

	{
		extraParams = new LinkedHashMap<String, String>();
		extraParams.put("utm_source", "fanmila.com");
		extraParams.put("utm_medium", "b5t");
		extraParams = Collections.unmodifiableMap(extraParams);
	}

	@Override
	public final String getRedirectURL(URLHandlerContext context) {
		ClickHandlerInfo handlerInfo = context.getHandlerInfo();
		// 记录当前handler处理过该context
		handlerInfo.recordHandler(this);
		String url = getRedirectURL(context, handlerInfo);
		//handlerInfo.setClickType(getClickType());
		return url;
	}
	


	protected abstract String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo);

	public String getFeedback(URLHandlerContext context) {
		return null;

	}

	protected abstract ClickType getClickType();

	protected String appendParams(String url, URLHandlerContext context) {
		if (isUnSportAppendParams(context)) {
			return url;
		}
		Map<String, String> params = Maps.newLinkedHashMap();
		params.putAll(getExtraParams());
		params.put("a", getFeedback(context));
		if (context.getHandlerInfo().isSl()) {
			params.remove("utm_source");
			params.remove("utm_medium");
		}
		return URLUtils.appendOrUpdateParams(url, params);
	}

	@Override
	public Map<String, String> getExtraParams() {
		return extraParams;
	}

	private boolean isUnSportAppendParams(URLHandlerContext context) {

			return true;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}
	

}
