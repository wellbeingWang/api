package com.fanmila.service.task;

public  class BaseRunableTaskTemplate implements IRunnableTask {
	private int seelpTime = 0;
	private ITask callback = null;

	public BaseRunableTaskTemplate(ITask call) {
		this.callback=call;
	}

	public ITask getCallback() {
		return callback;
	}

	public void run() {
		if (callback != null) {
			callback.execute();
		}
	}

	public void setCallback(ITask callback) {
		this.callback = callback;
	}

	public int getSeelpTime() {
		return seelpTime;
	}

	public void setSeelpTime(int seelpTime) {
		this.seelpTime = seelpTime;
	}

}