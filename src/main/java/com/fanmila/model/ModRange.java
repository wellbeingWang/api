package com.fanmila.model;
/**
 * 模区间对象，标识模一个数值后其值落在的区间范围
 * @author caliph
 * 2014-03-17
 */
public class ModRange {
	private int mod;//模区间的模
	private int min;//区间最小值
	private int max;//区间最大值
	public ModRange()
	{
		super();
	}
	public ModRange(int min,int max)
	{
		this(0,min,max);
	}
	public ModRange(int mod,int min,int max)
	{
		this.mod=mod;
		this.min=min;
		this.max=max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMod() {
		return mod;
	}
	public void setMod(int mod) {
		this.mod = mod;
	}
	
}
