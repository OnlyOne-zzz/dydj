package com.bestfeng.dydj.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public class TimeUtils {

    public static String timeStampToStr(long time) {
        return timeStampToStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String timeStampToStr(Long time, String pattern) {
        if (time == null) {
            return null;
        }
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(pattern);
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }
}
