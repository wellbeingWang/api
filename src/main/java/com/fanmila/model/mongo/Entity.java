package com.fanmila.model.mongo;

import java.util.Date;


public abstract class Entity {

	protected String id;
	protected Date createDate = new Date();
	/**
	 *@Deprecated 在依赖多库的情况下不能用
	 */
	/*@Deprecated
	public void save(){
		MongoUtils.getMongoTemplate().save(this);
	}*/
	public Entity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}