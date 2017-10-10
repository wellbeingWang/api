/*
 * [文 件 名]:B5MInitServlet.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-9
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
 */

package com.fanmila.service.listener;

/*import java.util.List;*/

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fanmila.util.framework.ContextUtils;


/**
 * [简要描述]:初始化 [详细描述]:初始化
 * 
 * @author [Wiley]
 * @version [版本号,2012-10-9]
 * @see [B5MInitServlet]
 * @since [comb5mpluginserver]
 */
public class FanmilaInitServlet extends HttpServlet {
	private static final long serialVersionUID = 5486824670505826252L;

	private String getMessage(Throwable t) {
		StackTraceElement[] msgs = t.getStackTrace();
		StringBuffer message = new StringBuffer();
		message.append(t.getMessage() + "\n");
		for (StackTraceElement msg : msgs) {
			message.append(msg.toString() + "\n");
		}
		return message.toString();
	}

	private void sendMail(boolean succ, String msg) {
		JavaMailSenderImpl mailSender = ContextUtils.getApplicationContext().getBean(JavaMailSenderImpl.class);
		SimpleMailMessage mail = ContextUtils.getApplicationContext().getBean(SimpleMailMessage.class);
		mail.setCc("zhenyuanzi@fanmila.com");
		mail.setText(msg);
		mail.setSubject("应用服务器启动" + "[" + (succ ? "成功" : "失败") + "]");
		mailSender.send(mail);	
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);

//			TraceLogManager.instace.registerEvent();
			WebApplicationContext webCtx = WebApplicationContextUtils
					.getWebApplicationContext(config.getServletContext(),
							"org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
			ContextUtils.getInstance()._setContextPath(
					config.getServletContext().getContextPath());
			ContextUtils.getInstance()._setApplicationContext(webCtx);
			ContextUtils.getInstance()._setIntercepter(
					(HandlerInterceptorAdapter) webCtx
							.getBean("baseInterceptor"));
			FanmilaInitInfo.setContextPath(ContextUtils.getInstance()
					.getContextPath());
			this.log(webCtx.getServletContext().getContextPath());
			initCache();
			this.sendMail(true, "success");


		} catch (Throwable t) {
			this.sendMail(false, getMessage(t));
			t.printStackTrace();
			System.exit(0);
		}


	}

	private void initCache(){

	}
}
