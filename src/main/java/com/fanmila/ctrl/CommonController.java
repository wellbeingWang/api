package com.fanmila.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.ctrl.core.JsonpCallbackView;
import com.fanmila.service.ServiceFactory;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.http.HttpRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class CommonController extends AbstractBaseController {
	private static Logger log4j = LoggerFactory.getLogger("NewCpsController");

	private @Value("#{configProperties['stat.url']}")
	String statUrl;

	ServiceFactory serviceFactory = new ServiceFactory();

	private JSONObject initRequestData() {
		JSONObject requestData = new JSONObject();
		requestData.put("browser", this.getBrowser());
		requestData.put("browserVersion", this.getBrowserVersion());
		requestData.put("os", this.getOperatingSystem());
		requestData.put("ip", this.getIpAddr());
		requestData.put("reffer", this.getReferer());
		requestData.put("source", this.getString("n"));
		requestData.put("uuid", this.getString("u"));
		requestData.put("method", this.getString("method"));
		requestData.put("version", this.getString("v"));
		requestData.put("title", this.getString("title"));
		requestData.put("url", this.getString("url"));
		requestData.put("sid", StringUtils.isBlank(this.getString("pi"))?"10":this.getString("pi"));
		requestData.put("site", this.getString("site"));
		requestData.put("pid", this.getString("pid"));
		return requestData;
	}

	@RequestMapping("/getIpInfo")
	public ModelAndView ruleAction(HttpServletResponse response, ModelMap map,
								   String jsonp) {
		long start=System.currentTimeMillis();


		String ip = StringUtils.isBlank(this.getString("ip"))?this.getIpAddr():this.getString("ip");
		String url = "http://ip.taobao.com/service/getIpInfo.php";
		String ipInfo= HttpRequest.sendGet(url, "ip=".concat(ip));

		try {
			return JsonpCallbackView.Render(ipInfo, jsonp, response);

		} catch (Exception e) {
			logger.logError(e);
			return null;
		}finally{
				StringBuffer log=new StringBuffer();
				log.append("process Time：").append((System.currentTimeMillis() -start));
			logger.logInfoMsg(log.toString());
		}
	}

	@RequestMapping("/getIP")
	public ModelAndView getIp(HttpServletRequest request, HttpServletResponse response,  String jsonp) {
		long start=System.currentTimeMillis();
		try {
			String ip = StringUtils.isBlank(this.getString("ip"))?this.getIpAddr():this.getString("ip");
			JSONObject city = IPToolUtil.getCityofIp(ip);
			return JsonpCallbackView.Render(city, jsonp, response);

		} catch (Exception e) {
			logger.logError(e);
			return null;
		}finally{
			StringBuffer log=new StringBuffer();
			log.append("process Time：").append((System.currentTimeMillis() -start));
			logger.logInfoMsg(log.toString());
		}

	}


}
