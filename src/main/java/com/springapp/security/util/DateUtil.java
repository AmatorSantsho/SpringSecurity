package com.springapp.security.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 123 on 28.01.2018.
 */
public class DateUtil {
    public static final String DATE_PATTERN="yyyy-MM-dd HH:mm";

    public static SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DATE_PATTERN);
    public static String converteDateToString(Date date){
        return simpleDateFormat.format(date);
    }
    public static Date converteStringToDate(String date){
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
