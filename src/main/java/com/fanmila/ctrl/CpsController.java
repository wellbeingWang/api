package com.fanmila.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.ctrl.core.AbstractBaseController;
import com.fanmila.ctrl.core.JsonpCallbackView;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.service.AppServiceFace;
import com.fanmila.service.ServiceFactory;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.framework.ContextUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/")
public class CpsController extends AbstractBaseController {
	private static Logger log4j = LoggerFactory.getLogger("NewCpsController");

	private @Value("#{configProperties['stat.url']}")
	String statUrl;

	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

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
		requestData.put("version", this.getString("vp"));
		requestData.put("title", this.getString("title"));
		requestData.put("url", this.getString("url"));
		requestData.put("sid", StringUtils.isBlank(this.getString("pi"))?"10":this.getString("pi"));
		requestData.put("site", this.getString("site"));
		requestData.put("pid", this.getString("pid"));
		return requestData;
	}

	@RequestMapping("/gr")
	public ModelAndView ruleAction(HttpServletResponse response, ModelMap map,
								   String jsonp) {
		long start=System.currentTimeMillis();
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods","POST,GET");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		JSONObject requestData = this.initRequestData();
		requestData.put("ip", this.getIpAddr());
		requestData.put("method", "getRule");
		requestData.put("referrer", getReferrer());

		requestData.put("activeDays", this.getString("an"));
		requestData.put("channel", this.getString("n"));

		AppServiceFace face = serviceFactory.createCpsRedirectService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		CPSURLHandlerContext context = (CPSURLHandlerContext) result.get("result");

		try {
			return JsonpCallbackView.Render(context.getResponseData(), jsonp, response);

		} catch (Exception e) {
			logger.logError(e);
			return null;
		}finally{
				StringBuffer log=new StringBuffer();
				log.append("process Time：").append((System.currentTimeMillis() -start))
						.append("ms;p:").append(context.getSid()).append("-").append(context.getSsub()).append("-").append(context.getUuid()).append("-").append(context.getSource())
						.append(";tu:").append(context.getTurl())
						.append(";du:").append(context.getDurl());
			logger.logInfoMsg(log.toString());
		}
	}


	@RequestMapping("/dt")
	public void redirectAction(HttpServletResponse response,ModelMap map) {
	
		long start=System.currentTimeMillis();		
		JSONObject requestData = this.initRequestData();
		requestData.put("ip", this.getIpAddr());
		requestData.put("docid", this.getString("docid"));
		requestData.put("dest", this.getString("dl"));
		requestData.put("odest", this.getString("odest"));
		requestData.put("ssub", this.getString("ssub"));
		requestData.put("rurl", this.getString("rurl"));
		requestData.put("datatype", this.getString("datatype"));
		requestData.put("method", "redirect");
		if(StringUtils.isBlank(this.getString("dl"))){
			try {
				logger.logError("ERROR:dest is not null ");
				response.getWriter().write("ERROR:dest is not null ");
			} catch (IOException e) {
				logger.logError(e);
			}
			return;
		}

		AppServiceFace face = serviceFactory.createCpsRedirectService();
		face.setRequestJSON(requestData);
		face.service();

		JSONObject result = face.getResultJSON();
		CPSURLHandlerContext context = (CPSURLHandlerContext) result.get("result");
		
		
		String dataType =this.getString("datatype");
		
		//System.out.println(new Gson().toJson(context));

		String url ="";
		try {
			if("JSON".equalsIgnoreCase(dataType)){
				String turl=context.getTurl();
				String durl= context.getDurl();
				String dJson ="{turl:\""+turl+"\",durl:\""+durl+"\"}";
				//System.out.println(dJson);
				response.getWriter().write(dJson);
			}else{
				url = cpsRedirect( context);
				response.sendRedirect(url);
			}
		} catch (Exception e) {
			logger.logError(e);
		}finally{
			if(!isTaobao(context)){
				StringBuffer log=new StringBuffer();
				log.append("process Time：").append((System.currentTimeMillis() -start))
				.append("ms;p:").append(context.getSid()).append("-").append(context.getSsub()).append("-").append(context.getUuid()).append("-").append(context.getSource())
				.append(";tu:").append(context.getTurl())
				.append(";du:").append(context.getDurl());
				/*if( context.getHandlerInfo().getClickType().equals(ClickType.NONE)){
					log4j.warn(log.toString());
//					log4j.error("process Time：{}ms ,sid:{},ssub:{},toUlr:{},resultUrl:{}",(System.currentTimeMillis() -start), context.getSid(),context.getHandlerInfo().isSl()?"LV2":"LV1",context.getTurl(),url);
				}else{
					if(CpsServerConfig.isSaveLog4j){
						log4j.info(log.toString());
					}
//					log4j.info("process Time：{}ms ,sid:{},ssub:{},toUlr:{},resultUrl:{}",(System.currentTimeMillis() -start), context.getSid(),context.getHandlerInfo().isSl()?"LV2":"LV1",context.getTurl(),url);
				}*/

				logger.logInfoMsg(log.toString());
			}
		}
		
//		return null;
	}

	private boolean isTaobao(CPSURLHandlerContext context){
		if(context.getDurl().startsWith("http://item.taobao.com")){
			return true;
		}
		if(context.getDurl().startsWith("http://detail.tmall.com")){
			return true;
		}
		if(context.getDurl().startsWith("http://www.tmall.com")){
			return true;
		}
		if(context.getDurl().startsWith("http://ju.taobao.com")){
			return true;
		}
		if(context.getDurl().startsWith("http://detail.ju.taobao.com")){//http://ju.mmstat.com
			return true;
		}
		if(context.getDurl().startsWith("http://www.taobao.com")){
			return true;
		}
		if(context.getDurl().indexOf("youku.com")>-1){
			return true;
		}
		return false;
	}
	
	private boolean isTaobaoKe(CPSURLHandlerContext context){
		if(context.getDurl().startsWith("http://ai.taobao.com")){
			return true;
		}
		if(context.getDurl().startsWith("http://s.click.taobao.com")){
			return true;
		}
		if(context.getDurl().startsWith("https://ai.taobao.com")){
			return true;
		}
		if(context.getDurl().startsWith("https://s.click.taobao.com")){
			return true;
		}
		return false;
	}


	protected String cpsRedirect(CPSURLHandlerContext context) throws Exception{

		String resultUrl=null ;
		String ssub = this.getString("ssub");
		String sid=context.getSid();
		try {
			if (context != null) {
				ClickHandlerInfo handlerInfo = context.getHandlerInfo();
				String td = context.getTdomain();
				String fb = context.getFb();
				ClickType clickType = handlerInfo.getClickType();

			/*	try {
					// feedback数据写入cookie，path为/cps/_a_域名.do（其中域名中的.替换成_）
					if (StringUtils.isNotBlank(fb) && StringUtils.isNotBlank(d)) {
						Cookie cookie = new Cookie(IUnionHandler.B5M_FB_KEY, fb);
						cookie.setMaxAge(60 * 60 * 24);
						cookie.setPath("/cps/_a_" + d.replaceAll("\\.", "_") + ".do");
						getResponse().addCookie(cookie);
					}
				} catch (Exception e) {
					logger.logError(e);
				}*/


				//直接重定向到cps服务 不走state接口
				if( "1".equals(this.getString("st"))){
					return context.getDurl();
				}


				String uid=URLEncoder.encode(context.getUuid()==null?"":context.getUuid(), "UTF-8");
				String cid=URLEncoder.encode("", "UTF-8");
				String it=URLEncoder.encode("8888", "UTF-8");
				String ad="";//cps日记记录标记
				String statUrl2 = context.getStatUrl();
				String stateDomain = StringUtils.isBlank(statUrl2)?statUrl:statUrl2;

				ad=URLEncoder.encode(ad, "UTF-8");
				String ip=URLEncoder.encode(ContextUtils.getInstance().getIpAddr(), "UTF-8");
				String st=URLEncoder.encode(String.valueOf(System.currentTimeMillis() + RandomUtils.nextInt(1000)), "UTF-8");
				String dl=URLEncoder.encode(context.getTurl(), "UTF-8");
				String cl=URLEncoder.encode(context.getDurl(), "UTF-8");

/////测试专用
//System.out.println(context.getDurl());

				StringBuilder url = new StringBuilder("http://"+stateDomain+"/go?");
				url.append("d=").append(URLEncoder.encode(context.getDurl(),"utf-8"));
				/*url.append("&target=").append(cl);
				url.append("&uid=").append(uid);
				url.append("&cid=").append(cid);
				url.append("&lt=").append(it);
				url.append("&ad=").append(ad);
				url.append("&ip=").append(ip);
				url.append("&pi=").append(sid);
				url.append("&st=").append(st);
				url.append("&dl=").append(dl);
				url.append("&cl=").append(cl);
				url.append("&ssub=").append(ssub);
				url.append("&fb=").append(fb);
				url.append("&td=").append(td);*/
				// level2无法使用包含了alexa统计代码的跳转方式
				//if ("saleb5t".equalsIgnoreCase(ssub)||"salerys".equalsIgnoreCase(ssub)) {
				/*if ("1".equalsIgnoreCase(ssub)||"LV2".equalsIgnoreCase(ssub)||"saleb5t".equalsIgnoreCase(ssub)||"salerys".equalsIgnoreCase(ssub)) {
					url.append("t=1");
				}else if ("LV1".equalsIgnoreCase(ssub)) {//提示跳转
					url.append("t=2");
				}else if ("LVIF".equalsIgnoreCase(ssub)) {//iframe类
					url.append("t=3");
				}else {//默认不提示跳转
					url.append("t=1");
				}*/
				/*url.append("&target=").append(cl);
				url.append("&uid=").append(uid);
				url.append("&cid=").append(cid);
				url.append("&lt=").append(it);
				url.append("&ad=").append(ad);
				url.append("&ip=").append(ip);
				url.append("&st=").append(st);
				if ("tmall.com".equalsIgnoreCase(d)||"taobao.com".equalsIgnoreCase(d)) {
					url.append("&dl=");
					url.append("&cl=");
				}else {
					url.append("&dl=").append(dl);
					url.append("&cl=").append(cl);
				}*/
				resultUrl = url.toString();
			}

		} catch (Exception e) {
			throw e;
		}
		return resultUrl;
	}

}
