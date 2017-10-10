package com.fanmila.cms.model.taoke;

import com.fanmila.model.cache.ICatcheItem;

import java.io.Serializable;

/**
 * 规则分流条目信息
 * @author lscm
 *
 */
public class TaokeTagRule implements Serializable ,ICatcheItem,Comparable<TaokeTagRule>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String tag_uuid;
	private String key_uuid;
	private String ar_uuid;
	private String target_name;
	private String cps_url;
	private Integer start_range;
	private Integer end_range;
	private Integer is_enable;

	
	private TaokeAccount taokeAccount;
	private TaokeKeyDomain taokeKeyDomain;
	

	public TaokeTagRule() {
		super();
	}


	public TaokeTagRule(String tag_uuid, String key_uuid, String ar_uuid,
			String target_name, Integer start_range,  Integer end_range,
			String cps_url, Integer is_enable) {
		super();
		this.tag_uuid = tag_uuid;
		this.key_uuid = key_uuid;
		this.ar_uuid = ar_uuid;
		this.target_name = target_name;
		this.setStart_range(start_range);
		this.setEnd_range(end_range);
		this.cps_url = cps_url;
		this.is_enable = is_enable;
	}




	public String getAr_uuid() {
		return ar_uuid;
	}




	public void setAr_uuid(String ar_uuid) {
		this.ar_uuid = ar_uuid;
	}





	public String getTag_uuid() {
		return tag_uuid;
	}




	public void setTag_uuid(String tag_uuid) {
		this.tag_uuid = tag_uuid;
	}




	public String getKey_uuid() {
		return key_uuid;
	}




	public void setKey_uuid(String key_uuid) {
		this.key_uuid = key_uuid;
	}




	public String getTarget_name() {
		return target_name;
	}




	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}




	public String getCps_url() {
		return cps_url;
	}




	public void setCps_url(String cps_url) {
		this.cps_url = cps_url;
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



	public TaokeAccount getTaokeAccount() {
		return taokeAccount;
	}




	public void setTaokeAccount(TaokeAccount taokeAccount) {
		this.taokeAccount = taokeAccount;
	}




	public TaokeKeyDomain getTaokeKeyDomain() {
		return taokeKeyDomain;
	}




	public void setTaokeKeyDomain(TaokeKeyDomain taokeKeyDomain) {
		this.taokeKeyDomain = taokeKeyDomain;
	}

	@Override
	public String getUUid() {
		// TODO Auto-generated method stub
		return getTag_uuid();
	}


	@Override
	public int compareTo(TaokeTagRule o) {
		//升序排列
		if(this.taokeAccount.getStart_range() > o.getTaokeAccount().getStart_range() ){
			return 1;
		}
		if(this.taokeAccount.getStart_range() <= o.getTaokeAccount().getStart_range() ){
			return -1;
		}
		return 0;
	}


	public Integer getEnd_range() {
		return end_range;
	}


	public void setEnd_range(Integer end_range) {
		this.end_range = end_range;
	}


	public Integer getStart_range() {
		return start_range;
	}


	public void setStart_range(Integer start_range) {
		this.start_range = start_range;
	}





	

}
