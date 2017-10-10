package com.fanmila.service.task;

import com.fanmila.model.Click;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Larry Lang
 * @date Jan 7, 2013
 * 
 */
public class APICLTClickTask extends AbstractBaseTask {


	private Logger logger = LoggerFactory.getLogger("api_clt_log");


	private Click click;

	public APICLTClickTask(Click click) {
		this.click = click;
	}

	public Click getClick() {
		return click;
	}

	@Override
	public void execute() {
		String logs = click.getRequest().toJSONString();
		logger.info(logs);
	}
}
