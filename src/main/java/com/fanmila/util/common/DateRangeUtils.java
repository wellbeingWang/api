package com.fanmila.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;



/**
 * 日期范围工具
 * 
 * @author xiao.zhao
 * 
 */
public class DateRangeUtils {
	
	
	// 获得下周星期一的日期  
    @SuppressWarnings("static-access")
    public static String getNextMonday(int count) {  
          
        Calendar strDate = Calendar.getInstance();         
        strDate.add(strDate.DATE,count);  
          
        //System.out.println(strDate.getTime());  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.set(strDate.get(Calendar.YEAR), strDate.get(Calendar.MONTH),strDate.get(Calendar.DATE));  
        Date monday = currentDate.getTime();  
        SimpleDateFormat df = new SimpleDateFormat("MMdd");  
        String preMonday = df.format(monday);  
        return preMonday;  
    }  
	
    /**
	 * 获得自然周范围 当前日期的星期的周一至周日
	 * @author caliph 2013-05-29
	 * @param date
	 * @return　DateRange
	 */
	public static DateRange getWeek(Date date) {
		GregorianCalendar c=new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		Date sunDayDate=DateRangeUtils.getDay(c.getTime()).getStartDate();
		if (DateRangeUtils.getDay(date).getStartDate().getTime()==sunDayDate.getTime()) {
			Date endDate=DateRangeUtils.getDay(c.getTime()).getStartDate();
			Date startDate=DateRangeUtils.getDay(DateUtils.addDays(endDate, -6)).getStartDate();
			DateRange range = new DateRange(startDate,endDate);
			return range;
		} else {
			c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);//获取当前日期的所在星期的周一
			Date startDate=DateRangeUtils.getDay(c.getTime()).getStartDate();
			Date endDate=DateRangeUtils.getDay(DateUtils.addDays(startDate, 6)).getStartDate();
			DateRange range = new DateRange(startDate,endDate);
			return range;
		}
	}

	/**
	 * 获得自然月范围 当前日期的月份的1号至月末
	 * @author caliph 2013-05-29
	 * @param date
	 * @return　DateRange
	 */
	public static DateRange getMonth(Date date) {
		GregorianCalendar c=new GregorianCalendar();
		c.setTime(DateRangeUtils.getDay(date).getStartDate());
		DateRange range = new DateRange();
		int minDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);//获取当前日期月的开始时间
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), minDay, 00, 00, 00);
        range.setStartDate(c.getTime());
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);//获取当前日期月的结束时间
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), maxDay, 23, 59, 59);
        range.setEndDate(c.getTime());
		return range;
	}

	/**
	 * 是否是当天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isNowDay(Date date) {
		return DateUtils.isSameDay(format(date), getTheDay().getStartDate());
	}

	/**
	 * 获得自然日范围
	 * 
	 * @param date
	 * @return
	 */
	public static DateRange getDay(Date date) {

		Date startDate = format(date);
		Date endDate = DateUtils.addDays(startDate, 1);

		return DateRange.newDateRange(startDate, endDate);
	}

	public static String formatDate(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(date);
	}
	
	public static String formatDate(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}
	
	public static String getDateTime(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//ComConstants.SDF_YYYYMMDDHHMMSS;
	    return sdf.format(date);
	}
	public static String getDateTime(Date date, String fromat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(fromat);//ComConstants.SDF_YYYYMMDDHHMMSS;
	    return sdf.format(date);
	}
	
	/**
	 * 获得自然日范围
	 * 
	 * @param date
	 * @return
	 */
	public static DateRange getDay(String date) {

		Date startDate = pasre(date);
		Date endDate = DateUtils.addDays(startDate, 1);

		return DateRange.newDateRange(startDate, endDate);
	}

	/**
	 * 获得今天时间范围
	 * 
	 * @return
	 */
	public static DateRange getTheDay() {
		Date startDate = format(new Date());
		Date endDate = DateUtils.addDays(startDate, 1);
		return DateRange.newDateRange(startDate, endDate);
	}

	/**
	 * 获得昨天时间范围
	 * 
	 * @return
	 */
	public static DateRange getYesterDay() {
		Date endDate = format(new Date());
		Date startDate = DateUtils.addDays(endDate, -1);
		return DateRange.newDateRange(startDate, endDate);
	}

	public static List<DateRange> getLastDateByDays(Date currentDate, int days) {
		List<DateRange> dateList = new ArrayList<DateRange>();
		DateRange currentRange = DateRangeUtils.getDay(currentDate);
		dateList.add(currentRange);
		if (days > 0) {
			dateList.addAll(getLastDateByDays(currentRange.lastDay().getStartDate(), days - 1));
		}

		return dateList;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date format(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date pasre(String date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
     * [简要描述]:获得指定时间的当月所有天
     *  [详细描述]:
     * @author [廖明辉]
     * @E-mail [j2ee.liao@gmail.com]
     * @date   [2013-1-4]
     * @method [getMonthDateRangeList]
     * @param date
     * @return
     * @retruntype [List<DateRange>]
     * @exception
     */
    public static List<DateRange> getMonthDateRangeList(Date date)
    {
        String str_date = DateRangeUtils.formatDate(date);
        int dayCount = getDays(str_date);
        DateRange range = getFirstDay(date);
        Date fristDay = range.getStartDate();
        List<DateRange> list = new ArrayList<DateRange>();
        list.add(range);
        for (int i = 2; i <= dayCount; i++)
        {
            list.add(DateRangeUtils.getDay(fristDay).nextDay());
            fristDay = DateRangeUtils.getDay(fristDay).nextDay().getStartDate();
        }
        return list;
    }
    /**
     * [简要描述]:获得指定时间的月份总天数
     *  [详细描述]:
     * @author [廖明辉]
     * @E-mail [j2ee.liao@gmail.com]
     * @date   [2013-1-4]
     * @method [getDays]
     * @return
     * @retruntype [int]
     * @exception
     */
    public static int getDays(String date)
    {
        String[] str = date.split("-");
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(str[0]));
        c.set(Calendar.MONTH, Integer.parseInt(str[1])-1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * [简要描述]:获得指定时间的月份第一天
     *  [详细描述]:
     * @author [廖明辉]
     * @E-mail [j2ee.liao@gmail.com]
     * @date   [2013-1-4]
     * @method [getFirstDay]
     * @param theDate
     * @return
     * @retruntype [DateRange]
     * @exception
     */
    public static DateRange getFirstDay(Date theDate)
    {
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = DateRangeUtils.formatDate(gcLast.getTime());
       return  DateRangeUtils.getDay(day_first);
    }
    
    public static String getFirstDayWithFormat(Date theDate, String fromat)
    {
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = DateRangeUtils.formatDate(gcLast.getTime(), fromat);
       return  day_first;
    }
}
