package com.blazedemo.utils;

public class TimeManager {
    //screenshots - logs - reports
    public static String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
    }

    //unique timestamps for each data
    public static String getSimpleTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }

}
