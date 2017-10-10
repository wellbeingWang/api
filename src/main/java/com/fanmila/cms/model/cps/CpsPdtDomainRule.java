package com.fanmila.cms.model.cps;

import java.io.Serializable;
import java.util.*;

/**
 * cps 规则信息 提供规则的定制
 * @author lscm
 *
 */
public class CpsPdtDomainRule implements Serializable ,ICatcheItem{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rule_uuid ;
	private String product;
	private String domain;
	private String rule_info;
	private String is_enable;
	private String extra_params;
	private Set<CpsRuleRange>  ruleRangeList=new HashSet<CpsRuleRange>();
	
	
	public CpsPdtDomainRule() {
		super();
	}

	public CpsPdtDomainRule(String rule_uuid, String product, String domain,
			String rule_info, String is_enable,String extra_params) {
		super();
		this.rule_uuid = rule_uuid;
		this.product = product;
		this.domain = domain;
		this.rule_info = rule_info;
		this.is_enable = is_enable;
		this.extra_params=extra_params;
	}

	public boolean addRuleRange(CpsRuleRange e){
		return ruleRangeList.add(e);
	}

	public String getExtra_params() {
		return extra_params;
	}

	public void setExtra_params(String extra_params) {
		this.extra_params = extra_params;
	}

	public String getRule_uuid() {
		return rule_uuid;
	}

	public void setRule_uuid(String rule_uuid) {
		this.rule_uuid = rule_uuid;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRule_info() {
		return rule_info;
	}

	public void setRule_info(String rule_info) {
		this.rule_info = rule_info;
	}

	public String getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(String is_enable) {
		this.is_enable = is_enable;
	}
	public boolean isEnable() {
		return "Y".equalsIgnoreCase(this.is_enable)?true : false ;
	}

	public List<CpsRuleRange> getRuleRangeList() {
		List<CpsRuleRange> crrL= new ArrayList<CpsRuleRange>();
		crrL.addAll(this.ruleRangeList);
		Collections.sort(crrL);
		return crrL;
	}

	public void setRuleRangeList(Set<CpsRuleRange> ruleRangeList) {
		this.ruleRangeList = ruleRangeList;
	}

	@Override
	public String getUUid() {
		// TODO Auto-generated method stub
		return getRule_uuid();
	}
	
	

}
