package com.fanmila.handlers.cps;

import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultUnionHandler extends CPSURLHandler {

	private String[] applyTo = {};

	public String getRedirectURL(CPSURLHandlerContext context, ClickHandlerInfo handlerInfo) {
		String url = context.getTurl();//appendParams(context.getTurl(), context);
		context.setDurl(url);
		return context.getDurl();
	}

	@Override
	public String getRedirectURL(URLHandlerContext context) {
		return null;
	}

	@Override
	public String[] getApplyTO() {
		return applyTo;
	}

	@Override
	public String getFeedback(URLHandlerContext context) {
		return null;
	}

	@Override
	protected ClickType getClickType() {
		return ClickType.NONE;
	}
}
