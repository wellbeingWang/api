package com.fanmila.ctrl;


import com.alibaba.fastjson.JSONObject;
import com.fanmila.cache.IPTool;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.service.AppServiceFace;
import com.fanmila.service.ServiceFactory;
import com.fanmila.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class TaobaoVasController extends AbstractBaseController {
	private static Logger log4j = LoggerFactory.getLogger("TaobaoVasController");
	
	ServiceFactory serviceFactory = new ServiceFactory();

	private JSONObject initRequestData() {
		JSONObject requestData = new JSONObject();
		requestData.put("browser", this.getBrowser());
		requestData.put("browserVersion", this.getBrowserVersion());
		requestData.put("os", this.getOperatingSystem());
		requestData.put("ip", this.getIpAddr());
		requestData.put("source", this.getString("source"));
		//requestData.put("uuid", this.getString("uuid"));
		requestData.put("method", this.getString("method"));
		requestData.put("version", this.getString("version"));
		requestData.put("title", this.getString("title"));
		requestData.put("url", this.getString("url"));
		requestData.put("site", this.getString("site"));
		requestData.put("pid", this.getString("pid"));//自定义参数
		return requestData;
	}

	@RequestMapping("/taovas")
	public void taovas(HttpServletResponse response,ModelMap map) throws IOException {
	
		//long start=System.currentTimeMillis();		
		JSONObject requestData = this.initRequestData();
		
		String channel  =this.getString("channel");    //推广渠道。
		String tag  =this.getString("tag");    //链接URL
		String uuid  =this.getString("uuid");   //用户UUID。
		String sid  =this.getString("pi");
		String tt = this.getString("tt");
		String tu  =this.getString("tu");
		String ip  =this.getString("ip");
		
		requestData.put("channel", channel);
		requestData.put("tag", tag);
		requestData.put("uuid", uuid);
		requestData.put("sid", sid);
		requestData.put("tt", tt);
		requestData.put("tu", tu);
		requestData.put("ips", ip);
		
		requestData.put("hasin", this.hasParam("in"));
		requestData.put("hastag", this.hasParam("tag"));
		//requestData.put("hassn", this.hasParam("sn"));
		
		
		logger.logError(requestData.toString());
		

		
		AppServiceFace face = serviceFactory.createTaoVasRedirectService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		CPSURLHandlerContext context = (CPSURLHandlerContext) result.get("result");
		
		
		
		try {
			String durl = context.getDurl();
			response.getWriter().write(durl);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		
		
	}

	
/*
	@RequestMapping("/taoke/flash.do")
	public synchronized String flash(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			TaokeDBTool.flash();
			response.getWriter().write("Taoke Pool reFlash OK!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/

	@RequestMapping("/updateIP.do")
	public void flashIp(HttpServletRequest request,HttpServletResponse response) {
	    try {
			IPTool.downloadIpFile();
			response.getWriter().write("IP File download OK!");
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
	@RequestMapping("/getIP.do")
	public void getIp(HttpServletRequest request,HttpServletResponse response) {
	    try {
	    	com.alibaba.fastjson.JSONObject city = IPToolUtil.getCityofIp(this.getString("ips"));
			response.getWriter().write(city.toString());
			response.getWriter().close();
			logger.logInfoMsg(city.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/queryIp.do")
	public void getIp2(HttpServletRequest request,HttpServletResponse response) {
	    try {
	    	com.alibaba.fastjson.JSONObject city = IpipnetUtil.getCityofIp(this.getString("ips"));
			response.getWriter().write(city.toString());
			response.getWriter().close();
			logger.logInfoMsg(city.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@RequestMapping("/getfedback.do")
	public void getaid(HttpServletRequest request,HttpServletResponse response) {
	    try {
	    	String method = this.getString("method");
	    	String fedback = "";
	    	if ("de".equals(method)){
	    		fedback = Encrypt.desString(this.getString("str"));
	    	}else if("en".equals(method)){
	    		fedback = Encrypt.encString(this.getString("str"));
	    	}
			response.getWriter().write(fedback);
			response.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getweb.do")
	public void sss(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String string = "<!DOCTYPE html>\n"+
				"\n"+
				"<html style=\"width:100%;height:100%;\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"+
				"\n"+
				"    <head>\n"+
				"        <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"></meta>\n"+
				"    </head>\n"+
				"    <body style=\"margin: 0px;\">\n"+
				"        <a target=\"_blank\" href=\"" +this.getString("href")+
				"\">\n"+
				"            <img height=\"272\" width=\"664\" src=\"" + this.getString("img")+
				"\"></img>\n"+
				"        </a>\n"+
				"    </body>\n"+
				"\n"+
				"</html>\n";
		response.getWriter().write(string);
		response.getWriter().close();
	}
}
