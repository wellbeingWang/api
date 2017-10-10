package com.fanmila.util.common;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;


/**
 * 日期范围类
 * 
 * @author xiao.zhao
 * 
 */
public class DateRange {
	private Date startDate;
	private Date endDate;

	public DateRange() {
	}

	public DateRange nextDay() {
		DateRange range = new DateRange();
		range.setStartDate(DateUtils.addDays(startDate, 1));
		range.setEndDate(DateUtils.addDays(endDate, 1));
		return range;
	}

	public DateRange lastDay() {
		DateRange range = new DateRange();
		range.setStartDate(DateUtils.addDays(startDate, -1));
		range.setEndDate(DateUtils.addDays(endDate, -1));
		return range;

	}


	public DateRange(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static DateRange newDateRange(Date startDate, Date endDate) {
		return new DateRange(startDate, endDate);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "<-- startDate: " + DateRangeUtils.formatDate(this.startDate) + "; endDate: " + DateRangeUtils.formatDate(this.endDate) +"-->";
	}
	
	

}
