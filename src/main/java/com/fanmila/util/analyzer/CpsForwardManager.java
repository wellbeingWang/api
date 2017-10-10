package com.fanmila.util.analyzer;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;

public class CpsForwardManager {

	private String domain;
	private String sid;

	private String ssub = "";
	private String url;

	@PostConstruct
	public void init() {
		// FIXME ssub应该是动态生成的
		// url = domain + "go?" + "sid=" + sid + "&ssub=" + ssub;
		url = domain + "cps/_c.do?" + "sid=" + sid + "&ssub=" + ssub;
	}

	public String getCommonForwardUrl(String currUrl, String docid, String uuid, String source) {
		StringBuilder sb = new StringBuilder();
		sb.append(url);

		if (StringUtils.isNotEmpty(currUrl)) {
			sb.append("&referrer=");
			sb.append(currUrl);
		}

		if (StringUtils.isNotEmpty(docid)) {
			sb.append("&docid=");
			sb.append(docid);
		}
		
		if (StringUtils.isNotEmpty(uuid)) {
			sb.append("&uuid=");
			sb.append(uuid);
		}

		if (StringUtils.isNotBlank(source)) {
			sb.append("&source=");
			sb.append(source);
		}

		sb.append("&dest=");
		return sb.toString();
	}

	public String insertAddedInfo(String cpsUrl, String addedInfo) {
		int index = cpsUrl.indexOf("&dest=");
		if (index < 0) {
			return cpsUrl;
		}

		String bcpsUrl = cpsUrl.substring(0, index);
		String lcpsUrl = cpsUrl.substring(index);

		return bcpsUrl + addedInfo + lcpsUrl;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
