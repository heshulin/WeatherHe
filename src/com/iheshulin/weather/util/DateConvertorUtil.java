package com.iheshulin.weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HeShulin on 2018/8/15.
 */
public class DateConvertorUtil {

    public static String getTodayWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";


        }
        return "";
    }
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    public static String getMonthDay()
    {
        String monthDay;
        java.util.Calendar c=java.util.Calendar.getInstance();
        java.text.SimpleDateFormat f=new java.text.SimpleDateFormat("yyyy-MM-dd");
        System.out.println(f.format(c.getTime()));
//        String issueDate = "2001-12-25";
        monthDay = getMonthStr((formatStr(f.format(c.getTime()))))+"月"+getDayStr((formatStr(f.format(c.getTime()))));
        return monthDay;
    }
    /**
     * create date:2010-5-22下午04:29:37
     * 描述：将日期转换为指定格式字符串
     * @param date  日期
     * @return
     */
    public static String getDateStr(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String datestr = sdf.format(date);
        return datestr;
    }
    /**
     * create date:2010-5-22下午03:40:44
     * 描述：取出日期字符串中的年份字符串
     * @param str 日期字符串
     * @return
     */
    public static String getYearStr(String str)
    {
        String yearStr = "";
        yearStr = str.substring(0,4);
        return yearStr;
    }

    /**
     * create date:2010-5-22下午03:40:47
     * 描述：取出日期字符串中的月份字符串
     * @param
     * @return
     */
    public static String getMonthStr(String str)
    {
        String monthStr;
        int startIndex = str.indexOf("年");
        int endIndex = str.indexOf("月");
        monthStr = str.substring(startIndex+1,endIndex);
        return monthStr;
    }

    /**
     * create date:2010-5-22下午03:32:31
     * 描述：将源字符串中的阿拉伯数字格式化为汉字
     * @param sign 源字符串中的字符
     * @return
     */
    public static char formatDigit(char sign){
        if(sign == '0')
            sign = '0';
        if(sign == '1')
            sign = '一';
        if(sign == '2')
            sign = '二';
        if(sign == '3')
            sign = '三';
        if(sign == '4')
            sign = '四';
        if(sign == '5')
            sign = '五';
        if(sign == '6')
            sign = '六';
        if(sign == '7')
            sign = '七';
        if(sign == '8')
            sign = '八';
        if(sign == '9')
            sign = '九';
        return sign;
    }

    /**
     * create date:2010-5-22下午03:31:51
     * 描述： 获得月份字符串的长度
     * @param str  待转换的源字符串
     * @param pos1 第一个'-'的位置
     * @param pos2 第二个'-'的位置
     * @return
     */
    public static int getMidLen(String str,int pos1,int pos2){
        return str.substring(pos1+1, pos2).length();
    }
    /**
     * create date:2010-5-22下午03:32:17
     * 描述：获得日期字符串的长度
     * @param str  待转换的源字符串
     * @param pos2 第二个'-'的位置
     * @return
     */
    public static int getLastLen(String str,int pos2){
        return str.substring(pos2+1).length();
    }

    /**
     * create date:2010-5-22下午03:40:50
     * 描述：取出日期字符串中的日字符串
     * @param str 日期字符串
     * @return
     */
    public static String getDayStr(String str)
    {
        String dayStr = "";
        int startIndex = str.indexOf("月");
        int endIndex = str.indexOf("日");
        dayStr = str.substring(startIndex+1,endIndex);
        return dayStr;
    }
    /**
     * create date:2010-5-22下午03:32:46
     * 描述：格式化日期
     * @param str 源字符串中的字符
     * @return
     */
    public static String formatStr(String str){
        StringBuffer sb = new StringBuffer();
        int pos1 = str.indexOf("-");
        int pos2 = str.lastIndexOf("-");
        for(int i = 0; i < 4; i++){
            sb.append(formatDigit(str.charAt(i)));
        }
        sb.append('年');
        if(getMidLen(str,pos1,pos2) == 1){
            sb.append(formatDigit(str.charAt(5))+"月");
            if(str.charAt(7) != '0'){
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(7))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(7) != '1' && str.charAt(8) != '0'){
                        sb.append(formatDigit(str.charAt(7))+"十"+formatDigit(str.charAt(8))+"日");
                    }
                    else if(str.charAt(7) != '1' && str.charAt(8) == '0'){
                        sb.append(formatDigit(str.charAt(7))+"十日");
                    }
                    else if(str.charAt(7) == '1' && str.charAt(8) != '0'){
                        sb.append("十"+formatDigit(str.charAt(8))+"日");
                    }
                    else{
                        sb.append("十日");
                    }
                }
            }
            else{
                sb.append(formatDigit(str.charAt(8))+"日");
            }
        }
        if(getMidLen(str,pos1,pos2) == 2){
            if(str.charAt(5) != '0' && str.charAt(6) != '0'){
                sb.append("十"+formatDigit(str.charAt(6))+"月");
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(8))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(8) != '0'){
                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
                        }
                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
                            sb.append(formatDigit(str.charAt(8))+"十日");
                        }
                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
                            sb.append("十"+formatDigit(str.charAt(9))+"日");
                        }
                        else{
                            sb.append("十日");
                        }
                    }
                    else{
                        sb.append(formatDigit(str.charAt(9))+"日");
                    }
                }
            }
            else if(str.charAt(5) != '0' && str.charAt(6) == '0'){
                sb.append("十月");
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(8))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(8) != '0'){
                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
                        }
                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
                            sb.append(formatDigit(str.charAt(8))+"十日");
                        }
                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
                            sb.append("十"+formatDigit(str.charAt(9))+"日");
                        }
                        else{
                            sb.append("十日");
                        }
                    }
                    else{
                        sb.append(formatDigit(str.charAt(9))+"日");
                    }
                }
            }
            else{
                sb.append(formatDigit(str.charAt(6))+"月");
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(8))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(8) != '0'){
                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
                        }
                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
                            sb.append(formatDigit(str.charAt(8))+"十日");
                        }
                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
                            sb.append("十"+formatDigit(str.charAt(9))+"日");
                        }
                        else{
                            sb.append("十日");
                        }
                    }
                    else{
                        sb.append(formatDigit(str.charAt(9))+"日");
                    }
                }
            }
        }
        return sb.toString();
    }
}
