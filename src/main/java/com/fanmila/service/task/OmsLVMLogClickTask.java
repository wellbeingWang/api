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
public class OmsLVMLogClickTask extends AbstractBaseTask {


	private Logger logger = LoggerFactory.getLogger("dmp_zhike_click");

	public static final String CPS_DB_NAME = "clt_oms_lvm";

	private Click click;

	public OmsLVMLogClickTask(Click click) {
		this.click = click;
	}

	public Click getClick() {
		return click;
	}

	@Override
	public void execute() {
		logger.info("");
	}
}
