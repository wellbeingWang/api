package com.fanmila.service.impl;


import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.handlers.resolver.TaokeURLDbHandlerResolver;
import com.fanmila.model.cps.CpsClick;
import com.fanmila.service.BaseServiceFace;
import com.fanmila.service.task.TaokeLogClickTask;
import com.fanmila.util.framework.ContextUtils;
import com.fanmila.util.framework.RequestUtils;
import com.fanmila.util.framework.ThreadPoolUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 
 * @author lscm
 *
 */
@SuppressWarnings("unused")
public class TaoVasRedirectServiceImpl extends BaseServiceFace {
	

	private static TaokeURLDbHandlerResolver dbHandler = ContextUtils.getBean(TaokeURLDbHandlerResolver.class);

	public String service() {
		CPSURLHandlerContext context = new CPSURLHandlerContext();
		
		context.setRequestData(getRequestJSON());
		try {
			generatorId(context);
			String ssub = this.getParam("ssub");
			context.setTurl(this.getParam("tu"));
			context.setUuid(this.getParam("uuid"));
			context.setCurl(getCurl());
			context.setSid(this.getParam("sid"));
			context.setPid(this.getParam("pid"));
			context.setDatatype(this.getParam("datatype"));
			if ("in".equalsIgnoreCase(ssub)) {//saleb5t
				context.getHandlerInfo().setSl(false);
			} else {
				context.getHandlerInfo().setSl(true);
			}
			dbHandler.getRedirectURL(context);
			this.getResultJSON().put("result", context);
		}finally{
			logClick(context);//记录日志到mongodb
		}
		setSucc();
		return getResult();
	}

	private void logClick(CPSURLHandlerContext context) {
		try {
			CpsClick click = new CpsClick();
			click.setId(context.getId());
			click.setIp(ContextUtils.getInstance().getIpAddr());
			click.setTurl(context.getTurl());
			click.setTdomain(context.getTdomain());
			click.setDurl(context.getDurl());
			click.setOurl(getOurl());
			click.setCurl(getCurl());
			click.setRurl(this.getParam("rurl"));
			click.setDurl(context.getDurl());
			click.setSid(context.getSid());
			click.setSfb(context.getSsub());
			click.setDocid(context.getDocid());
			click.setUuid(context.getUuid());
			click.setSource(context.getSource());
			click.setCreateDate(new Date(ObjectId.massageToObjectId(context.getId()).getTime()));
			// FIXME: 这里可能出现多线程修改问题，后续不要修改handlerInfo对象
			click.setHandlerInfo(context.getHandlerInfo());
			ThreadPoolUtils.getInstance().addTask(new TaokeLogClickTask(click));
		} catch (Exception e) {
			logger.logError(e);
		}
	}

	private void generatorId(CPSURLHandlerContext context) {
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
