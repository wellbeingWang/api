package com.fanmila.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.ctrl.core.AbstractBaseController;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/")
public class ShouCangjiaController extends AbstractBaseController {
	
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
		requestData.put("viewtime", new Date());
		return requestData;
	}
	/**
	 * 客户端收藏夹功能链接
	 * @param urlId
	 * @param request
	 * @param response
     * @param jsonp
     */
	@RequestMapping("/{url}")
	public void getScj(@PathVariable("url") String urlId, HttpServletRequest request, HttpServletResponse response,
							   String jsonp) {
		long start=System.currentTimeMillis();

		if(urlId.length()!=2) return;

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
			String url = context.getStringInfo();
			if(StringUtils.isBlank(url) || !url.startsWith("http")) return;
			response.sendRedirect(url);
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
