/*
package com.fanmila.ctrl;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import com.alibaba.fastjson.JSONObject;
import com.fanmila.cache.LVMRedisSynch;
import com.fanmila.service.AppServiceFace;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.service.ServiceFactory;
import com.fanmila.util.LZStringUtil;

@Controller
@RequestMapping("/")
public class OmsLVMController extends AbstractBaseController {

	ServiceFactory serviceFactory = new ServiceFactory();

	private JSONObject initRequestData() {
		JSONObject requestData = new JSONObject();
		requestData.put("browser", this.getBrowser());
		requestData.put("browserVersion", this.getBrowserVersion());
		requestData.put("os", this.getOperatingSystem());
		requestData.put("ip", this.getIpAddr());
		requestData.put("source", this.getString("source"));
		requestData.put("method", this.getString("method"));
		requestData.put("version", this.getString("version"));
		return requestData;
	}

	@RequestMapping("/s.do")
	public void getLvm(HttpServletResponse response, ModelMap map)
			throws IOException {

		long start=System.currentTimeMillis();
		JSONObject requestData = this.initRequestData();

		String channel = this.getString("chn"); // 推广渠道。
		String uuid = this.getString("uid"); // 用户UUID。
		String product = this.getString("prod");// 商品价格
		String fromWhere = this.getString("from");// 当前站点
		String jsonp = this.getString("jsonp");// jsonp参数，支持jsonp调用

		requestData.put("channel", channel);
		requestData.put("uuid", uuid);
		requestData.put("fromWhere", fromWhere);
		requestData.put("jsonp", jsonp);
		requestData.put("product", product);

		logger.logError(requestData.toString());

		AppServiceFace face = serviceFactory.createOmsLVMService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		try {
			com.alibaba.fastjson.JSONObject responseJson = context.getLvmInfo();
			String responseString = "";
			if ("".equals(context.getRequestData().getString("jsonp"))
					|| context.getRequestData().getString("jsonp") == null) {
				responseString = responseJson.toString();
			} else {// 支持jsonp输出
				responseString = context.getRequestData().getString("jsonp")
						+ "(" + responseJson.toString() + ")";
			}
			response.getWriter().write(responseString);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			response.getWriter().close();
			StringBuffer log=new StringBuffer();
			log.append("process Time: ").append((System.currentTimeMillis() -start))
			   .append("; ourl: ").append(context.getOurl());
			logger.logInfoMsg(log.toString());
		}
	}

	@RequestMapping("/config.do")
	public void getDhConfig(HttpServletResponse response, ModelMap map)
			throws IOException {
		*/
/*long start=System.currentTimeMillis();
		String querySting = getQueryString();
		JSONObject queryJsonObject = JSONObject.parseObject(LZStringUtil
						.decompressFromEncodedURIComponent(querySting));
		

		logger.logError(queryJsonObject.toString());

		AppServiceFace face = serviceFactory.createLv1DHConfigService();
		face.setRequestJSON(queryJsonObject);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		try {
			com.alibaba.fastjson.JSONObject responseJson = context.getLvmInfo();
			response.getWriter().write(responseJson.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			response.getWriter().close();
			StringBuffer log=new StringBuffer();
			log.append("process Time: ").append((System.currentTimeMillis() -start)).append("ms; ") 
			   .append("ourl: ").append(context.getOurl());
			logger.logInfoMsg(log.toString());
		}*//*

	}

	*/
/**
	 * 服务器参数更新只单线程同步
	 * 
	 * @param request
	 * @param response
	 * @return
	 *//*

	@RequestMapping("flash.do")
	public synchronized String flash(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			LVMRedisSynch.flash();
			response.getWriter().write(" reFlash OK!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	


}
*/
