package com.fanmila.util;

import com.fanmila.util.framework.ContextUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class mailUtil {
	
	/**
	 * 
	 * @param succ 
	 * @param subject
	 * @param msg
	 */
	public static void sendMail(boolean succ, String subject, String msg) {
		JavaMailSenderImpl mailSender = ContextUtils.getApplicationContext()
				.getBean(JavaMailSenderImpl.class);
		SimpleMailMessage mail = ContextUtils.getApplicationContext().getBean(
				SimpleMailMessage.class);
		mail.setCc("zhenyuanzi@fanmila.com");
		mail.setText(msg);
		mail.setSubject(subject + "[" + (succ ? "成功" : "失败") + "]");
		mailSender.send(mail);	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {


	}

}
