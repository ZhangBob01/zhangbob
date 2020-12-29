package com.bob.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zhang bob
 * @date: 2020-12-24 13:45
 * @description: 日期工具类
 */
public class DateUtils extends  org.apache.commons.lang3.time.DateUtils {
    /** 年月日.*/
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    /** 年月日时分秒.*/
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /** 常规年月日时分秒.*/
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyyMMdd"};

    /**
     * 获取当前日期
     * @return
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期字符串
     * @return
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String dateTimeNow(String format) {
        return parseDateToStr(format, new Date());
    }

    /**
     * 日期转字符串
     * @param format
     * @param date
     * @return
     */
    public static String parseDateToStr(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取日期路径
     * @return
     */
    public static String getDatePath(){
        Date nowDate = new Date();
        return parseDateToStr(parsePatterns[4], nowDate);
    }

    /**
     * 获取日期路径
     * @return
     */
    public static String getYyyyMmDd(){
        Date nowDate = new Date();
        return parseDateToStr(parsePatterns[12], nowDate);
    }

    /**
     * 字符串转日期
     * @param format
     * @param dateStr
     * @return
     */
    public static Date dateTime(String format, String dateStr){
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转日期
     * @param str
     * @return
     */
    public static Date parseDate(Object str){
        if (str == null || "".equals(str)){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        }catch (ParseException e){
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     * @return
     */
    public static Date getServerStartDate(){
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个日期相差天数
     * @param fromDay
     * @param toDay
     * @return
     */
    public static int getDifferentDays(Date fromDay, Date toDay){
        return Math.abs((int) (fromDay.getTime() - toDay.getTime()) / (1000*60*60*24));
    }

    /**
     * 获取两个日期差
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date nowDate){
        long day = 1000 * 60 * 60 * 24;
        long hour = 1000 * 60 * 60;
        long minute = 1000 * 60;
        //计算相差毫秒数
        long ms = endDate.getTime() - nowDate.getTime();
        //计算相差天数
        long diffDay = ms / day;
        //计算相差小时数
        long diffHour = ms / hour;
        //计算相差分钟数
        long diffMinute = ms / minute;
        return diffDay + "天" + diffHour + "小时" + diffMinute + "分钟";
    }

    public static void main(String[] args) {
        Date date1 = dateTime(YYYY_MM_DD_HH_MM_SS, "2020-12-10 10:20:00");
        Date date2 = dateTime(YYYY_MM_DD_HH_MM_SS, "2020-12-15 10:20:00");
        System.out.println(getDatePoor(date2, date1));
    }

}