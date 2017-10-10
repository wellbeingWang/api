package com.fanmila.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.ctrl.core.JsonpCallbackView;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.AppServiceFace;
import com.fanmila.service.ServiceFactory;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.LZStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/p")
public class PluginDHSSController extends AbstractBaseController {

	//private Logger logger = LoggerFactory.getLogger("dmp_adexchange_report");

	@Autowired
	private BaseRedisServiceImpl baseRedisService;

	ServiceFactory serviceFactory = new ServiceFactory();

	private JSONObject initRequestData() {
		JSONObject requestData = new JSONObject();
		requestData.put("browser", this.getBrowser());
		requestData.put("browserVersion", this.getBrowserVersion());
		requestData.put("os", this.getOperatingSystem());
		requestData.put("productId", this.getInt("pi"));
		requestData.put("ip", this.getIpAddr());
		return requestData;
	}

	@RequestMapping("/dhmr")
	public ModelAndView dhMatchRule(HttpServletRequest request, HttpServletResponse response,
									  String jsonp) {

		long start=System.currentTimeMillis();
		String uuid  =this.getString("u");
		String channel  =this.getString("n");
		String reason  =this.getString("sc");
		String activeDay  =this.getString("an");
		String version  =this.getString("vp");
		String time  =this.getString("time");


		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("reason", reason);
		requestData.put("activeDay", activeDay);
		requestData.put("version", version);
		requestData.put("time", time);
		requestData.put("method", "DhssMatchRule");

		logger.logError(requestData.toString());
		if(!checkParams()) return null;

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createPluginService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");


		try {
			//response.getWriter().write("");
			if(!context.getInfo().getBoolean("succ")) return null;
			String info = "";
			if(version.equals("1.0.0.13")) info = context.getInfo().getJSONObject("info").toJSONString();
			else info =  LZStringUtil.compressToEncodedURIComponent(context.getInfo().getJSONObject("info").toJSONString());
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
/*

	@RequestMapping("/dhdt")
	public ModelAndView downUrl(HttpServletRequest request, HttpServletResponse response,
								  String jsonp) {

		long start=System.currentTimeMillis();
		String uuid  =this.getString("u");
		String channel  =this.getString("n");
		String reason  =this.getString("sc");
		String activeDay  =this.getString("an");
		String version  =this.getString("vp");
		String ip  =this.getString("ip");
		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("reason", reason);
		requestData.put("activeDay", activeDay);
		requestData.put("version", version);
		requestData.put("ips", ip);
		requestData.put("method", "DhDownUrl");

		logger.logError(requestData.toString());
		//if(!checkParams()) return null;

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createPluginService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		return writeLog(response, jsonp, context.getStringInfo(),context, start);

	}
*/


	@RequestMapping("/ssdt")
	public ModelAndView ssdownUrl(HttpServletRequest request, HttpServletResponse response,
								String jsonp) {

		long start = System.currentTimeMillis();
		String uuid = this.getString("u");
		String channel = this.getString("n");
		String os = this.getString("os");
		String activeDay = this.getString("an");
		String version = this.getString("vp");
		String tt = this.getString("q");
		String domain = this.getString("se");
		String ip = this.getString("ip");
		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("os", os);
		requestData.put("activeDay", activeDay);
		requestData.put("version", version);
		requestData.put("text", tt);
		requestData.put("domain", domain);
		requestData.put("ips", ip);
		requestData.put("method", "SsDownUrl");

		logger.logError(requestData.toString());
		//if(!checkParams()) return null;

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createPluginService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		return writeLogWithSucc(response, jsonp, context, start);
	}

}
