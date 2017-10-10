package com.fanmila.cms.model.cps;

import com.fanmila.model.ClickType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * CPS外部接口url地址信息
 * @author lscm
 *
 */
public class CpsUrlPattern implements Serializable,ICatcheItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url_uuid;
	
	private String union_id;//联盟id
	private String  union_name;//联盟名字
	private String union_type;//联盟类型 自营 三方
	
	private String  click_type;//CPC, CPS, EXT, NONE
	//url 接口描述带详细说明
	private String  url_pattern;
	//url静态参数
	private String url_static_addr;
	private String url_param_fb;//url feedback参数名称
	private String url_param_to;//url to目标地址参赛名称
	private String url_accout_pattern;//账户信息模版填充 {"ss":"用户id标识","w":"账户名称"}
	
	private String url_param_1;
	private String url_param_2;
	private String url_param_3;
	
	
	
	private String is_url_to_encode;
	private String is_enable;
	
	
	public CpsUrlPattern(){
		
	}
	
	public CpsUrlPattern(String url_uuid, String union_id, String union_name,
			String union_type, String click_type, String url_pattern,
			String url_static_addr, String url_param_fb, String url_param_to,
			String url_accout_pattern, String url_param_1, String url_param_2,
			String url_param_3, String is_enable,String is_url_to_encode) {
		this.url_uuid = url_uuid;
		this.union_id = union_id;
		this.union_name = union_name;
		this.union_type = union_type;
		this.click_type = click_type;
		this.url_pattern = url_pattern;
		this.url_static_addr = url_static_addr;
		this.url_param_fb = url_param_fb;
		this.url_param_to = url_param_to;
		this.url_accout_pattern = url_accout_pattern;
		this.url_param_1 = url_param_1;
		this.url_param_2 = url_param_2;
		this.url_param_3 = url_param_3;
		this.is_enable = is_enable;
		this.is_url_to_encode =is_url_to_encode;
	}
	private Set<CpsAccount> actList=new HashSet<CpsAccount>();
	
	
	public Map<String, String> findAccountPattenMap() {
		if(null == url_accout_pattern || "".equals(url_accout_pattern.trim())){
			return null;
		}
		try {
			Map<String, String> o = new LinkedHashMap<String, String>();
			String url_accout_pattern_temp =url_accout_pattern.trim();
			// {"uinonId":"联盟ID" ,"fb":"Feedback" ,"backurl":"目的URL"  }
			url_accout_pattern_temp = url_accout_pattern_temp.substring(1, url_accout_pattern_temp.length()-1);
			String [] itemA = url_accout_pattern_temp.split(",");
			for(String item : itemA){
				String [] jsA = item.replaceAll("\"", "").replaceAll("'", "").split(":");
				o.put(jsA[0], jsA[1]);
			}
			
			return o;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean addAct(CpsAccount c){
		return actList.add(c);
	}
	public Set<CpsAccount> getActList() {
		return actList;
	}
	public void setActList(Set<CpsAccount> actList) {
		this.actList = actList;
	}
	public String getUnion_id() {
		return union_id;
	}
	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}
	public String getUrl_uuid() {
		return url_uuid;
	}
	public void setUrl_uuid(String url_uuid) {
		this.url_uuid = url_uuid;
	}
	public String getUnion_name() {
		return union_name;
	}
	public void setUnion_name(String union_name) {
		this.union_name = union_name;
	}
	public String getUnion_type() {
		return union_type;
	}
	public void setUnion_type(String union_type) {
		this.union_type = union_type;
	}
	public String getUrl_pattern() {
		return url_pattern;
	}
	public void setUrl_pattern(String url_pattern) {
		this.url_pattern = url_pattern;
	}
	
	public void setClick_type(String click_type) {
		this.click_type = click_type;
	}
	
	public String getUrl_accout_pattern() {
		return url_accout_pattern;
	}
	public void setUrl_accout_pattern(String url_accout_pattern) {
		this.url_accout_pattern = url_accout_pattern;
	}
	
	public String getUrl_static_addr() {
		return url_static_addr;
	}
	public void setUrl_static_addr(String url_static_addr) {
		this.url_static_addr = url_static_addr;
	}
	public String getUrl_param_fb() {
		return url_param_fb;
	}
	public void setUrl_param_fb(String url_param_fb) {
		this.url_param_fb = url_param_fb;
	}
	public String getUrl_param_to() {
		return url_param_to;
	}
	public void setUrl_param_to(String url_param_to) {
		this.url_param_to = url_param_to;
	}
	public String getUrl_param_1() {
		return url_param_1;
	}
	public void setUrl_param_1(String url_param_1) {
		this.url_param_1 = url_param_1;
	}
	public String getUrl_param_2() {
		return url_param_2;
	}
	public void setUrl_param_2(String url_param_2) {
		this.url_param_2 = url_param_2;
	}
	public String getUrl_param_3() {
		return url_param_3;
	}
	public void setUrl_param_3(String url_param_3) {
		this.url_param_3 = url_param_3;
	}
	public String getClick_type() {
		return click_type;
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
		return getUrl_uuid();
	}
	public ClickType getClickType(){
		if("CPC".equals(getClick_type())){
			return ClickType.CPC;
		}else if("CPS".equals(getClick_type())){
			return ClickType.CPS;
		}else if("EXT".equals(getClick_type())){
			return ClickType.EXT;
		}else{
			return ClickType.NONE;
		}
	}

	public String getIs_url_to_encode() {
		return is_url_to_encode;
	}

	public void setIs_url_to_encode(String is_url_to_encode) {
		this.is_url_to_encode = is_url_to_encode;
	}
	public boolean IsUrlToEncode() {
		return "Y".equalsIgnoreCase(this.is_url_to_encode)  ? true:false;
	}

	@Override
	public String toString() {
		return "CpsUrlPattern [url_uuid=" + url_uuid + ", union_id=" + union_id
				+ ", union_name=" + union_name + ", union_type=" + union_type
				+ ", click_type=" + click_type + ", url_pattern=" + url_pattern
				+ ", url_static_addr=" + url_static_addr + ", url_param_fb="
				+ url_param_fb + ", url_param_to=" + url_param_to
				+ ", url_accout_pattern=" + url_accout_pattern + "]";
	}
	
	
	
}
