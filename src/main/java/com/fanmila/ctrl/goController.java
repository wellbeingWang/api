package com.fanmila.ctrl;

import com.fanmila.ctrl.core.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class goController extends AbstractBaseController {


	/**
	 * 中间跳转接口
	 *
	 * @param response
	 * @param map
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/go")
	public ModelAndView go(HttpServletResponse response, ModelMap map) throws IOException {
		long start = System.currentTimeMillis();
		//String key = this.getQueryString();
		String dest = this.getString("d");
		String target = this.getString("target");
		String uid = this.getString("uid");
		String cid = this.getString("cid");
		String lt = this.getString("lt");
		String ad = this.getString("ad");
		String ip = this.getString("ip");
		String pi = this.getString("pi");
		String st = this.getString("st");
		String dl = this.getString("dl");
		String cl = this.getString("cl");
		String ssub = this.getString("ssub");
		String fb = this.getString("fb");
		String td = this.getString("td");
		StringBuffer url = this.getRequest().getRequestURL();
		String logUrl = url.delete(url.length() - this.getRequest().getRequestURI().length(), url.length()).append("/").toString();
		logUrl = logUrl.concat("");

		if(!dest.startsWith("http")) dest = "http://" + dest;

		ModelAndView mav = new ModelAndView("/index");
		mav.addObject("destUrl", dest);
		//mav.addObject("target", target);
		/*mav.addObject("uid", uid);
		mav.addObject("cid", cid);
		mav.addObject("ltt", lt);
		mav.addObject("ad", ad);
		mav.addObject("ip", ip);
		mav.addObject("pi", pi);
		mav.addObject("st", st);
		mav.addObject("dl", dl);
		mav.addObject("cl", cl);
		mav.addObject("ssub", ssub);
		mav.addObject("fb", fb);
		mav.addObject("td", td);
		mav.addObject("logUrl", "http://f.fanmila.com/pcl.gif?");*/

		return mav;
	}

}