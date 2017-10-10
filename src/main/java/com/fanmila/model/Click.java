package com.fanmila.model;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.mongo.Entity;

/**
 * 广告被点击到跳转过程信息
 * 
 * @author Larry Lang
 * @email larry.lang@b5m.com
 * @date Jan 21, 2013
 * 
 */
public class Click extends Entity {

	/**
	 * 用户ip
	 */
	private String ip;

	// uuid request传递参数
	private String uuid;
	// 展现日志URL
	private String surl;
	// 返回的目的地址,重定向地址
	private String durl;
	// reffer
	private String curl;
	// 请求链接
	private String ourl;
	// 调用接口
	private String method;


	private JSONObject request;

	private JSONObject response;
	
	// 产品ID
	private String sid;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
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
		this.curl = curl;
	}

	public String getOurl() {
		return ourl;
	}

	public void setOurl(String ourl) {
		this.ourl = ourl;
	}

	public JSONObject getRequest() {
		return request;
	}

	public void setRequest(JSONObject request) {
		this.request = request;
	}

	public JSONObject getResponse() {
		return response;
	}

	public void setResponse(JSONObject response) {
		this.response = response;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public ClickHandlerInfo getHandlerInfo() {
		return handlerInfo;
	}

	public void setHandlerInfo(ClickHandlerInfo handlerInfo) {
		this.handlerInfo = handlerInfo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	// 关联日志mongoID
	private String mid;

	private Union union;

	private ClickHandlerInfo handlerInfo;
	
}
