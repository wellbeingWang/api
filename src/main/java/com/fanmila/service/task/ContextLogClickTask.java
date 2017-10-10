package com.fanmila.service.task;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.URLHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Larry Lang
 * @date Jan 7, 2013
 * 
 */
public class ContextLogClickTask extends AbstractBaseTask {


	private Logger logger = LoggerFactory.getLogger("api_context_log");


	private URLHandlerContext click;

	public ContextLogClickTask(URLHandlerContext click) {
		this.click = click;
	}

	public URLHandlerContext getClick() {
		return click;
	}

	@Override
	public void execute() {
		String logs = JSONObject.toJSONString(click);
		logger.info(logs);
	}
}
