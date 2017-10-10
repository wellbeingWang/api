package com.fanmila.cms.model.taoke;

import com.fanmila.model.cache.ICatcheItem;

import java.io.Serializable;

/**
 * 规则分流条目信息
 * @author lscm
 *
 */
public class TaokeAccount implements Serializable ,ICatcheItem {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String ar_uuid;
	private String website;
	private String common_url;
	private String short_tag;
	private Integer start_range;
	private Integer end_range;
	private Integer is_enable;

	
	

	public TaokeAccount() {
		super();
	}




	public TaokeAccount(String ar_uuid, String website,String common_url,String short_tag, Integer start_range,
			Integer end_range, Integer is_enable) {
		super();
		this.ar_uuid = ar_uuid;
		this.website = website;
		this.setCommon_url(common_url);
		this.setShort_tag(short_tag);
		this.start_range = start_range;
		this.end_range = end_range;
		this.is_enable = is_enable;
	}




	public String getAr_uuid() {
		return ar_uuid;
	}




	public void setAr_uuid(String ar_uuid) {
		this.ar_uuid = ar_uuid;
	}




	public String getWebsite() {
		return website;
	}




	public void setWebsite(String website) {
		this.website = website;
	}




	public Integer getStart_range() {
		return start_range;
	}




	public void setStart_range(Integer start_range) {
		this.start_range = start_range;
	}




	public Integer getEnd_range() {
		return end_range;
	}




	public void setEnd_range(Integer end_range) {
		this.end_range = end_range;
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



	@Override
	public String getUUid() {
		// TODO Auto-generated method stub
		return getAr_uuid();
	}




	public String getCommon_url() {
		return common_url;
	}




	public void setCommon_url(String common_url) {
		this.common_url = common_url;
	}




	public String getShort_tag() {
		return short_tag;
	}




	public void setShort_tag(String short_tag) {
		this.short_tag = short_tag;
	}





	

}
