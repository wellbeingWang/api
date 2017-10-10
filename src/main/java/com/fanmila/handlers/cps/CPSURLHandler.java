package com.fanmila.handlers.cps;

import com.fanmila.handlers.IUnionHandler;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.model.cache.CacheConstant;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.common.URLUtils;
import com.fanmila.util.common.UUIDShortUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CPSURLHandler implements IUnionHandler {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	protected URLAnalyzerManager urlAnalyzerManager;
	@Autowired
	private BaseRedisServiceImpl baseRedisService;

	private Map<String, String> extraParams;

	{
		extraParams = new LinkedHashMap<String, String>();
		extraParams = Collections.unmodifiableMap(extraParams);
	}

	public final String getRedirectURL(CPSURLHandlerContext context) {
		ClickHandlerInfo handlerInfo = context.getHandlerInfo();
		// 记录当前handler处理过该context
		handlerInfo.recordHandler(this);
		handlerInfo.setClickType(getClickType());
		return getRedirectURL(context, handlerInfo);
	}

	protected abstract ClickType getClickType();

	protected abstract String getRedirectURL(CPSURLHandlerContext context, ClickHandlerInfo handlerInfo);

	public String getFeedback(CPSURLHandlerContext context) {
		if (StringUtils.isBlank(context.getFb())) {
			String fbStr=getFbFirstStr(context);
			if (StringUtils.isBlank(context.getUuid())) {
				context.setFb(fbStr.concat(context.getId().substring(0,context.getId().length()-2)));
			} else {
				context.setFb(fbStr.concat(UUIDShortUtils.shorter(context.getUuid())));
			}
		}
		return context.getFb();
	}
	/**
	 * 根据处理器的上下文
	 * @param context
	 * @return
	 */
	private String getFbFirstStr(CPSURLHandlerContext context) {
		String fbStr="";
		//标识渠道
		String mark=baseRedisService.getString(CacheConstant.CLT_CHANNEL_CODE + context.getSource());//ChannelDBTool.findChannel(context.getSource());
		if (StringUtils.isNotBlank(mark)) {
			fbStr=fbStr.concat(mark);
		} else {
			fbStr=fbStr.concat("zzz");
		}
		//标识产品
		String mark2="";//ChannelDBTool.findSid(context.getSid());
		if (StringUtils.isNotBlank(mark2)) {
			fbStr=fbStr.concat(mark2);
		} else {
			fbStr=fbStr.concat("ZZ");
		}
		//判断是否开启LV2，
		if (context.getHandlerInfo().isSl()) {
			fbStr=fbStr.concat("2");
		} else {
			fbStr=fbStr.concat("1");
		}
		fbStr=fbStr.concat(context.getSid());
		return fbStr;
	}

	/**
	 * 从数据库获取渠道标识
	 * @param channel
	 * @return
	 */
	private String getMarkFromDB(String channel)
	{
		/*Query query=new Query(Criteria.where("channel").is(channel));
		DBObject dbObject=channelMapTemplate.findOne(query, DBObject.class,"channelMark");
		return (dbObject==null?"":String.valueOf(dbObject.get("mark")));*/
		
		return "channel";
	}

	private String getSidMarkFromDB(String sid)
	{
		/*Query query=new Query(Criteria.where("sid").is(sid));
		DBObject dbObject=channelMapTemplate.findOne(query, DBObject.class,"sidMark");
		return (dbObject==null?"":String.valueOf(dbObject.get("mark")));*/
		return "sid";
	}
	/**
	 * 获取清理后的目标URL
	 * 
	 * @author Larry Lang
	 * @email larry.lang@b5m.com
	 * @date Jan 11, 2013
	 * 
	 * @param context
	 *            ,依赖属性:dest、destDomain
	 * @return 清理后的目标URL
	 */
	protected String getTrimDestUrl(CPSURLHandlerContext context) {
		return urlAnalyzerManager.cleanURL(context.getTurl(), context.getTdomain());
	}

	/**
	 * 从目标URL中获取目标商品的ID属性
	 * 
	 * @author Larry Lang
	 * @email larry.lang@b5m.com
	 * @date Jan 11, 2013
	 * 
	 * @param context
	 *            ，依赖属性:dest、destDomain
	 * @return 清理后的目标URL
	 */
	protected String getGoodsId(CPSURLHandlerContext context) {
		return urlAnalyzerManager.getGoodsId(context.getTurl(), context.getTdomain());
	}

	protected String appendParams(String url, CPSURLHandlerContext context) {
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

	private boolean isUnSportAppendParams(CPSURLHandlerContext context) {
		try {
			return true;//unSportAppendParamsSite.contains(context.getTdomain());
		} catch (Exception e) {
			return true;
		}
	}
	public static boolean isHave(String s, String t) {
		if (StringUtils.isBlank(s)) return true;
		String[] strs = s.split(",");
		for(int i=0;i<strs.length;i++) {
			//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
			if(strs[i].equals(t)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isHaveA(String s, String t) {
		if (StringUtils.isBlank(s)) return false;
		String[] strs = s.split(",");
		for(int i=0;i<strs.length;i++) {
			//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
			if(strs[i].equals(t)) {
				return true;
			}
		}
		return false;
	}
	
	protected String getToUrl(CPSURLHandlerContext context){
		String urlto = context.getTurl();
		//目的地址转换、清洗 通过程序中的正则表达式解析
		urlto = getTrimDestUrl(context);
		
		//目的地址拼接额外参数
		if(false){///目前目的地址暂时不拼接额外的参数，因为目前拼接上无任何意义
			urlto = appendParams(urlto, context);
		}
		Map<String,String> extParamMap=new LinkedHashMap<String, String>();
		if(null != context.getPid()&&!"".equals(context.getPid().trim())){
			try {
				String paramOther = context.getPid().trim();
				String [] p1= paramOther.split("&");
				for(String p11 :p1){
					if(p11.indexOf("=")>-1){
						String []pp1=p11.split("=");
						extParamMap.put(pp1[0], pp1[1]);
					}
				}
			} catch (Exception e) {
			}
			urlto=URLUtils.appendOrUpdateParams( urlto , extParamMap);
		}
		return urlto;
	}
}
