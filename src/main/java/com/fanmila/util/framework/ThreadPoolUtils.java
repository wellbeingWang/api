package com.fanmila.util.framework;

import java.util.concurrent.ThreadPoolExecutor;

import com.fanmila.service.thread.ThreadPoolFactory;
import com.fanmila.service.task.BaseRunableTaskTemplate;
import com.fanmila.service.task.IRunnableTask;
import com.fanmila.service.task.ITask;
/**
 * 线程池工具
 * @author xiao.zhao
 *
 */
public class ThreadPoolUtils {
	private static ThreadPoolUtils instance = new ThreadPoolUtils();
	private ThreadPoolExecutor pool = ThreadPoolFactory.newCommonThreadPool(50,10000);

	private ThreadPoolUtils() {
	};

	public static ThreadPoolUtils getInstance() {
		return instance;
	}

	public void addTask(ITask task){
	    // 因线程池使用存在bug，暂改成同步执行
	    // task.execute();
	    
		pool.execute(new BaseRunableTaskTemplate(task));
	}

	public void addTask(IRunnableTask task) {
		pool.execute(task);
	}

	public void shutdown() {
		pool.shutdown();
	}
}
