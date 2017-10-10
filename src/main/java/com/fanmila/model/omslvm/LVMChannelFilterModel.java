package com.fanmila.model.omslvm;


public class LVMChannelFilterModel  extends LVMFilterModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public LVMChannelFilterModel(int productId, String channel, String businessCode,
			String region, String timePoint, String uuidLastNo, Integer uuidStart,
			Integer uuidEnd, String isOpen) {
		super(productId,  businessCode,
				 region,  timePoint,  uuidLastNo,  uuidStart,
				 uuidEnd,  isOpen);
		this.setChannel(channel);
	}




	public LVMChannelFilterModel() {
		super();
	}

	
	public String getChannel() {
		return channel;
	}




	public void setChannel(String channel) {
		this.channel = channel;
	}


	/**
	 * 
	 */
	private String channel;
	

}
