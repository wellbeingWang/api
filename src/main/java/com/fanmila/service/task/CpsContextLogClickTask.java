package com.fanmila.service.task;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Larry Lang
 * @date Jan 7, 2013
 * 
 */
public class CpsContextLogClickTask extends AbstractBaseTask {


	private Logger logger = LoggerFactory.getLogger("api_cps_context_log");


	private CPSURLHandlerContext click;

	public CpsContextLogClickTask(CPSURLHandlerContext click) {
		this.click = click;
	}

	public CPSURLHandlerContext getClick() {
		return click;
	}

	@Override
	public void execute() {
		String logs = JSONObject.toJSONString(click);
		logger.info(logs);
	}
}
