package com.fanmila.handlers.cps.selfUnion;

public class SiteSize {
	private String siteSize;
	
	private int x;
	private int y;
	public SiteSize() {
	}	
	public SiteSize(String siteSize) {
		super();
		this.siteSize = siteSize;
	}
	public String getSiteSize() {
		return siteSize;
	}
	public void setSiteSize(String siteSize) {
		this.siteSize = siteSize;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "SiteSize [siteSize=" + siteSize + ", x=" + x + ", y=" + y + "]";
	}
	
}
