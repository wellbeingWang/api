/*
 * [文 件 名]:BaseServiceFace.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-12
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
 */

package com.fanmila.service;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.log.B5MLogger;
import com.fanmila.model.common.Constants;
import com.fanmila.util.Native2AsciiUtil;

/**
 * [简要描述]: [详细描述]:
 * 
 * @author [Wiley]
 * @version [版本号,2012-10-12]
 * @see [BaseServiceFace]
 */
public abstract class BaseServiceFace implements AppServiceFace {
	public static final String uuid = "uuid";
	public static final String METHOD = "method";
	public static final String JSONCALLBACK = "jsoncallback";
	public static final String VERSION = "version";
	public static final String SOURCE = "source";

	protected B5MLogger logger = new B5MLogger(BaseServiceFace.class.getSimpleName());
	protected JSONObject requestData = new JSONObject();
	protected JSONObject resultData = new JSONObject();

	protected String getVersion() {
		return getParam(VERSION);
	}

	protected String getMethod() {
		return getParam(METHOD);
	}

	protected String getSource() {
		return getParam(SOURCE);
	}

	protected String getJSONCALLBACK() {
		return getParam(JSONCALLBACK);
	}

	protected String getUuid() {
		return getParam(uuid);

	}

	protected String getParam(String key) {
		if (this.requestData.containsKey(key)) {
			return this.requestData.getString(key);
		}
		return "";
	}
	
	protected String getParamValue( String key ){
		JSONObject jsonObj = this.getRequestJSON();
		return getParamValue(jsonObj, key, "");
	}

	protected String getParamValue( JSONObject jsonObj, String key, String defaultValue){
		return jsonObj.containsKey(key) ? jsonObj.getString(key) : defaultValue;
	}

	@Override
	public void setRequestJSON(JSONObject data) {
		this.requestData = data;
	}

	@Override
	public JSONObject getRequestJSON() {
		return this.requestData;
	}

	@Override
	public String getResult() {
		return this.resultData.toString();
	}

	@Override
	public JSONObject getResultJSON() {
		return this.resultData;
	}


	public void setSucc() {

		this.setSucc(Constants.RSP_MSG_SUCCESS);
	}

	@Override
	public void setSucc(String msg) {
		resultData.put("succ", Constants.RSP_SUCCESS);
		resultData.put("msg", Native2AsciiUtil.encode(msg));
	}

	@Override
	public void setFail(String msg) {
		this.setFail(msg, Constants.RSP_FAILE);
	}

	@Override
	public void setFail(String msg, int errorCode) {
		resultData.put("succ", errorCode);
		resultData.put("msg", Native2AsciiUtil.encode(msg));
	}
	
	@Override
	public void setRequestData(String data) {
		this.requestData=JSONObject.parseObject(data);
		
	}

	@Override
	public String getRequestData() {
		if(this.requestData==null)
			return null;
		return this.requestData.toString();
	}

	@Override
	public boolean validateData() {
	
		return true;
	}

	
    /**
     * 
     *  [简要描述]:组织重定向的URL
     *  [详细描述]:组织重定向的URL
     *  @author [Wiley]
     *  @date   [2012-10-15]
     *  @method [getForwordUrl]
     *  @return
     *  @retruntype [String]
     *  @exception
     */
/*    public String getForwordUrl()
    {
        String serverName =  RequestUtils.getInstance().getRequest().getServerName();
        int serverPort =  RequestUtils.getInstance().getRequest().getServerPort();
        //String contextPath = FanmilaInitInfo.getContextPath();
//        String serverName = "172.16.7.4";
//        int serverPort = 80;
//        String contextPath = "";
          
        String version = this.getRequestJSON().getString("version");
        String source = this.getRequestJSON().getString("source");
        String uuid = this.getRequestJSON().getString("uuid");
        String forwordUrl = "http://" + serverName;
        if (serverPort != 80)
        {
            forwordUrl += ":" + serverPort;
        }
        forwordUrl += contextPath + "/plugin.do?method=go&version=" + version + "&source=" + source + "&uuid=" + uuid;
        return forwordUrl;
    }*/

}
