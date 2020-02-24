package com.faystmax.tradingbot.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DateUtils {
    private static final String DEFAULT_PATTERN = "dd.MM.yyyy HH:mm:ss";

    public static String format(long mils) {
        return format(new Date(mils));
    }

    public static String format(Date date) {
        return DateFormatUtils.format(date, DEFAULT_PATTERN);
    }
}
