package com.fanmila.service.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


/**
 * 通用常规任务线程池
 * 
 * @author xiao.zhao
 * 
 */
public class CommonThreadPoolExecutor extends ThreadPoolExecutor {

	protected Logger logger = Logger.getLogger(this.getClass());
	private int rejectedCount = 0;
	private RejectedExecutionHandler rejected = new RejectedExecutionHandler() {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
			// TODO 多线程不安全，记录个大概吧先
			logger.warn(String.format("%s ThreadPool rejected task [count:%s]",
					Thread.currentThread().getName(), ++rejectedCount));
		}
	};


	public CommonThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		/**
		 * 任务拒绝时执行饱和策略
		 */

		this.setRejectedExecutionHandler(rejected);

	}

	public CommonThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue,
			RejectedExecutionHandler rejectedExecutionHandler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		/**
		 * 任务拒绝时执行饱和策略
		 */

		this.setRejectedExecutionHandler(rejectedExecutionHandler);

	}

	/**
	 * 任务回调入口
	 */
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
	}

	/**
	 * 任务回调入口
	 */
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
	}
}
