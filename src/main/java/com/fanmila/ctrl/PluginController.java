package com.fanmila.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.ctrl.core.JsonpCallbackView;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.AppServiceFace;
import com.fanmila.service.ServiceFactory;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.LZStringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/p")
public class PluginController extends AbstractBaseController {

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

	@RequestMapping("/goods")
	public ModelAndView getGoods(HttpServletRequest request, HttpServletResponse response,
									  String jsonp) {

		long start=System.currentTimeMillis();
		String cid  =this.getString("cid");   //用户UUID。
		JSONObject requestData = this.initRequestData();
		requestData.put("cid", cid);
		requestData.put("method", "getGoods");

		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createPluginService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");
		return writeLog(response, jsonp, context, start);

	}

	@RequestMapping("/favorites")
	public ModelAndView getTaobaoFavoritesGoods(HttpServletRequest request, HttpServletResponse response,
									  String jsonp) {

		long start=System.currentTimeMillis();
		Long favoritesId = this.getLong("groupId");//7412494L;
		Long pageSize = this.getLong("pageSize");//200l;
		Long pageNo = this.getLong("pageNo");//1l;
		Long adzoneId = this.getLong("zoneId");//115034663L;
		String time  =this.getString("time");
		JSONObject requestData = this.initRequestData();
		requestData.put("favoritesId", favoritesId.compareTo(0l)==0 ? 7412494L : favoritesId);
		requestData.put("pageSize", pageSize.compareTo(0l)==0 ? 200l : pageSize);
		requestData.put("pageNo", pageNo.compareTo(0l)==0 ? 1l : pageNo);
		requestData.put("adzoneId", adzoneId.compareTo(0l)==0 ? 115034663L : adzoneId);
		requestData.put("time", time);
		requestData.put("method", "getTaobaoFavoritesGoods");

		logger.logError(requestData.toString());

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createPluginService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");
		return writeLog(response, jsonp, context, start);

	}

	@RequestMapping("/mr")
	public ModelAndView matchRule(HttpServletRequest request, HttpServletResponse response,
									  String jsonp) {

		long start=System.currentTimeMillis();
		String uuid  =this.getString("u");
		String channel  =this.getString("n");
		String reason  =this.getString("sc");
		String br  =this.getString("br");
		String version  =this.getString("vp");
		String time  =this.getString("time");


		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("reason", reason);
		requestData.put("time", time);
		requestData.put("br", br);
		requestData.put("version", version);
		requestData.put("method", "matchRule");

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
			if(StringUtils.isBlank(version)) return JsonpCallbackView.Render(context.getInfo().getString("info"), jsonp, response);
			else info =  LZStringUtil.compressToEncodedURIComponent(context.getInfo().getString("info"));
			if(StringUtils.isBlank(jsonp)) return JsonpCallbackView.Render(info, jsonp, response);
			else return JsonpCallbackView.Render("'"+info+"'", jsonp, response);
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



	@RequestMapping("/nmr")
	public ModelAndView newMatchRule(HttpServletRequest request, HttpServletResponse response,
								  String jsonp) {

		long start=System.currentTimeMillis();
		String uuid  =this.getString("u");
		String channel  =this.getString("n");
		String reason  =this.getString("sc");
		String version  =this.getString("vp");
		String time  =this.getString("time");


		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("referrer", getReferrer());
		requestData.put("activeDays", this.getInt("an"));
		requestData.put("reason", reason);
		requestData.put("time", time);
		requestData.put("version", version);
		requestData.put("method", "newMatchRule");

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
			String info =  LZStringUtil.compressToEncodedURIComponent(context.getInfo().getString("info"));
			if(StringUtils.isBlank(jsonp)) return JsonpCallbackView.Render(info, jsonp, response);
			else return JsonpCallbackView.Render("'"+info+"'", jsonp, response);
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


	@RequestMapping("/pop")
	public ModelAndView popUp(HttpServletRequest request, HttpServletResponse response,
									 String jsonp) {

		long start=System.currentTimeMillis();
		String uuid  =this.getString("u");
		String channel  =this.getString("n");
		String reason  =this.getString("sc");
		String version  =this.getString("vp");
		Long infoVersion  =this.getLong("ve");
		String time  =this.getString("time");


		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("referrer", getReferrer());
		requestData.put("activeDays", this.getInt("an"));
		requestData.put("reason", reason);
		requestData.put("time", time);
		requestData.put("version", version);
		requestData.put("infoVersion", infoVersion);
		requestData.put("method", "popUp");

		logger.logError(requestData.toString());
		if(!checkParams()) return null;

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createPluginService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");

		return writeLogContextWithSuccWithoutLz(response, jsonp, context, start);
	}


	@RequestMapping("/yhhd")
	public ModelAndView youhui(HttpServletRequest request, HttpServletResponse response,
							  String jsonp) {

		long start=System.currentTimeMillis();
		String uuid  =this.getString("u");
		String channel  =this.getString("n");
		String reason  =this.getString("sc");
		String tt  =this.getString("tt");
		String version  =this.getString("vp");
		String an  =this.getString("an");
		String time  =this.getString("time");


		JSONObject requestData = this.initRequestData();
		requestData.put("uuid", uuid);
		requestData.put("channel", channel);
		requestData.put("referrer", getReferrer());
		requestData.put("activeDays", an);
		requestData.put("reason", reason);
		requestData.put("time", time);
		requestData.put("version", version);
		requestData.put("method", "youhuihuodong");

		logger.logError(requestData.toString());
		if(!checkParams()) return null;

		//获取跳转链接的主程序
		AppServiceFace face = serviceFactory.createPluginService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		URLHandlerContext context = (URLHandlerContext) result.get("result");
		return writeLogContextWithSuccWithoutLz(response, jsonp, context, start);

	}

}
