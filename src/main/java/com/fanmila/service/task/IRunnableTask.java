package com.fanmila.service.task;

public interface IRunnableTask  extends Runnable{

	public abstract ITask getCallback();

	public abstract void setCallback(ITask callback);

	public abstract int getSeelpTime();

	public abstract void setSeelpTime(int seelpTime);


}