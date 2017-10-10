package com.fanmila.ctrl.core;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.common.Constants;
import com.fanmila.model.log.B5MLogger;
import com.fanmila.util.framework.MD5Utils;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * 控制器的基类
 * @author Wiley
 *
 */
public abstract class AbstractBaseController{

	protected B5MLogger logger=new B5MLogger(AbstractBaseController.class.getName());
	
	private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	private ThreadLocal<PrintWriter> out = new ThreadLocal<PrintWriter>();
	private ThreadLocal<UserAgent> userAgent = new ThreadLocal<UserAgent>();

	private MultipartHttpServletRequest multipartRequest = null;
	private boolean blMultipart=false;
	
	
	public AbstractBaseController() {
		super();
		logger=new B5MLogger(this.getClass().getName());
	}

	public final void _setServlet(HttpServletRequest request, HttpServletResponse response) {
		this.request.set(this.parseMultipart(request));// 自动解析是否有文件上传
		this.response.set(response);
		String userAgentString=request.getHeader("user-agent");
		if(null != userAgentString){
			UserAgent userAgent=UserAgent.parseUserAgentString(userAgentString);
			this.userAgent.set(userAgent);
		}
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		//String queryString = request.getQueryString();
		//if (StringUtils.isNotBlank(queryString) && queryString.indexOf("action=json") > 0)
		//	response.setContentType(Constants.JSON_CONTENT_TYPE);
		//else
			response.setContentType(Constants.HTML_CONTENT_TYPE);
	}
	
	protected final boolean isMultipart(){
		return blMultipart;
	}

	/**
	 * 分析是否上传文件请求
	 * 
	 * @param request as HttpServletRequest
	 * @return HttpServletRequest
	 */
	protected final HttpServletRequest parseMultipart(HttpServletRequest request) {
		this.blMultipart = ServletFileUpload.isMultipartContent(request);
		if (!this.blMultipart) {
			try {
				request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException uee) {
				logger.logError(uee, "parseMultipart");
			}
			return request;
		}
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding(Constants.DEFAULT_ENCODING);
		try {
			this.multipartRequest = resolver.resolveMultipart(request);
		} catch (MultipartException me) {
			logger.logError(me, "parseMultipart");
		}
		return this.multipartRequest;
	}

	protected final HttpServletRequest getMultipartRequest() {
		return (this.multipartRequest == null) ? this.getRequest() : this.multipartRequest;
	}

	/**
	 * 
	 * @return Iterator&lt;Input.name&gt;
	 */
	public final String[] getUploadNames(String prefix) {
		if (!this.blMultipart)
			return null;
		Iterator<String> ite = multipartRequest.getFileNames();// input.name
		List<String> names = new ArrayList<String>();
		String tmpString = null;
		while (ite.hasNext()) {
			tmpString = ite.next();
			if (tmpString.startsWith(prefix))
				names.add(tmpString);
		}
		return names.toArray(new String[names.size()]);
	}

	/**
	 * //根据getUploadNames()的得到文件对象
	 * 
	 * @param fname as String
	 * @return MultipartFile
	 */
	public final MultipartFile getUploadFile(String fname) {
		if (!this.blMultipart)
			return null;
		return this.multipartRequest.getFile(fname);
	}

	protected final String getString(String pName) {
		return _getParam(request.get(), pName);
	}
	
	@SuppressWarnings("unchecked")
	protected final JSONObject getRequestData() throws UnsupportedEncodingException {
		JSONObject requestData = new JSONObject();
		requestData.put("browser", this.getBrowser());
		requestData.put("browserVersion", this.getBrowserVersion());
		requestData.put("os", this.getOperatingSystem());
		requestData.put("ip", this.getIpAddr());
		
		HttpServletRequest pReq =request.get();
		Map<String, String[]> paramMaps = pReq.getParameterMap();
		Iterator<Entry<String, String[]>> ii = paramMaps.entrySet().iterator();

		while (ii.hasNext()) {
			Entry<String, String[]> e = ii.next();
			String key = e.getKey();
			String[] vaA = e.getValue();
			String valueS = null;
			for (String p : vaA) {
				if (null == valueS) {
					valueS = p;
				} else {
					valueS += ("," + p);
				}
			}
			requestData.put(key, valueS);
		}
		
		return requestData;
	}

	protected final TreeMap<String, String> getParamsMap(){
		TreeMap<String, String> params = new TreeMap<String, String>();

		HttpServletRequest pReq =request.get();
		Map<String, String[]> paramMaps = pReq.getParameterMap();
		Iterator<Entry<String, String[]>> ii = paramMaps.entrySet().iterator();

		while (ii.hasNext()) {
			Entry<String, String[]> e = ii.next();
			String key = e.getKey();
			String[] vaA = e.getValue();
			String valueS = null;
			for (String p : vaA) {
				if (null == valueS) {
					valueS = p;
				} else {
					valueS += ("," + p);
				}
			}
			params.put(key, valueS);
		}

		return params;

	}
	

	protected final String getString(String pName, String defVal) {
		return _getParam(request.get(), pName, defVal);
	}
	
	/**
	 * 
	* @Title: getQueryString 
	* @Description: 用字符串格式獲取所有參數
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	protected final String getQueryString(){
		return request.get().getQueryString();
	}
	
	protected final boolean hasParam(String pName){
		HttpServletRequest pReq = request.get();
		String tmp=pReq.getParameter(pName);
		if (null==tmp){
			return false;
		}
		return true;
	}

	private final String _getParam(HttpServletRequest pReq, String pName, String defVal) {
		String tmp=pReq.getParameter(pName);
		return StringUtils.isBlank(tmp)?defVal:tmp;
	}

	private final String _getParam(HttpServletRequest pReq, String pName) {
		String tmp = "";
		if (StringUtils.isBlank(pName))
			return tmp;
		tmp = pReq.getParameter(pName);
		return StringUtils.isBlank(tmp) ? "" : tmp;
	}

	protected final int getInt(String pName) {
		int val = 0;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Integer.parseInt(tmp);
		} catch (Exception e) {
			val = 0;
		}
		return val;
	}

	protected final long getLong(String pName) {
		long val = 0;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Long.parseLong(tmp);
		} catch (Exception e) {
			val = 0;
		}
		return val;
	}

	protected final Date getDate(String pName, String format) {
		 String tmp=_getParam(request.get(), pName);
		 SimpleDateFormat bartDateFormat =   new SimpleDateFormat(format);
		 try{     
		       java.util.Date date = bartDateFormat.parse(tmp);     
		       java.sql.Date sqlDate = new java.sql.Date(date.getTime());   
		       return sqlDate;     
		}     
		catch (Exception ex) {     
		     return null;     
		}  
	}
	
	protected final Timestamp getTimestamp(String pName ) {
		 String tmp=_getParam(request.get(), pName);
		 try{     
			 SimpleDateFormat bartDateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		       java.util.Date date = bartDateFormat.parse(tmp); 
		       java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());   
		       return sqlDate;     
		}catch (ParseException ex){
			SimpleDateFormat bartDateFormat2 =   new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date date = bartDateFormat2.parse(tmp);
			    java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime()); 
			    return sqlDate;
			} catch (ParseException e) {
				return null;
			}   
		}catch (Exception ex) {  
		     return null;     
		}  
	}

	protected final boolean getBoolean(String pName) {
		String tmp = _getParam(request.get(), pName);
		return (tmp.equalsIgnoreCase("true") || tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("1"));
	}

	protected final double getDouble(String pName) {
		double val = 0.0d;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Double.parseDouble(tmp);
		} catch (Exception e) {
			val = 0.0d;
		}
		return val;
	}

	protected final float getFloat(String pName) {
		float val = 0.0f;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Float.parseFloat(tmp);
		} catch (Exception e) {
			val = 0.0f;
		}
		return val;
	}

	protected final String[] getArrays(String pName) {
		String[] vals = null;
		vals = request.get().getParameterValues(pName);
		if (null==vals)
			vals = new String[] {};
		return vals;
	}

	protected final String getInputString() {
		String str = null;
		InputStream in = null;
		try {
			in = request.get().getInputStream();
			str = IOUtils.toString(in, request.get().getCharacterEncoding());
			in.close();
		} catch (IOException ioe) {
			logger.logError(ioe, "getInputString");
			str = null;
		} finally {
			IOUtils.closeQuietly(in);
		}
		return str;
	}
	
	/**
	 * 获取参数值
	 * @param key
	 * @param type
	 * @return
	 */
/*	private final Object _getClearTypeObject(String key,int type){
		Object obj=null;
		switch(type){
			case Constants.TYPE_STRING: obj=getString(key); break;	
			case Constants.TYPE_INTEGER: obj=getInt(key); break;
			case Constants.TYPE_LONG: obj= getLong(key); break;
			case Constants.TYPE_BOOLEAN: obj= getBoolean(key); break;
			case Constants.TYPE_DOUBLE: obj= getDouble( key); break;
			case Constants.TYPE_FLOAT: obj=getFloat( key); break;
			case Constants.TYPE_DATE: obj=getString(key);obj=getDate(key); break;
			case Constants.TYPE_ARRAY: obj= getArrays(key); break;
			default:
		}
		return obj;
	}*/
	
	/**
	 * 将参数映射成DTO对象
	 * @param fieldType "{fieldName1:String,fieldName2:Integer，...}"
	 * @param t
	 * @return
	 */
/*	protected final <T> T getParamDTO(String fieldType,T t){
		BeanWrapperImpl bwi=new BeanWrapperImpl(t);
		JSONObject jo=JSONObject.fromString(fieldType);
		JSONArray jaNames=jo.names();
		Object[] objNames=jaNames.toArray();
		for (Object objName : objNames) {
			String strName=(String)objName;//字段名
			String strValue=jo.getString(strName).toLowerCase();//字段类型
			int intType=Constants.TYPE_MAPPING.get(strValue);
			//logger.logDebug("paramName:"+strName);
			//logger.logDebug("paramValue:"+_getClearTypeObject(strName,intType));
			bwi.setPropertyValue(strName, _getClearTypeObject(strName,intType));
		}
		return t;
	}*/
	
	
	/*************************/
	/*
	@RequestMapping()
	public abstract void DefaultAction(PrintWriter out);
	//public abstract void DefaultAction(ModelMap map)
		
	@RequestMapping(params = "action=json")
	public abstract void JsonAction(PrintWriter out);
	*/
	protected final PrintWriter getOut(){
		if(this.out.get()==null){
			try{
				//System.out.println(this.response.get().getWriter().toString());
				this.out.set(this.response.get().getWriter());
			}catch(IOException ioe){
				logger.logError(ioe, "getOut");
			}
		}
		return this.out.get();
	}
	protected final HttpServletRequest getRequest(){
		return this.request.get();
	}
	protected final HttpServletResponse getResponse(){
		return this.response.get();
	}
	
	protected final String getIpAddr(){
		HttpServletRequest aHttpServletRequest = this.getRequest();
        String ip=aHttpServletRequest.getHeader("x-forwarded-for");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip=this.getRequest().getHeader("Proxy-Client-IP");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip=this.getRequest().getHeader("WL-Proxy-Client-IP");
		if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
			ip=this.getRequest().getHeader("X-Real-IP");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip=this.getRequest().getRemoteAddr();
        return ip.split(",").length>1?ip.split(",")[0]:ip;
    }
	
	protected final String getOperatingSystem() {
		UserAgent agent = this.userAgent.get();
		if (agent == null) {
			return "";
		}
		OperatingSystem os = agent.getOperatingSystem();
		if (os == null) {
			return "";
		}
		return StringUtils.trimToEmpty(os.getName());
	}

	protected final String getBrowser() {
		UserAgent agent = this.userAgent.get();
		if (agent == null) {
			return "";
		}
		Browser browser = agent.getBrowser();
		if (browser == null) {
			return "";
		}
		return StringUtils.trimToEmpty(browser.getName());
	}

	protected final String getBrowserVersion() {
		UserAgent agent = this.userAgent.get();
		if (agent == null) {
			return "";
		}
		Version version = agent.getBrowserVersion();
		if (version == null) {
			return "";
		}
		return StringUtils.trimToEmpty(version.getVersion());
	}
	
	protected final String getReferer(){
		return request.get().getHeader("referer");
	}

	protected final String getReferrer(){
		return request.get().getHeader("referrer");
	}

	protected final boolean checkParams(){
		TreeMap<String, String> params = this.getParamsMap();
		String md5Code = this.getString("code");
		Long time = this.getLong("time");
		Long now = new java.util.Date().getTime();
		//校验值为空不符合
		if(StringUtils.isBlank(md5Code)) return false;
		//客户端时间和服务的时间相差10分钟以上不符合
        //if(Math.abs(time-now)>600000) return false;

		String otherParams = "";
		Iterator it=params.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry ent=(Map.Entry )it.next();
			String keyt=ent.getKey().toString();
			if(keyt.equals("code")) continue;
			if(keyt.equals("_")) continue;
			if(keyt.equals("jsonp")) continue;
			String valuet=ent.getValue().toString();
			otherParams += keyt;
			otherParams += valuet;
		}
		String realCode = MD5Utils.md5(otherParams);
		if(realCode.equals(md5Code)) return true;
		return false;
	}

	protected ModelAndView writeLog (HttpServletResponse response,
								   String jsonp, URLHandlerContext context, Long start){
		try {
			//response.getWriter().write("");
			return JsonpCallbackView.Render(context.getInfo(), jsonp, response);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally{
			StringBuffer log=new StringBuffer();
			log.append("process Time: ").append((System.currentTimeMillis() -start)).append("ms; ")
					.append("ourl: ").append(context.getOurl());
			logger.logInfoMsg(log.toString());
		}
	}

	protected ModelAndView writeLog (HttpServletResponse response,
									 String jsonp, String info, URLHandlerContext context, Long start){
		try {
			//response.getWriter().write("");
			if(StringUtils.isBlank(info)) return null;
			return JsonpCallbackView.Render(info, jsonp, response);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally{
			StringBuffer log=new StringBuffer();
			log.append("process Time: ").append((System.currentTimeMillis() -start)).append("ms; ")
					.append("ourl: ").append(context.getOurl());
			logger.logInfoMsg(log.toString());
		}
	}

	protected ModelAndView writeLogWithSucc (HttpServletResponse response,
									 String jsonp, URLHandlerContext context, Long start){
		try {
			//response.getWriter().write("");
			if(!context.getInfo().getBoolean("succ")) return null;
			context.getInfo().remove("succ");
			return JsonpCallbackView.Render(context.getInfo(), jsonp, response);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally{
			StringBuffer log=new StringBuffer();
			log.append("process Time: ").append((System.currentTimeMillis() -start)).append("ms; ")
					.append("ourl: ").append(context.getOurl());
			logger.logInfoMsg(log.toString());
		}
	}

	protected ModelAndView writeLogContextWithSuccWithoutLz (HttpServletResponse response,
											 String jsonp, URLHandlerContext context, Long start){
		try {
			//response.getWriter().write("");
			if(!context.getInfo().getBoolean("succ")) return null;
			String info =  context.getInfo().getString("info");
			return JsonpCallbackView.Render(info, jsonp, response);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally{
			StringBuffer log=new StringBuffer();
			log.append("process Time: ").append((System.currentTimeMillis() -start)).append("ms; ")
					.append("ourl: ").append(context.getOurl());
			logger.logInfoMsg(log.toString());
		}
	}
	
}
