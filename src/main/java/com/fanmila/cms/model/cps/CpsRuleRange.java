package com.fanmila.cms.model.cps;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 规则分流条目信息
 * @author lscm
 *
 */
public class CpsRuleRange implements Serializable ,ICatcheItem,Comparable<CpsRuleRange>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rule_range_uuid;
	private String rule_uuid;
	private String url_uuid;
	private String act_uuid;
	private int start_range;
	private int end_range;
	private String is_enable;
	private String extra_params;
	private String stat_url;

	
	private CpsAccount cpsAccount;
	private CpsUrlPattern cpsUrlPattern;
	private CpsPdtDomainRule cpsPdr;
	
	
	
	public CpsRuleRange(String rule_range_uuid, String rule_uuid,
			String url_uuid, String act_uuid, int start_range, int end_range,
			String is_enable, String extra_params, String stat_url) {
		super();
		this.rule_range_uuid = rule_range_uuid;
		this.rule_uuid = rule_uuid;
		this.url_uuid = url_uuid;
		this.act_uuid = act_uuid;
		this.start_range = start_range;
		this.end_range = end_range;
		this.is_enable = is_enable;
		this.extra_params = extra_params;
		this.stat_url = stat_url;
	}
	public CpsRuleRange() {
		super();
	}
	

	public CpsPdtDomainRule getCpsPdr() {
		return cpsPdr;
	}
	public void setCpsPdr(CpsPdtDomainRule cpsPdr) {
		this.cpsPdr = cpsPdr;
	}
	public String getRule_range_uuid() {
		return rule_range_uuid;
	}
	public void setRule_range_uuid(String rule_range_uuid) {
		this.rule_range_uuid = rule_range_uuid;
	}
	public String getRule_uuid() {
		return rule_uuid;
	}
	public void setRule_uuid(String rule_uuid) {
		this.rule_uuid = rule_uuid;
	}

	public String getUrl_uuid() {
		return url_uuid;
	}
	public void setUrl_uuid(String url_uuid) {
		this.url_uuid = url_uuid;
	}
	public String getAct_uuid() {
		return act_uuid;
	}
	public void setAct_uuid(String act_uuid) {
		this.act_uuid = act_uuid;
	}
	
	public int getStart_range() {
		return start_range;
	}
	public void setStart_range(int start_range) {
		this.start_range = start_range;
	}
	public int getEnd_range() {
		return end_range;
	}
	public void setEnd_range(int end_range) {
		this.end_range = end_range;
	}
	public String getIs_enable() {
		return is_enable;
	}
	public void setIs_enable(String is_enable) {
		this.is_enable = is_enable;
	}
	public CpsAccount getCpsAccount() {
		return cpsAccount;
	}
	public void setCpsAccount(CpsAccount cpsAccount) {
		this.cpsAccount = cpsAccount;
	}
	public CpsUrlPattern getCpsUrlPattern() {
		return cpsUrlPattern;
	}
	public void setCpsUrlPattern(CpsUrlPattern cpsUrlPattern) {
		this.cpsUrlPattern = cpsUrlPattern;
	}
	
	
	public String getExtra_params() {
		return extra_params;
	}
	public void setExtra_params(String extra_params) {
		this.extra_params = extra_params;
	}
	
	public JSONObject getExtraParamsMap() {
		if(null == extra_params || "".equals(extra_params.trim())){
			return null;
		}
		try {
			JSONObject json = JSONObject.parseObject(extra_params);
			return json;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	public String getUUid() {
		// TODO Auto-generated method stub
		return getRule_range_uuid();
	}
	@Override
	public int compareTo(CpsRuleRange o) {
		//升序排列
		if(this.start_range > o.start_range ){
			return 1;
		}
		if(this.start_range < o.start_range ){
			return -1;
		}
		return 0;
	}


	public String getStat_url() {
		return stat_url;
	}

	public void setStat_url(String stat_url) {
		this.stat_url = stat_url;
	}
}
