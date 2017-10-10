package com.fanmila.model.log;

import com.fanmila.model.common.Constants;
import org.apache.log4j.Logger;


/**
 * 本类用于移动业务部分的日志处理。<BR>
 * 在金录AppLog上修改
 * @author Wiley.Wang
 */
public class B5MLogger {
	/**
	 * log4j对象
	 */
	private static Logger logger = Logger.getLogger(Constants.LOG_NAME);

	/**
	 * 类名
	 */
	private String className = "";

	/**
	 * <p>
	 * 日志处理类构造函数
	 * </p>
	 * 
	 * @param clsName
	 *            类名
	 */
	public B5MLogger(String clsName) {
		this.className = clsName;
	}

	/**
	 * <p>
	 * 日志处理类构造函数
	 * </p>
	 * 
	 * @param clsName
	 *            类名
	 */
	public B5MLogger(String clsName, String userID) {
		this.className = clsName;
	}

	/**
	 * <p>
	 * 取得Log的方法名。
	 * </p>
	 * 
	 * @return 方法名
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <p>
	 * 向log文件输出系统严重错误信息。
	 * </p>
	 * 
	 * @param e
	 *            异常对象
	 * @param strMethodName
	 *            方法名
	 */
	public void logFatal(Exception e, String strMethodName) {

		logger.fatal(this.getMessage(e.getMessage(), strMethodName), e);

	}

	/**
	 * <p>
	 * 向log文件输出系统严重错误信息。
	 * </p>
	 * 
	 * @param e
	 *            异常对象
	 */
	public void logError(String errMsg) {
		logger.error(this.getMessage(errMsg, new Throwable().getStackTrace()[1].getMethodName()));
	}

	/**
	 * <p>
	 * 向log文件输出系统严重错误信息。
	 * </p>
	 * 
	 * @param e
	 *            异常对象
	 */
	public void logFatal(Exception e) {
		logger.fatal(this.getMessage(e.getMessage(), new Throwable().getStackTrace()[1].getMethodName()), e);

	}

	/**
	 * <p>
	 * 向log文件输出系统异常信息。
	 * </p>
	 * 
	 * @param e
	 *            异常对象
	 * @param strMethodName
	 *            方法名
	 */
	public void logError(Throwable e, String strMethodName) {
		logger.error(this.getMessage(e.getMessage(), strMethodName), e);
	}

	/**
	 * <p>
	 * 向log文件输出系统异常信息。
	 * </p>
	 * 
	 * @param e
	 *            异常对象
	 */
	public void logError(Throwable e) {

		logger.error(this.getMessage(e.getMessage(), new Throwable().getStackTrace()[1].getMethodName()), e);

	}

	/**
	 * <p>
	 * 向log文件输出系统提示信息。
	 * </p>
	 */
	public void logInfoMsg(String msg) {
		logger.info(this.getMessage(msg, new Throwable().getStackTrace()[1].getMethodName()));
	}

	/**
	 * <p>
	 * 向log文件输出系统调试信息。
	 * </p>
	 * 
	 * @param msg
	 *            系统Debug信息
	 * @param strMethodName
	 *            方法名
	 */
	public void logDebug(String msg, String strMethodName) {

		logger.debug(this.getMessage(msg, strMethodName));
	}

	/**
	 * <p>
	 * 向log文件输出系统调试信息。
	 * </p>
	 * 
	 * @param msg
	 *            系统Debug信息
	 * @param strMethodName
	 *            方法名
	 */
	public void logDebug(String msg) {
		logger.debug(this.getMessage(msg, new Throwable().getStackTrace()[1].getMethodName()));
	}

	/**
	 * <p>
	 * 向log文件输出系统跟踪信息。
	 * </p>
	 * 
	 * @param msg
	 *            系统跟踪信息
	 * @param strMethodName
	 *            方法名
	 */
	public void logTrace(String msg, String strMethodName) {
		logger.trace(this.getMessage(msg, strMethodName));
	}

	/**
	 * <p>
	 * 向log文件输出系统跟踪信息。
	 * </p>
	 * 
	 * @param msg
	 *            系统跟踪信息
	 * @param strMethodName
	 *            方法名
	 */
	public void logTrace(String msg) {
		logger.trace(this.getMessage(msg, new Throwable().getStackTrace()[1].getMethodName()));
	}

	/**
	 * <p>
	 * 取得带有类名和方法名的日志信息。
	 * </p>
	 * 
	 * @param msg
	 *            日志信息
	 * @param strMethodName
	 *            方法名
	 * @return 带有类名和方法名的日志信息
	 */
	private String getMessage(String msg, String strMethodName) {
		StringBuffer logMsg = new StringBuffer();
		logMsg.append(Constants.MSG_FORMAT_START);
		logMsg.append(this.className);
		logMsg.append(Constants.MSG_FORMAT_END);
		logMsg.append(Constants.MSG_FORMAT_SPLIT);
		logMsg.append(Constants.MSG_FORMAT_START);
		logMsg.append(strMethodName);
		logMsg.append(Constants.MSG_FORMAT_END);
		logMsg.append(Constants.MSG_FORMAT_SPLIT);
		logMsg.append(msg);
		return logMsg.toString();
	}
}
