package com.fanmila.service.impl;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.fanmila.model.URLHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import com.fanmila.handlers.resolver.OmsLVMHandlerResolver;
import com.fanmila.model.Click;
import com.fanmila.service.task.OmsLVMLogClickTask;
import com.fanmila.util.framework.ContextUtils;
import com.fanmila.util.framework.RequestUtils;
import com.fanmila.util.framework.ThreadPoolUtils;
import com.fanmila.service.BaseServiceFace;


public class LV1DaoHangConfigServiceImpl extends BaseServiceFace {
	


	private static OmsLVMHandlerResolver dbHandler = ContextUtils.getBean(OmsLVMHandlerResolver.class);
	
	public String service() {
		URLHandlerContext context = new URLHandlerContext();
		
		context.setRequestData(getRequestJSON());
		try {
			generatorId(context);
			//context.setFdomain(context.getRequestData().getString("d"));
			//context.setVersion(context.getRequestData().getString("v"));

			dbHandler.getLV1DhConfig(context);
			this.getResultJSON().put("result", context);
		}catch (Exception e){
			e.printStackTrace();
			this.getResultJSON().put("result", context);
		}finally{
			//logClick(context);//记录日志到mongodb
		}
		setSucc();
		return getResult();
	}

	private void logClick(URLHandlerContext context) {
		try {
			Click click = new Click();
			click.setId(context.getId());
			click.setIp(ContextUtils.getInstance().getIpAddr());
			click.setDurl(context.getDurl());
			click.setOurl(getOurl());
			click.setCurl(getCurl());
			click.setUuid(context.getUuid());
			/*click.setChannel(context.getChannel());
			click.setFdomain(context.getFdomain());
			click.setFromurl(context.getFromurl());
			click.setLvmInfo(context.getLvmInfo());*/
			click.setCreateDate(new Date(ObjectId.massageToObjectId(context.getId()).getTime()));
			// FIXME: 这里可能出现多线程修改问题，后续不要修改handlerInfo对象
			click.setHandlerInfo(context.getHandlerInfo());
			ThreadPoolUtils.getInstance().addTask(new OmsLVMLogClickTask(click));
		} catch (Exception e) {
			logger.logError(e);
		}
	}

	private void generatorId(URLHandlerContext context) {
		context.setId(ObjectId.get().toString());
	}

	private String getCurl() {
		HttpServletRequest request = RequestUtils.getInstance().getRequest();
		String referer = request.getHeader("referer");
		return StringUtils.trimToEmpty(referer);
	}

	private String getOurl() {
		HttpServletRequest request = RequestUtils.getInstance().getRequest();
		StringBuffer sb = request.getRequestURL();
		if (StringUtils.isNotBlank(request.getQueryString())) {
			sb.append("?");
			sb.append(request.getQueryString());
		}
		return sb.toString();
	}

}
