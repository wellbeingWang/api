package com.fanmila.cms.model.cps;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * csp 帐号信息
 * @author lscm
 *
 */
public class CpsAccount implements Serializable ,ICatcheItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String act_uuid;
	private String url_uuid;
	//帐号采用SJON格式根据帐号模版填写
	private String act_info;
	private String act_desc;
	private String is_enable;
	private String act_type;
	private CpsUrlPattern cspUrlPattern;
	
	public CpsAccount() {
		
	}
	
	public CpsAccount(String act_uuid, String url_uuid, String act_info,
			String act_desc, String is_enable,String act_type) {
		super();
		this.act_uuid = act_uuid;
		this.url_uuid = url_uuid;
		this.act_info = act_info;
		this.act_desc = act_desc;
		this.is_enable = is_enable;
		this.act_type = act_type;
	}


	public Map<String, String> findAccountMap() {
		if(null == act_info || "".equals(act_info.trim())){
			return null;
		}
		try {
			JSONObject json = JSONObject.fromString(act_info);
			@SuppressWarnings("unchecked")
			Map<String, String> o = (Map<String, String>) JSONObject.toBean(json, HashMap.class);
			return o;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public String getAct_type() {
		return act_type;
	}

	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}

	public CpsUrlPattern getCspUrlPattern() {
		return cspUrlPattern;
	}
	public void setCspUrlPattern(CpsUrlPattern cspUrlPattern) {
		this.cspUrlPattern = cspUrlPattern;
	}
	public String getAct_uuid() {
		return act_uuid;
	}
	public void setAct_uuid(String act_uuid) {
		this.act_uuid = act_uuid;
	}
	public String getUrl_uuid() {
		return url_uuid;
	}
	public void setUrl_uuid(String url_uuid) {
		this.url_uuid = url_uuid;
	}
	public String getAct_info() {
		return act_info;
	}
	public void setAct_info(String act_info) {
		this.act_info = act_info;
	}
	public String getAct_desc() {
		return act_desc;
	}
	public void setAct_desc(String act_desc) {
		this.act_desc = act_desc;
	}
	public String getIs_enable() {
		return is_enable;
	}
	public boolean IsEnable() {
		return "Y".equalsIgnoreCase(this.is_enable)  ? true:false;
	}
	public void setIs_enable(String is_enable) {
		this.is_enable = is_enable;
	}
	@Override
	public String getUUid() {
		// TODO Auto-generated method stub
		return getAct_uuid();
	}

	@Override
	public String toString() {
		return "CpsAccount [act_uuid=" + act_uuid + ", url_uuid=" + url_uuid
				+ ", act_info=" + act_info + ", act_desc=" + act_desc
				+ ", act_type=" + act_type + "]";
	}
	
	

}
