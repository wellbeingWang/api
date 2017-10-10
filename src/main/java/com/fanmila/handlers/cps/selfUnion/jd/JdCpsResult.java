package com.fanmila.handlers.cps.selfUnion.jd;

import com.fanmila.handlers.cps.selfUnion.SiteSize;
import com.google.gson.Gson;

import java.util.List;

/**
 * 京东cps推广返回数据
 * @author lscm
 *
 */
public class JdCpsResult {
	private long id;//橱窗ID
	private String js;//Js推广代码
	private String url;//静态推广代码
	private String siteSize;//返回该推广类型下的所有推广位尺寸（目前提供的推广位尺寸见附录二）

	private String resultCode;//已选择的推广位尺寸
	private String resultMessage;//返回代码（传入参数格式校验）（见附录三）

	private List<SiteSize> siteSizeList;//返回代码（传入参数格式校验）（见附录三）
	
	private List<BatchJdCpsUrl> urlList;
	
	

	public static JdCpsResult createFromJson(String json) throws Exception {
		JdCpsResult o =null;
		
		try {
			o = new Gson().fromJson(json, JdCpsResult.class );
		} catch (Exception e) {
			throw new Exception(e);
		}
		return o;
	}

	public JdCpsResult() {
		super();
	}

	public List<BatchJdCpsUrl> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<BatchJdCpsUrl> urlList) {
		this.urlList = urlList;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSiteSize() {
		return siteSize;
	}

	public void setSiteSize(String siteSize) {
		this.siteSize = siteSize;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List<SiteSize> getSiteSizeList() {
		return siteSizeList;
	}

	public void setSiteSizeList(List<SiteSize> siteSizeList) {
		this.siteSizeList = siteSizeList;
	}

	
	
	@Override
	public String toString() {
		return "JdCpsResult [id=" + id + ", js=" + js + ", url=" + url
				+ ", siteSize=" + siteSize + ", resultCode=" + resultCode
				+ ", resultMessage=" + resultMessage + ", siteSizeList="
				+ siteSizeList + "]";
	}
	
	
	
	
	

}
