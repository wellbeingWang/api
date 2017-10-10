package com.fanmila.handlers.resolver;


import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.handlers.IUnionHandler;
import com.fanmila.handlers.cps.DefaultUnionHandler;
import com.fanmila.handlers.taoke.TaokeURLDbHandler;
import com.fanmila.util.framework.ContextUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaokeURLDbHandlerResolver {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	* @Title: getRedirectURL 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param context
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getRedirectURL(CPSURLHandlerContext context) {
		//数据库定制cps规则先处理
		String durl = getRedirectURLFromDb(context);
		if(StringUtils.isNotBlank(durl)){
			context.setDurl(durl);
			return durl;
		}
		
		IUnionHandler defaultUnionHandler = ContextUtils.getApplicationContext().getBean(DefaultUnionHandler.class);
		String text = context.getRequestData().getString("tt").replace(" ", "");
		boolean hasin = context.getRequestData().getBoolean("hasin");
		String turl = "https://www.tmall.com";
		if(text.contains("淘宝")) turl = hasin?null:"https://www.taobao.com";
		if(text.contains("聚划算")) turl = hasin?null:"https://ju.tmall.com";
		context.setTurl(hasin?null:turl);
		
		durl = defaultUnionHandler.getRedirectURL(context);
		//记录错误日志
		return durl;
	}

	
	private String getRedirectURLFromDb(CPSURLHandlerContext context) {

		TaokeURLDbHandler dbHandler = ContextUtils.getApplicationContext().getBean(TaokeURLDbHandler.class);
		return dbHandler.getRedirectURL(context);
	}
	
	
}
