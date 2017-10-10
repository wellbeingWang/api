package com.fanmila.cms.model.taoke;

import com.fanmila.model.cache.ICatcheItem;

import java.io.Serializable;
import java.util.*;


/**
 * 规则分流条目信息
 * @author lscm
 *
 */
public class TaokeKeyDomain implements Serializable ,ICatcheItem {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String key_uuid;
	private String keyword;
	private String domain;
	private String mdomain;
	private String tdomain;
	private Integer is_enable;

	private Set<TaokeTagRule>  taokeTagRuleList=new HashSet<TaokeTagRule>();
	
	
	public TaokeKeyDomain() {
		super();
	}
	
	public boolean addTaokeTagRule(TaokeTagRule e){
		return taokeTagRuleList.add(e);
	}



	public TaokeKeyDomain(String key_uuid, String keyword, String domain, String mdomain,
			String tdomain, Integer is_enable) {
		super();
		this.key_uuid = key_uuid;
		this.keyword = keyword;
		this.domain = domain;
		this.mdomain = mdomain;
		this.tdomain = tdomain;
		this.is_enable = is_enable;
	}




	public String getKey_uuid() {
		return key_uuid;
	}




	public void setKey_uuid(String key_uuid) {
		this.key_uuid = key_uuid;
	}




	public String getKeyword() {
		return keyword;
	}




	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}




	public String getDomain() {
		return domain;
	}




	public void setDomain(String domain) {
		this.domain = domain;
	}




	public Integer getIs_enable() {
		return is_enable;
	}




	public void setIs_enable(Integer is_enable) {
		this.is_enable = is_enable;
	}

	public boolean isEnable() {
		return 1==this.is_enable?true : false ;
	}

	public List<TaokeTagRule> getTaokeTagRuleList() {
		List<TaokeTagRule> crrL= new ArrayList<TaokeTagRule>();
		crrL.addAll(this.taokeTagRuleList);
		Collections.sort(crrL);
		return crrL;
	}

	public void setTaokeTagRuleList(Set<TaokeTagRule> taokeTagRuleList) {
		this.taokeTagRuleList = taokeTagRuleList;
	}

	@Override
	public String getUUid() {
		// TODO Auto-generated method stub
		return getKey_uuid();
	}

	public String getMdomain() {
		return mdomain;
	}

	public void setMdomain(String mdomain) {
		this.mdomain = mdomain;
	}

	@Override
	public String toString() {
		return "uuid:"+this.getUUid()+", domain:"+this.getDomain()+", keyword:"+this.getKeyword()+", mDomain:"+this.getMdomain();
	}


	public String getTdomain() {
		return tdomain;
	}

	public void setTdomain(String tdomain) {
		this.tdomain = tdomain;
	}
}
