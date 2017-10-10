package com.fanmila.service.impl;

import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.handlers.cps.CPSURLDbHandlerResolver;
import com.fanmila.model.cps.CpsClick;
import com.fanmila.service.BaseServiceFace;
import com.fanmila.service.task.CpsContextLogClickTask;
import com.fanmila.service.task.CpsLogClickTask;
import com.fanmila.util.framework.ContextUtils;
import com.fanmila.util.framework.RequestUtils;
import com.fanmila.util.framework.ThreadPoolUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * cps核心控制器
 *
 * <pre>
 * 	1、尝试用特定的处理器生成CPS引导URL
 * 	2、在dest上加上/替换GA标记生成URL
 * </pre>
 * @date Jan 6, 2013
 *
 * @return
 */
@SuppressWarnings("unused")
public class CpsRedirectServiceImpl extends BaseServiceFace {

//	private static CPSURLHandlerResolver handler = ContextUtils.getBean(CPSURLHandlerResolver.class);
//	private static CPSURLSUBHandlerResolver subHandler = ContextUtils.getBean(CPSURLSUBHandlerResolver.class);
	private static CPSURLDbHandlerResolver dbHandler = ContextUtils.getBean(CPSURLDbHandlerResolver.class);
//	private static ExtHandlerResolver extHandler = ContextUtils.getBean(ExtHandlerResolver.class);

	public String service() {
		CPSURLHandlerContext context = new CPSURLHandlerContext();

		context.setRequestData(getRequestJSON());
		String method = context.getRequestData().getString("method");
		try {
			generatorId(context);
			String ssub = this.getParam("ssub");
			String sid=context.getRequestData().getString("sid");
			/*if ("1000".equals(sid)) {//extb5t
				// 外部流量导入
				extHandler.getRedirectURL(context);
			} else {*/
			context.setTurl(this.getParam("dest"));
			context.setDocid(this.getParam("docid"));
			context.setUuid(this.getParam("uuid"));
			context.setSource(this.getParam("source"));
			context.setSid(sid);
			context.setSsub(ssub.toUpperCase());
			context.setPid(this.getParam("pid"));
			context.setCurl(context.getRequestData().getString("reffer"));

			context.setDatatype(this.getParam("datatype"));
			if ("M1".equalsIgnoreCase(ssub)) {//
					// SL LV1带跳转提示的
					context.getHandlerInfo().setSl(false);
					//subHandler.getRedirectURL(context);
			} else {
					///
					context.getHandlerInfo().setSl(true);
					//handler.getRedirectURL(context);
				}
			if("getRule".equals(method)){
				dbHandler.getRedirectRule(context);

			}else if("redirect".equals(method)){
				dbHandler.getRedirectURL(context);
			}


//			}
			this.getResultJSON().put("result", context);
		}finally{
			if("redirect".equals(method))
			    logClick(context, true);//记录日志
			else logClick(context, false);
		}
		setSucc();
		return getResult();
	}

	private void logClick(CPSURLHandlerContext context, boolean flag) {
		try {
			if(flag) {
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
				click.setSid(this.getParam("sid"));
				click.setSfb(this.getParam("ssub"));
				click.setDocid(context.getDocid());
				click.setUuid(context.getUuid());
				click.setSource(context.getSource());
				click.setFeedback(context.getFb());
				click.setDatatype(context.getDatatype());
				click.setCreateDate(new Date(ObjectId.massageToObjectId(context.getId()).getTime()));
				// FIXME: 这里可能出现多线程修改问题，后续不要修改handlerInfo对象
				click.setHandlerInfo(context.getHandlerInfo());
				ThreadPoolUtils.getInstance().addTask(new CpsLogClickTask(click));
			}else{
				if(context.getResponseData().isEmpty())context.getHandlerInfo().setSuccess(false);
				ThreadPoolUtils.getInstance().addTask(new CpsContextLogClickTask(context));
			}

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
