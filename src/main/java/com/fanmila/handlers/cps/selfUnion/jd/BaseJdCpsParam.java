package com.fanmila.handlers.cps.selfUnion.jd;

import com.google.gson.Gson;

/**
 * 京东联盟OpenAIP 请求参数
 * @author lscm
 *
 */
public abstract class BaseJdCpsParam {
	private String pin;//联盟用户名称
	private long unionId; //联盟ID
	private String subUnionId="";//子联盟ID
	private String channel="PC";//推广渠道 PC：pc推广，WL：无线推广
	private String webId="";//网站ID
	private String  ext1="";//扩展字段
	
	
	
	
	public BaseJdCpsParam(long unionId, String subUnionId, String channel,
			String webId, String ext1) {
		super();
		this.unionId = unionId;
		this.subUnionId = subUnionId;
		this.channel = channel;
		this.webId = webId;
		this.ext1 = ext1;
	}
	public BaseJdCpsParam(long unionId) {
		super();
		this.unionId = unionId;
		this.channel = "PC";
	}
	public BaseJdCpsParam(long unionId, String channel) {
		super();
		this.unionId = unionId;
		this.channel = channel;
	}
	public BaseJdCpsParam() {
		super();
	}
	public long getUnionId() {
		return unionId;
	}
	public void setUnionId(long unionId) {
		this.unionId = unionId;
	}
	public String getSubUnionId() {
		return subUnionId;
	}
	public void setSubUnionId(String subUnionId) {
		this.subUnionId = subUnionId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	
	public String toJsonString(){
		return new Gson().toJson(this);
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
}
