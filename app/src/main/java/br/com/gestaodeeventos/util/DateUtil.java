package br.com.gestaodeeventos.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

    static SimpleDateFormat sdfApi1 = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdfApi2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    static SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
    static SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    public static String formatDateShort(String date) {
        try {
            Date d = sdfApi1.parse(date);
            return sdf1.format(d);
        } catch (Exception ex) {
            return "-";
        }
    }

    public static String formatDateLong(String date) {
        try {
            Date d = sdfApi2.parse(date);
            return sdf2.format(d);
        } catch (Exception ex) {
            return "-";
        }
    }

}
