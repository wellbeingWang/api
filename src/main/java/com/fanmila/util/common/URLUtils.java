package com.fanmila.util.common;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

public class URLUtils {

	private static Logger logger = LoggerFactory.getLogger(URLUtils.class);

	public static String appendOrUpdateParams(String url, Map<String, String> extraParams) {
		try {
			URI uri = new URI(url);
			Map<String, String> params = getParams(uri);
			params.putAll(extraParams);
			StringBuilder sb = new StringBuilder();
			sb.append(uri.getScheme());
			sb.append("://").append(uri.getHost());
			if (uri.getPort() > 0) {
				sb.append(":").append(uri.getPort());
			}
			sb.append(uri.getRawPath());
			if (!params.isEmpty()) {
				sb.append("?");
				for (Entry<String, String> entry : params.entrySet()) {
					sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			if (StringUtils.isNotBlank(uri.getRawFragment())) {
				sb.append("#").append(uri.getRawFragment());
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("appendParams for {} Exception", url, e);
		}
		return url;
	}
	
	@SuppressWarnings("unchecked")
	public static String appendOrUpdateParams(String url, JSONObject extraParams) {
		return appendOrUpdateParams(url, (Map)extraParams);
	}

	private static Map<String, String> getParams(URI uri) {
		Map<String, String> map = Maps.newLinkedHashMap();
		String query = uri.getRawQuery();
		if (StringUtils.isNotBlank(query)) {
			String[] ss = query.split("=|&");
			for (int i = 0; i < ss.length; i++) {
				String key = ss[i];
				i++;
				String value = i == ss.length ? "" : ss[i];
				map.put(key, value);
			}
		}
		return map;
	}
	
	/**
	 * 获取第二次跳转的主域名部分
	 * 如
	 * @param ourl
	 * @return
	 */
	public static String getRedirectHost(String ourl){
		String host = "";
		try {
			String[] ourlString = ourl.split("\\?")[0].split("/");
			for (int i=0 ; i<ourlString.length-1; i++){
				host += ourlString[i];
				host += "/";
			}
		} catch (Exception e) {
			return "";
		}
		return host;
	}

}
