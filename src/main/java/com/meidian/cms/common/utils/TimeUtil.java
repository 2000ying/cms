package com.meidian.cms.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Title: com.meidian.cms.common.utils<br>
 * Description: <br>
 * Copyright: Copyright (c) 2015<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author 张中凯
 *         2017/11/12
 */
public class TimeUtil {
    /**
     * 根据传入的时间字符串，获得unix对应的时间戳格式
     *
     * @author liuqiang(liuqang@meicai.cn)
     * 2016年3月12日
     * @param day
     * @return
     */
    public static Integer getDayUnixTimeStamp(String day) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = df.parse(day);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long timestamp = cal.getTimeInMillis();
            return Integer.valueOf((int) (timestamp / 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将日期转换为 时间戳
     * @author yangweiqiang 2016.12.01
     * @param date
     * @return
     */
    public static Integer getDayUnixTimeStamp(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String day = df.format(date);
        return getDayUnixTimeStamp(day);
    }

    /**
     * 获取当天日期的unix时间戳
     *
     * @author liuqiang(liuqiang@meicai.cn)
     * 2016年3月12日
     * @return
     */
    public static Integer getTodayUnixTimeStamp() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String day = df.format(date);
        return getDayUnixTimeStamp(day);
    }

    /**
     * 获得当前系统时间戳
     * @author  liuqiang(liuqiang@meicai.cn)
     * 2016年3月20日
     * @return
     */
    public static Integer getNowTimeStamp() {
        Integer now = 0;
        Long time = System.currentTimeMillis() / 1000;
        now = time.intValue();
        return now;
    }

    /**
     * 描述: 将秒转换为指定格式化的日期
     * @author yangweiqiang
     * @param timeStamp 秒数
     * @param format 格式化 yyyy-MM-dd等
     * @date   2016/8/15
     */
    public static String getFormatDate(Integer timeStamp,SimpleDateFormat format){
        return format.format(new Date(timeStamp * 1000L));
    }

    /**
     * 获取某月最大的天数
     * @author yangweiqiang
     * @param time 日期
     * @param format 日期格式化类型
     * @date   2016/8/3
     * @return 实际最大天数
     */
    public static int getMaxDayOfMonth(String time,SimpleDateFormat format){
        try {
            Date date = format.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取昨天的时间戳
     * @return
     */
    public static int getPreDayByToday(Integer days){
        return getTodayUnixTimeStamp() + 86400 * days;
    }

    /**
     * 获取指定天的推迟天时间戳
     * @param time
     * @param days
     * @return
     */
    public static int getPreDayBy(Integer time,Integer days){
        return time + 86400 * days;
    }

    /**
     * 获取指定天的推迟天时间戳
     * @param time
     * @param days 增加的天数
     * @return
     */
    public static int getPreDayBy(String time,Integer days){
        return getDayUnixTimeStamp(time) + 86400 * days;
    }

    /**
     * 根据时间戳获取日期（此日期为几号）
     * @param date
     * @return
     */
    public static int getDayBy(Integer date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date((long)date * 1000));
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取上个月第一天的Unix时间戳
     */
    public static Integer getLastMonthFirstDayUnixTimeStamp() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date lastMonthFirstDay = cal.getTime();
        return TimeUtil.getDayUnixTimeStamp(lastMonthFirstDay);
    }

    /**
     * 获取上个月最后一天的Unix时间戳
     */
    public static Integer getLastMonthLastDayUnixTimeStamp() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastMonthLastDay = cal.getTime();
        return TimeUtil.getDayUnixTimeStamp(lastMonthLastDay);
    }

    /**
     * 获取本月第一天的Unix时间戳
     */
    public static Integer getThisMonthFirstDayUnixTimeStamp() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date thisMonthFirstDay = cal.getTime();
        return TimeUtil.getDayUnixTimeStamp(thisMonthFirstDay);
    }
    /**
     * 获取传入时间戳的所在月第一天的Unix时间戳
     */
    public static Integer getTheMonthFirstDayUnixTimeStamp(Integer timeStamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date((long)timeStamp * 1000));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date thisMonthFirstDay = cal.getTime();
        return TimeUtil.getDayUnixTimeStamp(thisMonthFirstDay);
    }
    /**
     * 获取传入时间戳的所在月15号Unix时间戳
     */
    public static Integer getTheMonth16UnixTimeStamp(Integer timeStamp) {
        return getTheMonthFirstDayUnixTimeStamp(timeStamp) + 3600 * 24 * 15;
    }
    /**
     * 获取传入时间戳的所在月最后一天的Unix时间戳
     */
    public static Integer getTheMonthEndDayUnixTimeStamp(Integer timeStamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date((long)timeStamp * 1000));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date thisMonthFirstDay = cal.getTime();
        return TimeUtil.getDayUnixTimeStamp(thisMonthFirstDay);
    }

    /**
     * Unix时间戳,转为指定格式的日期时间字符串
     */
    public static String unixTimestampToDateString(int unixTimestamp, String dateFormat) {
        long unixTimestampLong = (long)unixTimestamp * 1000;
        Date date = new Date(unixTimestampLong);
        String dateStr = DateFormatUtils.format(date, dateFormat);
        return dateStr;
    }

    /**
     * 日期格式字符串,转为Unix时间戳
     */
    public static int dateStringToUnixTimestamp(String dateString, String dateFormat) throws ParseException {
        Date date = DateUtils.parseDateStrictly(dateString, new String[]{dateFormat});
        long timestampLong = date.getTime();
        int unixTimestamp = (int)(timestampLong/1000);
        return unixTimestamp;
    }
}
