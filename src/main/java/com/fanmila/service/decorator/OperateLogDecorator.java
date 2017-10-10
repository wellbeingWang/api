/*
 * [文 件 名]:OperateLogDecorator.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-12
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
 */

package com.fanmila.service.decorator;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.fanmila.service.AppServiceFace;
import com.fanmila.util.framework.RequestUtils;


/**
 * [简要描述]:用户行为日志 [详细描述]:用户行为日志
 * 
 * @author [Wiley]
 * @version [版本号,2012-10-12]
 * @since [comb5mpluginserver]
 */
public class OperateLogDecorator extends AbstractAppServiceDecorator {

	private static ThreadLocal<Map<String, Long>> timeLine = new ThreadLocal<Map<String, Long>>();

	private Logger logger = LoggerFactory.getLogger("PL_OP_LOG");

	private final String TIME = "ts";

	public OperateLogDecorator(AppServiceFace service) {
		super(service);
	}

	/**
	 * [简要描述]:用户行为日志 [详细描述]:用户行为日志
	 * 
	 * @return
	 * @exception
	 * @see com.fanmila.plugin.face.IServiceFace#service()
	 */
	@Override
	public String service() {


		String result = "";
		try {
			result = service.service();
		} catch (Exception e) {
			this.setFail(e.getMessage());
			result = this.getResult();

			logger.error("", e);
		} finally {
			String ts = RequestUtils.getInstance().getParamter(TIME);
			if (StringUtils.isNotBlank(ts)) {
				try {
					com.alibaba.fastjson.JSONObject rs = JSON.parseObject(result);
					rs.put(TIME, ts);
					result = rs.toJSONString();
				} catch (Exception e) {
					
				}

			}

		}
		logger.debug("ResponseData==>{}", result);
		return result;

	}

	public static void putTimeLine(String op, long ms) {
		timeLine.get().put(op, ms);
	}

}
