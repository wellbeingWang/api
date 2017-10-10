package com.fanmila.model;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class URLHandlerContext {

	//程序生成objectid
	private String id;
	//uuid request传递参数
	private String uuid;
	//渠道  request传递参数
	private String channel;
	//第一步日志mongo存储的主键
	private String mid;
    //返回的目的地址,重定向地址
    private String durl;
    //reffer
    private String curl;
	//请求链接
    private String ourl;
    
    private JSONObject info;

    private String stringInfo;

	//跳转类型
	private String ssub;
	//产品
	private String sid;

	private int httpStatus;

	private Long currentTime = new Date().getTime();


	private JSONObject requestData ;


	private ClickHandlerInfo handlerInfo;


	public String getId() {
		return id;
	}


	public String getStringInfo() {
		return stringInfo;
	}


	public void setStringInfo(String stringInfo) {
		this.stringInfo = stringInfo;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getChannel() {
		return channel;
	}


	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getMid() {
		return mid;
	}


	public void setMid(String mid) {
		this.mid = mid;
	}


	public String getDurl() {
		return durl;
	}


	public void setDurl(String durl) {
		this.durl = durl;
	}


	public String getCurl() {
		return curl;
	}


	public void setCurl(String curl) {
		try {
			this.curl = URLEncoder.encode(curl, "utf8");
		} catch (UnsupportedEncodingException e) {
			this.curl = curl;
			e.printStackTrace();
		}
	}


	public String getOurl() {
		return ourl;
	}


	public void setOurl(String ourl) {
		this.ourl = ourl;
	}


	public JSONObject getInfo() {
		return info;
	}


	public void setInfo(JSONObject info) {
		this.info = info;
	}


	public String getSsub() {
		return ssub;
	}


	public void setSsub(String ssub) {
		this.ssub = ssub;
	}


	public String getSid() {
		return sid;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public JSONObject getRequestData() {
		return requestData;
	}


	public void setRequestData(JSONObject requestData) {
		this.requestData = requestData;
	}


	public void setHandlerInfo(ClickHandlerInfo handlerInfo) {
		this.handlerInfo = handlerInfo;
	}

	public ClickHandlerInfo getHandlerInfo() {
		if (handlerInfo == null) {
			handlerInfo = new ClickHandlerInfo();
		}
		return handlerInfo;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Long currentTime) {
		this.currentTime = currentTime;
	}
}
