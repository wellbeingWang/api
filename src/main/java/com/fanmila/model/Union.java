package com.fanmila.model;

public class Union {

	private String accountId;
	private String name;

	public Union(String accountId, String name) {
		this.accountId = accountId;
		this.name = name;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
