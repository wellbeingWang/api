package com.fanmila.service;

import com.alibaba.fastjson.JSONObject;

/**
 * APP Service interface
 * 
 * @author xiao.zhao
 * 
 */
public interface AppServiceFace {

	public void setRequestData(String data);

	public String getRequestData();

	public JSONObject getRequestJSON();

	public String getResult();

	public JSONObject getResultJSON();

	public boolean validateData();

	public void setSucc();

	public String service();

	public void setSucc(String msg);

	public void setFail(String msg);

	public void setFail(String msg, int errorCode);

	public void setRequestJSON(JSONObject data);
}
