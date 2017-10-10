package com.fanmila.service.decorator;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.service.AppServiceFace;

public abstract class AbstractAppServiceDecorator implements AppServiceDecorator {
	
/*	protected MobileLog logger = new MobileLog(
			this.getClass().getName());*/
	protected AppServiceFace service = null;

	public AbstractAppServiceDecorator(AppServiceFace service) {
		super();
		this.service = service;
	}

	protected String getServiceName() {
		return this.service.getClass().getName();
	}

	@Override
	public void setFail(String msg, int errorCode) {
		this.service.setFail(msg, errorCode);
		
	}

	protected String getMessage(Exception ex) {
		StackTraceElement[] msgs = ex.getStackTrace();
		StringBuffer message = new StringBuffer();
		for (StackTraceElement msg : msgs) {
			message.append(msg.toString());
		}
		return message.toString();
	}

	@Override
	public void setRequestData(String data) {
		this.service.setRequestData(data);

	}

	@Override
	public String getRequestData() {
		
		return this.service.getRequestData();
	}

	@Override
	public JSONObject getRequestJSON() {
		
		return this.service.getRequestJSON();
	}

	@Override
	public String getResult() {
	
		return this.service.getResult();
	}

	@Override
	public JSONObject getResultJSON() {
		
		return this.service.getResultJSON();
	}

	@Override
	public void setSucc(String msg) {
		this.service.setSucc(msg);

	}
	public void setSucc(){
		this.service.setSucc("操作成功");
	}
	@Override
	public void setFail(String msg) {
		this.service.setFail(msg);

	}
	

	@Override
	public boolean validateData() {
		
		return service.validateData();
	}


	@Override
	public void setRequestJSON(JSONObject data) {
		service.setRequestJSON(data);
		
	}

}