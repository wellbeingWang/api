package com.fanmila.model.common;

import java.util.HashMap;
import java.util.Map;

/**
 * FIXME Message可能需要提供扩展机制，或者用的地方自行提供扩展机制
 * @author Larry
 */
public class ErrorMessage {
	/**
	 * 系统默认失败
	 */
	public static int SYSTEM_DEFALUT_ERROR = -1;
	/**
	 * 请求验证签名失败
	 */
	public static int AUTH_FAIL_ERROR = 100;
	/**
	 * APPKEY 过期
	 */
	public static int APPKEY_EXPIRED_ERROR = 200;
	/**
	 * 条码在数据库中没有发现
	 */
	public static int BARCODE_NOTFOUND_ERROR = 400;
	/**
	 * 需要查询的条码为空
	 */
	public static int BARCODE_EMPTY_ERROR = 401;
	/**
	 * 条码数据库异常
	 */
	public static int BARCODE_DATABASE_ERROR = 402;
	/**
	 * 搜索引擎连接异常
	 */
	public static int SF1_IOEXCEPTION = 300;

	private static final Map<Integer, String> messageMap = new HashMap<Integer, String>();

	static {
		messageMap.put(SYSTEM_DEFALUT_ERROR, "服务失败");
		messageMap.put(AUTH_FAIL_ERROR, "非法请求");
		messageMap.put(APPKEY_EXPIRED_ERROR, "版本已过期，请下载新版本");
		messageMap.put(BARCODE_NOTFOUND_ERROR, "条码没有找到");
		messageMap.put(BARCODE_EMPTY_ERROR, "查询的条码为空");
		messageMap.put(BARCODE_DATABASE_ERROR, "条码数据库访问失败");
		messageMap.put(SF1_IOEXCEPTION, "搜索引擎连接异常");
	}

	public static String getMessage(int errorCode) {
		return messageMap.get(errorCode);
	}
}
