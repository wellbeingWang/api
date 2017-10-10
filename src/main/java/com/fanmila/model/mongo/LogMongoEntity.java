package com.fanmila.model.mongo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bson.types.ObjectId;

public class LogMongoEntity implements Cloneable {

	protected ObjectId id = ObjectId.get();
	protected Date createDate = new Date();
	private String sn = DateFormatUtils.format(createDate, "yyyy-MM-dd");

	public String getSn() {
		return sn;
	}

	protected void setSn(String sn) {
		this.sn = sn;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		setSn(DateFormatUtils.format(getCreateDate(), "yyyy-MM-dd"));
	}

	public LogMongoEntity copy() {
		try {
			return (LogMongoEntity) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("不能克隆");
		}
	}

	public String getCollectionName() {
		return StringUtils.uncapitalize(this.getClass().getSimpleName());
	}
}
