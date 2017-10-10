package com.fanmila.util.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author xiao.zhao
 *
 */
public class RequestUtils {
	private static RequestUtils instance = new RequestUtils();
//	private ThreadLocal<OperateLogTask> logTask = new ThreadLocal<OperateLogTask>();
	private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

	private RequestUtils() {

	}
	
	
	public String getHeader() {
		JSONObject header = new JSONObject();
		@SuppressWarnings("unchecked")
		Enumeration<String> em = this.getRequest().getHeaderNames();
		while (em.hasMoreElements()) {
			String key = em.nextElement();
			header.put(key, this.getRequest().getHeader(key));

		}
		return header.toString();
	}

	public void setRequest(HttpServletRequest request, boolean readBody) {
		this.request.set(request);
	
		if (readBody) {
			String body = this.getInputString(request);
			request.setAttribute("body", body);
		}

	}

	public String getPlatform() {
		String platform = this.getParamter("platform");
		return platform;

	}


	/**
	 * get request body
	 * 
	 * @return request body String
	 */
	public String getBody() {
		Object body = getRequest().getAttribute("body");
		if (body == null) {
			return null;
		}
		return body.toString();
	}

	public HttpServletRequest getRequest() {
		return this.request.get();
	}

	public static RequestUtils getInstance() {
		return instance;
	}

	public void addObject(String key, Object value) {
		this.getRequest().setAttribute(key, value);
	}

	public Object getObject(String key) {
		return this.getRequest().getAttribute(key);
	}

	private final String getInputString(HttpServletRequest request) {
		String str = null;
		InputStream in = null;

		try {
			in = request.getInputStream();
			str = IOUtils.toString(in, request.getCharacterEncoding());
			in.close();

		} catch (IOException ioe) {

			str = null;
		} finally {
			IOUtils.closeQuietly(in);
		}
		return str;
	}

	/**
	 * 
	 * @param key
	 * @return value
	 */
	public String getHeader(String key) {

		HttpServletRequest request = getRequest();
		return request.getHeader(key);
	}

	public String getParamter(String name) {
		HttpServletRequest request = getRequest();
		return request.getParameter(name);
	}

	

}
