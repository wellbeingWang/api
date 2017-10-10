package com.fanmila.handlers.cps;

import com.fanmila.handlers.IUnionHandler;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;

//@Component
//@Lazy
public class CPSURLHandlerResolver {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	private Object[] handlers;

	/**
	 * 不走联盟的商家
	 */
	private Set<String> thirdPartyIgnore = Sets.newHashSet("yishangguan.com", "tmall.com");


	/**
	 * 根据目标URL生成CPS引导URL，按以下步骤处理，只要一个步骤处理成功就返回
	 * 
	 * <pre>
	 * 	1、尝试用直接接入处理器处理
	 * 	2、尝试用第三方联盟处理器处理
	 * 	3、用默认处理器处理
	 * </pre>
	 * 
	 * @author Larry Lang
	 * @email larry.lang@b5m.com
	 * @date Jan 7, 2013
	 * 
	 * @param context
	 * @return
	 */
	public String getRedirectURL(CPSURLHandlerContext context) {
		context.getHandlerInfo().setSl(false);
		String durl = null;
		for (Object handler : handlers) {
			durl = getRedirectURL(handler, context);
			if (StringUtils.isNotBlank(durl)) {
				context.setDurl(durl);
				return durl;
			}
		}
		return null;
	}

	private String getRedirectURL(Object obj, CPSURLHandlerContext context) {
		IUnionHandler handler = null;
		String domain = urlAnalyzerManager.getDomain(context.getTurl());//去短域名
		if (Map.class.isInstance(obj)) {
			Object object = Map.class.cast(obj).get(domain+"_"+context.getSid());
			//如果短域名没配置，取全域名
			if (object==null) {
				domain=urlAnalyzerManager.getCommonDomain(context.getTurl());//取全域名
				object = Map.class.cast(obj).get(domain+"_"+context.getSid());
			}
			if (object != null && IUnionHandler.class.isInstance(object)) {
				handler = IUnionHandler.class.cast(object);
				context.setTdomain(domain);
				return handler.getRedirectURL(context);
			}
		} else if (IUnionHandler.class.isInstance(obj)) {
			handler = IUnionHandler.class.cast(obj);
			return handler.getRedirectURL(context);
		}
		return null;
	}

	@PreDestroy
	private void destroy() {
		handlers = null;
	}
}
