package com.fanmila.model.omslvm;

import java.io.Serializable;

public class LVMFilterModel  implements Serializable{
	
	
	public LVMFilterModel(int productId, String businessCode,
			String region, String timePoint, String uuidLastNo, Integer uuidStart,
			Integer uuidEnd, String isOpen) {
		super();
		this.productId = productId;
		this.businessCode = businessCode;
		this.region = region;
		this.timePoint = timePoint;
		this.uuidLastNo = uuidLastNo;
		this.uuidStart = uuidStart;
		this.uuidEnd = uuidEnd;
		this.isOpen = isOpen;
	}

	

	public LVMFilterModel() {
		super();
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int productId;
	private String businessCode;
	private String region;
	private String timePoint;
	private String uuidLastNo;
	private Integer uuidStart;
	private Integer uuidEnd;
	private String isOpen;
	
	
	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public String getBusinessCode() {
		return businessCode;
	}



	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}



	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getuuidLastNo() {
		return uuidLastNo;
	}


	public void setuuidLastNo(String uuidLastNo) {
		this.uuidLastNo = uuidLastNo;
	}


	public Integer getUuidStart() {
		return uuidStart;
	}


	public void setUuidStart(Integer uuidStart) {
		this.uuidStart = uuidStart;
	}


	public Integer getUuidEnd() {
		return uuidEnd;
	}


	public void setUuidEnd(Integer uuidEnd) {
		this.uuidEnd = uuidEnd;
	}


	public String getIsOpen() {
		return isOpen;
	}


	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}


	/** 
	 * @Title: main 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param args    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public String getTimePoint() {
		return timePoint;
	}


	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}

}
