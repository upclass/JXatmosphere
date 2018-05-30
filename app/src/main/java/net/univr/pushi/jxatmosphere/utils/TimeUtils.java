package net.univr.pushi.jxatmosphere.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/16
 * desc   :
 * version: 1.0
 */


public class TimeUtils {

    //       将时间字符串转换为时间戳
    public static long dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    //将时间戳转化为时间
    public static Date stampToDate(long s) {
        Date date = new Date(s);
        return date;
    }

    //将时间字符串转化为时间
    public static Date ToDate(String s) {
        Date dateTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateTime = simpleDateFormat.parse(s);
        } catch (ParseException e) {

        }
        return dateTime;
    }
}
