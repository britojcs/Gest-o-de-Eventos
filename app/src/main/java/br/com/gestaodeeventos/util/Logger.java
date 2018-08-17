package br.com.gestaodeeventos.util;

import android.util.Log;

public class Logger {

    public static final Boolean DEBUG = true;

    public static void i(String msg) {
        if (DEBUG)
            Log.println(Log.INFO, getTag(), msg);
    }

    public static void e(String msg) {
        if (DEBUG)
            Log.println(Log.ERROR, getTag(), msg);
    }

    private static String getTag() {
        String className = new Exception().getStackTrace()[2].getClassName();
        return className.substring(1 + className.lastIndexOf('.'));
    }

}