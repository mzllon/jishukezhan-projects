package com.jishukezhan.core.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.WeakHashMap;

/**
 * @author miles.tang
 */
class CustomFormatterCache {

    private static final WeakHashMap<String, DateTimeFormatter> FORMATTER_CACHE = new WeakHashMap<>();

    /**
     * 创建Formatter并且缓存
     *
     * @param pattern 日期格式字符串
     * @return 返回Formatter
     */
    static DateTimeFormatter getOrCreateFormatter(String pattern) {
        DateTimeFormatter formatter;
        synchronized (FORMATTER_CACHE) {
            formatter = FORMATTER_CACHE.get(pattern);
        }
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(pattern);
            synchronized (FORMATTER_CACHE) {
                FORMATTER_CACHE.put(pattern, formatter);
            }
        }
        return formatter;
    }

    /**
     * 解析日期字符串
     *
     * @param text       日期字符串
     * @param srcPattern 解析格式
     * @param dstPattern 格式化格式
     * @return 日期
     */
    static String swapFormat(String text, String srcPattern, String dstPattern) {
        DateTimeFormatter srcFormatter = getOrCreateFormatter(srcPattern);
        TemporalAccessor temporalAccessor = srcFormatter.parse(text);
        DateTimeFormatter dstFormatter = getOrCreateFormatter(dstPattern);
        return dstFormatter.format(temporalAccessor);
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param localDate 日期
     * @param pattern   格式化规则
     * @return 格式化后的日期字符串
     */
    static String format(LocalDate localDate, String pattern) {
        return localDate.atStartOfDay().atOffset(ZoneOffsetConstant.BEIJING_ZONE_OFFSET)
                .format(getOrCreateFormatter(pattern));
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param localDateTime localDateTime
     * @param pattern       格式化规则
     * @return 格式化后的日期字符串
     */
    static String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime.atOffset(ZoneOffsetConstant.BEIJING_ZONE_OFFSET).format(getOrCreateFormatter(pattern));
    }

}
