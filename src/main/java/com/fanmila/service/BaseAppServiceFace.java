package com.fanmila.service;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.common.Constants;
import com.fanmila.model.common.ErrorMessage;
import com.fanmila.util.framework.ContextUtils;

public abstract class BaseAppServiceFace implements AppServiceFace {
/*	protected MobileLog logger = new MobileLog(
			BaseAppServiceFace.class.getName());*/
	protected JSONObject requestData = null;
	protected JSONObject resultData = new JSONObject();
	protected String data = null;

	private String succMsg = "操作成功";

	public void setSucc() {
		this.setSucc(succMsg);
	}
	
	public void setRequestJSON(JSONObject data) {
		this.requestData = data;
	}
	
	protected boolean hasKey(String key, String msg) {
		if (!this.getRequestJSON().containsKey(key)) {
			this.setFail(msg);
			return false;
		}

		return true;
	}

	protected <T> T getBean(Class<T> clazz) {

		return ContextUtils.getBean(clazz);
	}

	protected void put(String key, String value) {
		this.getResultJSON().put(key, value);
	}

	protected void put(String key, int value) {
		this.getResultJSON().put(key, value);
	}

	protected String getStringParam(String key) {
		if (this.getRequestJSON().containsKey(key)) {
			return this.getRequestJSON().getString(key);
		}
		return "";
	}

	protected int getIntParam(String key) {
		return this.getRequestJSON().getInteger(key);
	}

	public void setSucc(String msg) {
		resultData.put(Constants.APP_REQUEST_SUCCESSFUL,
				Constants.APP_REQUEST_SUCCESS);
		resultData.put(Constants.APP_REQUEST_MSG, msg);

	}

	public void setFail(String msg) {
		resultData = new JSONObject();
		resultData.put("errorcode", ErrorMessage.SYSTEM_DEFALUT_ERROR);
		resultData.put(Constants.APP_REQUEST_SUCCESSFUL,
				Constants.APP_REQUEST_FAIL);
		resultData.put(Constants.APP_REQUEST_MSG, msg);
	}

	public void setFail(String msg, int errorCode) {
		resultData = new JSONObject();
		resultData.put("errorcode", errorCode);
		resultData.put(Constants.APP_REQUEST_SUCCESSFUL,
				Constants.APP_REQUEST_FAIL);
		resultData.put(Constants.APP_REQUEST_MSG, msg);
	}

	@Override
	public void setRequestData(String data) {
		this.data = data;
	}

	@Override
	public String getRequestData() {

		return this.data;
	}

	@Override
	public JSONObject getRequestJSON() {
		if (this.requestData == null)
			if (StringUtils.isNotBlank(data)) {
				try {
					this.requestData = JSONObject.parseObject(data);
				} catch (Exception ex) {
					ex.printStackTrace();
					this.requestData = new JSONObject();
				}

			} else {
				this.requestData = new JSONObject();
			}

		return this.requestData;
	}

	@Override
	public String getResult() {

		return resultData.toString();
	}

	@Override
	public JSONObject getResultJSON() {

		return resultData;
	}

	public void clear() {
		this.data = null;
		this.requestData = null;
		this.resultData = new JSONObject();
	}

}
