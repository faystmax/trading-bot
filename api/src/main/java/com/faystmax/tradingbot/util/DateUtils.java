package com.faystmax.tradingbot.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

@UtilityClass
public class DateUtils {
    private static final String DEFAULT_PATTERN = "dd.MM.yyyy HH:mm:ss";

    public String format(final long mils) {
        return format(new Date(mils));
    }

    public String format(final Date date) {
        return DateFormatUtils.format(date, DEFAULT_PATTERN);
    }
}
