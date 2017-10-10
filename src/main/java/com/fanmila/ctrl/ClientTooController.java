package com.fanmila.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.AppServiceFace;
import com.fanmila.service.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class ClientTooController extends AbstractBaseController {

	
	ServiceFactory serviceFactory = new ServiceFactory();


	private JSONObject initRequestData() {
		JSONObject requestData = new JSONObject();
		requestData.put("browser", this.getBrowser());
		requestData.put("browserVersion", this.getBrowserVersion());
		requestData.put("os", this.getOperatingSystem());
		requestData.put("ip", this.getIpAddr());
		requestData.put("productId", this.getString("pi"));
		return requestData;
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


	@RequestMapping("/popup")
	public ModelAndView popUp(HttpServletRequest request, HttpServletResponse response,
							  String jsonp) {

		long start=System.currentTimeMillis();
		String uuid  =this.getString("u");
		String channel  =this.getString("n");
		String reason  =this.getString("sc");
		String version  =this.getString("v");
		String type  =this.getString("type");


		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("referrer", getReferrer());
		requestData.put("activeDays", this.getInt("an"));
		requestData.put("reason", reason);
		requestData.put("version", version);
		requestData.put("type", type);
		requestData.put("method", "popUp");
		//if("2".equals(type)) requestData.put("method", "bottomRightPopUp");

		logger.logError(requestData.toString());
		//if(!checkParams()) return null;

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createCltService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		return writeLogContextWithSuccWithoutLz(response, jsonp, context, start);
	}

}
