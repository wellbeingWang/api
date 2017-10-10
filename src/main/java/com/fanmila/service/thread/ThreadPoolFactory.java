package com.fanmila.service.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
	/**
	 * 获取常规任务池
	 * @param queueSize　池列队大小
	 * @return
	 */
	public static ThreadPoolExecutor newCommonThreadPool(int queueSize) {
		int N_CPUS = Runtime.getRuntime().availableProcessors();
		return newCommonThreadPool(N_CPUS,queueSize);

	}
	public static ThreadPoolExecutor newCommonThreadPool(int coreSize,int queueSize) {
		
		ThreadPoolExecutor executor = new CommonThreadPoolExecutor(coreSize,
				coreSize + 1, 1L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>(queueSize));
		
		return executor;

	}
	/**
	 * 生产者线程池
	 * @param coreSize
	 * @param maxSize
	 * @param queueSize
	 * @return
	 */
	public static ThreadPoolExecutor newMessageThreadPool(int coreSize,int maxSize,int queueSize) {
		
		return newMessageThreadPool(coreSize,maxSize,queueSize,new ThreadPoolExecutor.CallerRunsPolicy());

	}
	public static ThreadPoolExecutor newMessageThreadPool(int coreSize,int maxSize,int queueSize,RejectedExecutionHandler rejectedHandler) {
		
		ThreadPoolExecutor executor = new CommonThreadPoolExecutor(maxSize,
				maxSize, 1L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>(queueSize),rejectedHandler);
		
		return executor;

	}
}
