package com.daxiasoftware.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
    // 星期天是1
    private static String[] week = new String[] {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    
    public static String getWeekChineseName(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return week[dayOfWeek];
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(secondsToDurationText(3602 * 10 + 59));
    }
    
    /**
     * 获取 最近 多少天的 起止日期
     * @param days
     * @return Date[0]=startDate, Date[1]=endDate
     */
    public static Date[] getLatestDays(int days) {
        Date end = new Date();
        Date start = org.apache.commons.lang.time.DateUtils.addDays(end, -days);
        return new Date[] {start, end};
    }
    
    /**
     * 返回月初时间 
     * @param date
     * @return
     */
    public static Date getBeginingOfAMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * 获取月末时间
     * @param date
     * @return
     */
    public static Date getEndOfAMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
	/**
	 * 返回一天的最开始的时候,00:00:00
	 * @param date
	 * @return
	 */
	public static Date getBeginningOfADay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 返回一天的最后的时候, 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getEndOfADay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

    public static Date getWeekStartDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        Date start = date;
        if (isMonday(start)) {
            return start;
        } else {
            // 倒推
            for (int i = 0; i < 7; i++) {
                start = org.apache.commons.lang.time.DateUtils.addDays(date, -(i + 1));
                if (isMonday(start)) {
                    return start;
                }
            }
        }
        return start;
    }
    
    private static boolean isMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 周日是1
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.MONDAY;
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 周日是1
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            return 7;
        } else {
            return dayOfWeek - 1;
        }
    }
    
    public static String secondsToDurationText(int seconds) {
        String text = "";
        int hours = seconds / 3600;
        seconds = seconds - (3600 * hours);
        text += StringUtils.leftPad(hours + "", 2, "0") + ":";
        
        int minutes = seconds / 60;
        text += StringUtils.leftPad(minutes + "", 2, "0") + ":";
        
        seconds = seconds - minutes * 60;
        text += StringUtils.leftPad(seconds + "", 2, "0");
        
        return text;
    }

}