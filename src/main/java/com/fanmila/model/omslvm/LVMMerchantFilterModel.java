package com.fanmila.model.omslvm;

public class LVMMerchantFilterModel  extends LVMFilterModel{
	
	
	public LVMMerchantFilterModel(int productId, String merchantHost, String businessCode,
			String region, String timePoint, String uuidLastNo, int uuidStart,
			int uuidEnd, String isOpen) {
		super(productId,  businessCode,
				 region,  timePoint,  uuidLastNo,  uuidStart,
				 uuidEnd,  isOpen);
		this.merchantHost = merchantHost;
	}


	public LVMMerchantFilterModel() {
		super();
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantHost;
	
	
	
	public String getMerchantHost() {
		return merchantHost;
	}


	public void setMerchantHost(String merchantHost) {
		this.merchantHost = merchantHost;
	}


}
