package com.fanmila.util.url;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUtils {

	// 识别域名的模式
	private static final Pattern PATTERN_DOMAIN = Pattern
			.compile("//([\\w|-]+\\.)*?(\\w+(\\.\\w{2,3})(\\.\\w{2})?)(?=/|\\?|#|$)");
	
	private static Map<String,UrlRefiner> REF_MAP = new HashMap<String,UrlRefiner>(50);
	private static Map<String, String> REPLACE_DOMAIN = new HashMap<String,String>();
	private static Map<String, String> DETAIL_URL_MAP = new HashMap<String, String>();

	static {
		// 初始化domain替换map
		REPLACE_DOMAIN.put("360buy.com", "jd.com");
		REPLACE_DOMAIN.put("51buy.com", "yixun.com");
		REPLACE_DOMAIN.put("yihaodian.com", "yhd.com");
		REPLACE_DOMAIN.put("coo8.com", "gome.com.cn");
		
		// 初始化标准地址过滤器
		UrlRefiner[] refiners = UrlRefiner.values();
		for(UrlRefiner refiner: refiners){
			REF_MAP.put(refiner.getDomain(), refiner);
		}
	}
	
	/**
	 * 描述：根据文档url获取它对应的域名；<br>
	 * 例如： http://detail.tmall.com/item.htm?id=35614306816 --> tmall.com
	 * 
	 * @param docUrl
	 * @return
	 */
	public static String getDomain(String url) {
		Matcher m = PATTERN_DOMAIN.matcher(url);
		if (m.find()) {
			return m.group(2);
		}

		return null;
	}

	/**
	 * 去除商品url中不纯净的部分，获得干净的url；
	 * 
	 * @param url
	 * @return
	 */
	public static String getRefineUrl(String url) {
		
		String domain = getDomain(url);
		url = replaceDomain(url, domain);
		UrlRefiner refiner = REF_MAP.get(domain);
		
		if(refiner != null){
			return refiner.refine(url);
		}
		
		return null;
	}
	
	/**
	 * 商品详情爬取url过滤
	 */
	public static String getRefinerDetailUrl(String url){
		String domain = getDomain(url);
		if(UrlUtils.detailUrlMap.get(domain) != null)
			return url;
		return null;
	}
	
	/**
	 * 把已经更改域名的那些url替换成新的域名； eg: 360buy.com --> jd.com
	 * 
	 * @param url
	 * @return
	 */
	private static String replaceDomain(String url, String domain){
		String newDomain = REPLACE_DOMAIN.get(domain);
		if(newDomain != null){
			return url.replaceFirst(domain, newDomain);
		}
		return url;
	}
}
