package com.fanmila.handlers.cps.selfUnion.jd;

public class BatchJdCpsUrl {
	private int id;
	private String url;
	public BatchJdCpsUrl() {
		super();
	}
	public BatchJdCpsUrl(int id, String url) {
		super();
		this.id = id;
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "BatchJdCpsUrl [id=" + id + ", url=" + url + "]";
	}
	
}
