package com.myuan.login.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import lombok.extern.log4j.Log4j;

@Log4j
public class DateUtil {

    public static final long MINUTE = 60;

    public static final long HOUR = MINUTE * 60;

    public static final long DAY = HOUR * 24;

    public static final long WEEK = DAY * 7;

    public final static String getDate(Date startTime) {

        long min = (new Date().getTime() - startTime.getTime()) / 1000;//除以1000转换成秒
        if (min < 60) {
            return "刚刚";
        } else if (min >= 60 && min < 60 * 60) {
            long num = min / 60;
            return num + "分钟前";
        } else if (min >= 60 * 60 && min < 60 * 60 * 24) {
            long num = min / 3600;
            return num + "小时前";
        } else if (min >= 60 * 60 * 24 && min < 60 * 60 * 24 * 4) {
            long num = min / 86400;
            return num + "天前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(startTime);
        }

    }

    //本周 <liuwei> [2018/2/26 11:11]
    public final static Date getThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 1);
        c.add(Calendar.DATE, -day_of_week + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    // <liuwei> [2018/3/6 12:59]
    public static Date parseString2Date(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("时间转换异常");
        }
        return null;
    }
}
