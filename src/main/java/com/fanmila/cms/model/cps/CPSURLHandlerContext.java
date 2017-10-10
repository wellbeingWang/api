package com.fanmila.cms.model.cps;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;

public class CPSURLHandlerContext {

	//程序生成objectid
	private String id;
	//商品url request传递参数
	private String turl;
	//
	//重定向url 根据联盟+turl生成
	//
	private String durl;
	//docid request传递参数
	private String docid;
	//商品url域名 根据turl解析出来
	private String tdomain;
	//
	//反馈信息。程序生成
	//
	private String fb;
	//uuid request传递参数
	private String uuid;
	//source request传递参数
	private String source;
	//sid公司产品id request传递参数
	private String sid;
	//跳转类型
	private String ssub;
	//自定义参数
	private String pid;
	//参数请求类型JSON/XML/redirect
	private String datatype;
	
	private JSONObject requestData ;

	private JSONObject responseData;

	private String curl;

	private String statUrl;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "turl:"+this.getTurl()+" durl:"+this.getDurl()+" docid:"+this.getDocid()+
				" tdomain"+this.getTdomain()+" fb:"+this.getFb()+" uuid:"+this.getUuid()+
				" source:"+this.getSource()+" sid:"+this.getSid()+" ssub:"+this.getSsub()+
				" pid:"+this.getPid()+" datatype:"+this.getDatatype()+" requestData:"+this.getRequestData();
	}

	
	public JSONObject getRequestData() {
		return requestData;
	}

	public void setRequestData(JSONObject requestData) {
		this.requestData = requestData;
	}

	public String getSsub() {
		return ssub;
	}

	public void setSsub(String ssub) {
		this.ssub = ssub;
	}

	public String getPid() {
		return pid;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	private ClickHandlerInfo handlerInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTurl() {
		return turl;
	}

	public void setTurl(String turl) {
		this.turl = turl;
	}

	public String getDurl() {
		return durl;
	}

	public void setDurl(String durl) {
		this.durl = durl;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getTdomain() {
		return tdomain;
	}

	public void setTdomain(String tdomain) {
		this.tdomain = tdomain;
	}

	public String getFb() {
		return fb;
	}

	public void setFb(String fb) {
		this.fb = fb;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public ClickHandlerInfo getHandlerInfo() {
		if (handlerInfo == null) {
			handlerInfo = new ClickHandlerInfo();
		}
		return handlerInfo;
	}


	public JSONObject getResponseData() {
		return responseData;
	}

	public void setResponseData(JSONObject responseData) {
		this.responseData = responseData;
	}

	public String getCurl() {
		return curl;
	}

	public void setCurl(String curl) {
		this.curl = curl;
	}

	public String getStatUrl() {
		return statUrl;
	}

	public void setStatUrl(String statUrl) {
		this.statUrl = statUrl;
	}
}
