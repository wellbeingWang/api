package com.fanmila.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.ctrl.core.JsonpCallbackView;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.AppServiceFace;
import com.fanmila.service.ServiceFactory;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
@RequestMapping("/c")
public class ClientController extends AbstractBaseController {
	
	//private Logger logger = LoggerFactory.getLogger("dmp_adexchange_report");

	@Autowired
	private BaseRedisServiceImpl baseRedisService;
	
	ServiceFactory serviceFactory = new ServiceFactory();

	private static String basePath;

	private static final String STORM_REQUEST_URL = PropertiesUtil.getValue(
			"sf1rconfig", "adserve_storm_requesturl");

	private static final String JS_VERSION = PropertiesUtil.getValue(
			"cpc_config", "b5m_union_js_version");

	private static final String COOKIE_KEY = PropertiesUtil.getValue(
			"cpc_config", "cookie_key");

	private JSONObject initRequestData() {
		JSONObject requestData = new JSONObject();
		requestData.put("browser", this.getBrowser());
		requestData.put("browserVersion", this.getBrowserVersion());
		requestData.put("os", this.getOperatingSystem());
		requestData.put("ip", this.getIpAddr());
		requestData.put("productId", this.getString("pi"));
		return requestData;
	}

	@RequestMapping("/u")
	public ModelAndView getUpdateInfo(HttpServletRequest request, HttpServletResponse response,
									  String jsonp) throws UnsupportedEncodingException {

		long start=System.currentTimeMillis();
		if(StringUtils.isBlank(this.getQueryString()))
			return JsonpCallbackView.Render(null, jsonp, response);

		String version  =this.getString("v");   //用户UUID。
		String channel = this.getString("n");
		String cversion  =this.getString("cv");
		String os = this.getString("o");
		String uuid = this.getString("u");
		Integer activeDay = this.getInt("an");
		String flag = this.getString("flag");

		JSONObject requestData = this.initRequestData();
		requestData.put("version", version);
		requestData.put("cversion", cversion);
		requestData.put("channel", channel);
		requestData.put("os", os);
		requestData.put("uuid", uuid);
		requestData.put("activeDay", activeDay);
		requestData.put("method", "update");

		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createCltService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");
		if(context.getHttpStatus()!=0) response.setStatus(context.getHttpStatus());
		return writeLog (response, jsonp, context, start);

	}

	/**
	 * reason接口
	 * @param request
	 * @param response
	 * @param jsonp
     * @return
     */
	@RequestMapping("/sc")
	public ModelAndView getReasonInfo(HttpServletRequest request, HttpServletResponse response,
									  String jsonp) throws UnsupportedEncodingException {
		long start=System.currentTimeMillis();
		Map test = request.getParameterMap();
		if(test.isEmpty()){
			return JsonpCallbackView.Render(null, jsonp, response);
		}
		//if(StringUtils.isBlank(this.getQueryString()))
		//	return JsonpCallbackView.Render(null, jsonp, response);
		String version  =this.getString("v");   //用户UUID。
		String channel = this.getString("n");
		String os = this.getString("o");
		String uuid = this.getString("u");
		String activeDays = this.getString("an");
		// String enemy = this.getString("a");
		String antiVirus = this.getString("a");
		String browserList = this.getString("b");
		String virtualMachine = this.getString("m");
		String ipCode = this.getString("ct");
		String isBar = this.getString("i");

		JSONObject requestData = this.initRequestData();
		requestData.put("version", version);
		requestData.put("channel", channel);
		requestData.put("os", os);
		requestData.put("uuid", uuid);
		requestData.put("activeDays", StringUtils.isBlank(activeDays)?0:Integer.valueOf(activeDays));
		//requestData.put("enemy", enemy);
		requestData.put("antiVirus", StringUtils.isBlank(antiVirus)?0:Integer.valueOf(antiVirus));
		requestData.put("browserList", StringUtils.isBlank(browserList)?0:Integer.valueOf(browserList));
		requestData.put("virtualMachine", virtualMachine);
		requestData.put("ipCode", ipCode);
		requestData.put("isBar", isBar);
		requestData.put("method", "reason");

		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createCltService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");
		return writeLog (response, jsonp, context, start);

	}

	@RequestMapping("/ej")
	public ModelAndView getEncyptJs(HttpServletRequest request, HttpServletResponse response,
									  String jsonp) {
		long start=System.currentTimeMillis();
		String version  =this.getString("v");   //用户UUID。
		String channel = this.getString("n");
		String uuid = this.getString("u");
		String reason = this.getString("sc");
		String fileMd5 = this.getString("fm");

		JSONObject requestData = this.initRequestData();
		requestData.put("version", version);
		requestData.put("channel", channel);
		requestData.put("uuid", uuid);
		requestData.put("reason", reason);
		requestData.put("fileMd5", fileMd5);
		requestData.put("method", "getEncyptJs");
		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createCltService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");


		try {
			//response.getWriter().write("");
			return JsonpCallbackView.Render(context.getStringInfo(), jsonp, response);
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

	@RequestMapping("/bj")
	public ModelAndView getBhoJs(HttpServletRequest request, HttpServletResponse response,
									String jsonp) {
		long start=System.currentTimeMillis();
		String version  =this.getString("v");   //用户UUID。
		String channel = this.getString("n");
		String uuid = this.getString("u");
		String reason = this.getString("sc");

		JSONObject requestData = this.initRequestData();
		requestData.put("version", version);
		requestData.put("channel", channel);
		requestData.put("uuid", uuid);
		requestData.put("reason", reason);
		requestData.put("method", "getBHOJs");
		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createCltService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");


		try {
			//response.getWriter().write("");
			return JsonpCallbackView.Render(context.getStringInfo(), jsonp, response);
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

	@RequestMapping("/bk")
	public ModelAndView getBK(HttpServletRequest request, HttpServletResponse response,
								 String jsonp) {
		long start=System.currentTimeMillis();
		String version  =this.getString("v");   //用户UUID。
		String channel = this.getString("n");
		String uuid = this.getString("u");
		String reason = this.getString("sc");

		JSONObject requestData = this.initRequestData();
		requestData.put("version", version);
		requestData.put("channel", channel);
		requestData.put("uuid", uuid);
		requestData.put("reason", reason);
		requestData.put("method", "getBKJson");
		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createCltService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		return writeLog (response, jsonp, context.getStringInfo(), context, start);

	}

	/**
	 * 客户端收藏夹功能链接
	 * @param urlId
	 * @param request
	 * @param response
     * @param jsonp
     */
	@RequestMapping("/scj/{url}")
	public void getScj(@PathVariable("url") String urlId, HttpServletRequest request, HttpServletResponse response,
							   String jsonp) {
		long start=System.currentTimeMillis();

		JSONObject requestData = this.initRequestData();
		requestData.put("urlId", urlId);
		requestData.put("method", "getSCJUrl");
		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createCltService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		try {
			//response.getWriter().write("");
			response.sendRedirect(context.getStringInfo());
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			StringBuffer log=new StringBuffer();
			log.append("process Time: ").append((System.currentTimeMillis() -start)).append("ms; ")
					.append("ourl: ").append(context.getOurl());
			logger.logInfoMsg(log.toString());
		}

	}


}
