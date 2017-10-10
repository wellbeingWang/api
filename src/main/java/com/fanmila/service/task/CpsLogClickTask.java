package com.fanmila.service.task;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.cps.CpsClick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Larry Lang
 * @date Jan 7, 2013
 * 
 */
public class CpsLogClickTask extends AbstractBaseTask {


	private Logger logger = LoggerFactory.getLogger("api_cps_log");


	private CpsClick click;

	public CpsLogClickTask(CpsClick click) {
		this.click = click;
	}

	public CpsClick getClick() {
		return click;
	}

	@Override
	public void execute() {
		String logs = JSONObject.toJSONString(click);
		logger.info(logs);
	}
}
