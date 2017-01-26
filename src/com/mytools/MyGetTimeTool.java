package com.mytools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  列出三个方法，分别是输入对当前时间进行分钟、小时、日期修改的三个方法
 */
public class MyGetTimeTool {
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /*
     * 获取当前时间之前或之后几分钟 minute
     */
    public static String getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return df.format(calendar.getTime());
    }

    /*
     * 获取当前时间之前或之后几小时 hour
     */
    public static String getTimeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return df.format(calendar.getTime());
    }

    /*
     * 获取当前时间之前或之后几天 day
     */
    public static String getTimeByDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        return df.format(calendar.getTime());
    }

    /*
     * 输出指定时间的方法
     */
    public static String printDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return printDate(calendar);
    }
    public static String printDate(Calendar calendar) {
        return df.format(calendar.getTime());
    }
    public static String printDate() {
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getTimeByMinute(-5));
        System.out.println(getTimeByHour(1));
        System.out.println(getTimeByDay(3));
    }
}
