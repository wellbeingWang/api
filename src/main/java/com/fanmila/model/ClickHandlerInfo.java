package com.fanmila.model;

import java.util.List;

import com.fanmila.handlers.IApiHandler;
import com.fanmila.handlers.IUnionHandler;
import com.google.common.collect.Lists;

/**
 * 跳转处理过程信息记录
 * 
 * @author larry
 * 
 */
public class ClickHandlerInfo {

	private List<String> handlers = Lists.newArrayList();
	private Union union;
	//sl=true 标识为LV2 sl=false 标识为LV1
	private boolean sl;
	private boolean ext;
	private boolean hm;
	private ClickType clickType;
	private boolean success = true;

	public List<String> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<String> handlers) {
		this.handlers = handlers;
	}

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public boolean isSl() {
		return sl;
	}

	public void setSl(boolean sl) {
		this.sl = sl;
	}

	public ClickType getClickType() {
		return clickType;
	}

	public void setClickType(ClickType clickType) {
		this.clickType = clickType;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void recordHandler(IUnionHandler handler) {
		this.handlers.add(handler.getClass().getSimpleName());
	}

	public void recordApiHandler(IApiHandler handler) {
		this.handlers.add(handler.getClass().getSimpleName());
	}

	public boolean isHm() {
		return hm;
	}

	public void setHm(boolean hm) {
		this.hm = hm;
	}

	public boolean isExt() {
		return ext;
	}

	public void setExt(boolean ext) {
		this.ext = ext;
	}
}
