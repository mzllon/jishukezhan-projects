package com.jishukezhan.core.date;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.exceptions.DateRuntimeException;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Date;

/**
 * Date Utilities
 *
 * @author miles.tang
 */
public class DateUtil {

    private DateUtil() {
        throw new AssertionError("No com.jishukezhan.core.date.DateUtil instances for you!");
    }

    //region format date method

    /**
     * 格式化日期
     *
     * @param formatter 格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable DateTimeFormatter formatter) {
        if (formatter == null) {
            return null;
        }
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 格式化日期
     *
     * @param localDateTime 日期
     * @param formatter     格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable LocalDateTime localDateTime, @Nullable DateTimeFormatter formatter) {
        if (localDateTime == null || formatter == null) {
            return null;
        }
        return localDateTime.format(formatter);
    }

    /**
     * 格式化日期
     *
     * @param localDate 日期
     * @param formatter 格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable LocalDate localDate, @Nullable DateTimeFormatter formatter) {
        if (localDate == null || formatter == null) {
            return null;
        }
        return localDate.format(formatter);
    }

    /**
     * 格式化时间
     *
     * @param localTime 时间
     * @param formatter 格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable LocalTime localTime, @Nullable DateTimeFormatter formatter) {
        if (localTime == null || formatter == null) {
            return null;
        }
        return formatter.format(localTime);
    }

    /**
     * 格式化当前日期，格式化规则为：yyyy-MM-dd
     *
     * @return 格式化后的日期字符串
     */
    public static String formatToDate() {
        return format(LocalDate.now(), DatePattern.NORMAL_DATE_FORMATTER);
    }

    /**
     * 格式化当前时间，格式化规则为: yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的日期字符串
     */
    public static String formatToDateTime() {
        return format(LocalDateTime.now(), DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    /**
     * 格式化当前时间，格式化规则为: HH:mm:ss
     *
     * @return 格式化后的时间字符串
     */
    public static String formatToTime() {
        return format(LocalTime.now(), DatePattern.NORMAL_TIME_FORMATTER);
    }

    /**
     * 格式化指定{@code date}，格式化规则为: yyyy-MM-dd
     *
     * @param epochMilliSeconds 日期毫秒数
     * @return 日期字符串
     */
    public static String formatToDate(final long epochMilliSeconds) {
        return format(DateConverter.to(epochMilliSeconds), DatePattern.NORMAL_DATE_FORMATTER);
    }

    /**
     * 格式化指定{@code date}，格式化规则为: yyyy-MM-dd HH:mm:ss
     *
     * @param epochMilliSeconds 日期毫秒数
     * @return 日期字符串
     */
    public static String formatToDateTime(final long epochMilliSeconds) {
        return format(DateConverter.to(epochMilliSeconds), DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    /**
     * 格式化指定{@code date}，格式化规则为: yyyy-MM-dd
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatToDate(@Nullable final Date date) {
        return format(DateConverter.toLocalDate(date));
    }

    /**
     * 格式化指定{@code date}，格式化规则为: yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatToDateTime(@Nullable final Date date) {
        return format(DateConverter.to(date));
    }

    /**
     * 格式化当前时间，格式化规则为{@code pattern}
     *
     * @param pattern 格式化规则
     * @return 日期字符串
     */
    public static String format(@Nullable String pattern) {
        if (StringUtil.isEmpty(pattern)) {
            return null;
        }
        return CustomFormatterCache.format(LocalDateTime.now(), pattern);
    }

    /**
     * 格式化指定{@code date}，格式化规则为{@code pattern}
     *
     * @param epochMilliSeconds 毫秒数
     * @param pattern           格式化规则
     * @return 日期字符串
     */
    public static String format(final long epochMilliSeconds, final String pattern) {
        return CustomFormatterCache.format(DateConverter.to(epochMilliSeconds), pattern);
    }

    public static String format(final long epochMilliSeconds, @Nullable DateTimeFormatter formatter) {
        if (formatter == null) {
            return null;
        }
        LocalDateTime localDateTime = DateConverter.to(epochMilliSeconds);
        return localDateTime.format(formatter);
    }

    /**
     * 格式化指定{@code date}，格式化规则为{@code pattern}
     *
     * @param date    日期对象
     * @param pattern 格式化规则
     * @return 日期字符串
     */
    public static String format(@Nullable Date date, @Nullable String pattern) {
        if (date == null || StringUtil.isEmpty(pattern)) {
            return null;
        }
        return CustomFormatterCache.format(DateConverter.to(date), pattern);
    }

    /**
     * 转换日期格式,从{@code srcPattern}转为{@code dstPattern}<br>
     * 如将2013/01/01转为2013-01-01
     *
     * @param text        日期字符串
     * @param srcPattern  原始的格式化规则
     * @param destPattern 转换的日期格式化规则
     * @return 转换后的日期字符串
     */
    public static String swapFormat(@Nullable String text, @Nullable String srcPattern, @Nullable String destPattern) {
        if (StringUtil.isEmpty(text) || StringUtil.isEmpty(srcPattern) || StringUtil.isEmpty(destPattern)) {
            return null;
        }
        if (srcPattern.length() > text.length()) {
            throw new DateRuntimeException("SrcPattern '" + srcPattern + "' is long than text '" + text);
        }
        String newText = text;
        if (srcPattern.length() < text.length()) {
            // 尝试截取，但不保证截取正确
            newText = text.substring(0, srcPattern.length());
        }
        return CustomFormatterCache.swapFormat(newText, srcPattern, destPattern);
    }

    //endregion

    //region format localDate

    /**
     * 格式化日期,格式化规则为: yyyy-MM-dd
     *
     * @param localDate 日期
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return format(localDate, DatePattern.NORMAL_DATE_FORMATTER);
    }

    /**
     * 格式化日期
     *
     * @param localDate 日期
     * @param pattern   格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable LocalDate localDate, @Nullable String pattern) {
        if (localDate == null || StringUtil.isEmpty(pattern)) {
            return null;
        }
        return CustomFormatterCache.format(localDate, pattern);
    }

    /**
     * 格式化日期,格式化规则为: yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime 日期
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable LocalDateTime localDateTime) {
        return format(localDateTime, DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    /**
     * 格式化日期
     *
     * @param localDateTime 日期
     * @param pattern       格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(@Nullable LocalDateTime localDateTime, @Nullable String pattern) {
        if (localDateTime == null || StringUtil.isEmpty(pattern)) {
            return null;
        }
        return CustomFormatterCache.format(localDateTime, pattern);
    }

    //endregion

    //region parse method

    /**
     * 解析日期字符串,解析规则: yyyy-MM-dd
     *
     * @param strDate 日期字符串
     * @return 解析后的日期
     */
    public static Date parseToDate(@Nullable String strDate) {
        return DateConverter.from(parseToLocalDate(strDate));
    }

    /**
     * 解析日期字符串,解析规则: yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 日期字符串
     * @return 解析后的日期
     */
    public static Date parseToDateTime(@Nullable String strDate) {
        return DateConverter.from(parseToLocalDateTime(strDate));
    }

    /**
     * 解析日期字符串,按照指定解析规则转为{@linkplain Date}
     * <p>{@code java.util.Date}已经是不推荐使用的日期类了，建议新的模块优先采用{@code java.time.*}下的日期类</p>
     *
     * @param strDate 日期字符串
     * @param pattern 解析规则
     * @return 日期对象
     */
    public static Date parseToJUDate(final String strDate, final String pattern) {
        if (StringUtil.isAnyEmpty(strDate, pattern)) {
            return null;
        }
        if (pattern.length() > strDate.length()) {
            throw new DateRuntimeException("Pattern '" + pattern + "' is long than text '" + strDate + "'");
        }
        DateTimeFormatter formatter = CustomFormatterCache.getOrCreateFormatter(pattern);
        TemporalAccessor temporalAccessor = formatter.parse(strDate);

        LocalDate localDate;
        try {
            int year = temporalAccessor.get(ChronoField.YEAR);
            int month = temporalAccessor.get(ChronoField.MONTH_OF_YEAR);
            int day = temporalAccessor.get(ChronoField.DAY_OF_MONTH);
            localDate = LocalDate.of(year, month, day);
        } catch (UnsupportedTemporalTypeException e) {
            throw new DateRuntimeException(e);
        }

        int hour = -1, minute = 0, second = 0, nanoSecond = 0;
        LocalTime localTime = null;
        try {
            hour = temporalAccessor.get(ChronoField.HOUR_OF_DAY);
            minute = temporalAccessor.get(ChronoField.MINUTE_OF_HOUR);
            second = temporalAccessor.get(ChronoField.SECOND_OF_MINUTE);
            nanoSecond = temporalAccessor.get(ChronoField.NANO_OF_SECOND);
        } catch (UnsupportedTemporalTypeException e) {
            throw new DateRuntimeException(e);
        }
        if (hour >= 0) {
            localTime = LocalTime.of(hour, minute, second, nanoSecond);
        }

        if (localTime == null) {
            return DateConverter.from(localDate);
        } else {
            return DateConverter.from(LocalDateTime.of(localDate, localTime));
        }
    }

    /**
     * 解析日期字符串,解析规则: yyyy-MM-dd
     *
     * @param strDate 日期字符串
     * @return 解析后的日期
     */
    public static LocalDate parseToLocalDate(@Nullable String strDate) {
        return parseToLocalDate(strDate, DatePattern.SMART_NORMAL_DATE_FORMATTER);
    }

    /**
     * 解析日期字符串
     *
     * @param strDate 日期字符串
     * @param pattern 自定义日期格式
     * @return 解析后的日期
     */
    public static LocalDate parseToLocalDate(@Nullable String strDate, @NonNull String pattern) {
        if (StringUtil.isAnyEmpty(strDate, pattern)) {
            return null;
        }
        DateTimeFormatter formatter = CustomFormatterCache.getOrCreateFormatter(pattern);
        return parseToLocalDate(strDate, formatter);
    }

    /**
     * 解析日期字符串,解析规则: yyyy-MM-dd
     *
     * @param strDate 日期字符串
     * @return 解析后的日期
     */
    public static LocalDate parseToLocalDate(@Nullable String strDate, @NonNull DateTimeFormatter formatter) {
        if (StringUtil.isEmpty(strDate) || formatter == null) {
            return null;
        }
        return LocalDate.parse(strDate, formatter);
    }

    /**
     * 解析日期字符串,解析规则: yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 日期字符串
     * @return 解析后的日期
     */
    public static LocalDateTime parseToLocalDateTime(@Nullable String strDate) {
        return parseToLocalDateTime(strDate, DatePattern.SMART_NORMAL_DATETIME_FORMATTER);
    }

    /**
     * 解析日期字符串
     *
     * @param strDate 日期字符串
     * @param pattern 自定义日期格式
     * @return 解析后的日期
     */
    public static LocalDateTime parseToLocalDateTime(@Nullable String strDate, @Nullable String pattern) {
        if (StringUtil.isAnyEmpty(strDate, pattern)) {
            return null;
        }
        DateTimeFormatter formatter = CustomFormatterCache.getOrCreateFormatter(pattern);
        return parseToLocalDateTime(strDate, formatter);
    }

    /**
     * 解析日期字符串
     *
     * @param strDate   日期字符串
     * @param formatter 自定义日期格式
     * @return 解析后的日期
     */
    public static LocalDateTime parseToLocalDateTime(@Nullable String strDate, @Nullable DateTimeFormatter formatter) {
        if (StringUtil.isEmpty(strDate) || formatter == null) {
            return null;
        }
        return LocalDateTime.parse(strDate, formatter);
    }


    /**
     * 根据年月日创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @return 日期对象
     */
    public static Date ofDate(final int year, final int month, final int dayOfMonth) {
        return ofDate(year, month, dayOfMonth, 0);
    }

    /**
     * 根据年月日时创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @param hour       小时
     * @return 日期对象
     */
    public static Date ofDate(final int year, final int month, final int dayOfMonth, final int hour) {
        return ofDate(year, month, dayOfMonth, hour, 0);
    }

    /**
     * 根据年月日时分创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @param hour       小时
     * @param minute     分
     * @return 日期对象
     */
    public static Date ofDate(final int year, final int month, final int dayOfMonth,
                              final int hour, final int minute) {
        return ofDate(year, month, dayOfMonth, hour, minute, 0);
    }

    /**
     * 根据年月日时分秒创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @param hour       小时
     * @param minute     分
     * @param second     秒
     * @return 日期对象
     */
    public static Date ofDate(final int year, final int month, final int dayOfMonth,
                              final int hour, final int minute, final int second) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        return DateConverter.from(localDateTime);
    }

    //endregion

    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateCalcUtils.isLeapYear(date); false
     * </pre>
     *
     * @param date 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(final Date date) {
        int year = getYear(date);

        //世纪闰年:能被400整除的为世纪闰年
        if (year % 400 == 0) {
            return true;
        }
        //普通闰年:能被4整除但不能被100整除的年份为普通闰年。
        return year % 4 == 0 && year % 100 != 0;
    }

    /**
     * 从日期中获取年份
     * <pre>
     *     比如时间2014-05-12 12:10:00  DateCalcUtils.getYear(date); 2014
     * </pre>
     *
     * @param date 日期对象
     * @return 年份
     */
    public static int getYear(@NonNull Date date) {
        LocalDateTime localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
        return localDateTime.getYear();
    }

    /**
     * 从日期中获取月份
     * <pre>
     *     比如时间2014-05-12 12:10:00  DateCalcUtils.getMonth(date); 5
     * </pre>
     *
     * @param date 日期对象
     * @return 月份
     */
    public static int getMonth(@NonNull Date date) {
        LocalDateTime localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
        return localDateTime.getMonthValue();
    }

    /**
     * 从日期中获取天
     * <pre>
     *     比如时间2014-05-12 12:10:00  DateCalcUtils.getDay(date); 12
     * </pre>
     *
     * @param date 日期对象
     * @return 天
     */
    public static int getDay(@NonNull Date date) {
        LocalDateTime localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
        return localDateTime.getDayOfMonth();
    }

    /**
     * 从日期中获取小时（24制）
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateCalcUtils.get24Hour(date); 22
     * </pre>
     *
     * @param date 日期对象
     * @return 小时（24制）
     */
    public static int get24Hour(@NonNull Date date) {
        LocalDateTime localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
        return localDateTime.getHour();
    }

    /**
     * 从日期中获取小时（12制）
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateCalcUtils.get12Hour(date); 10
     * </pre>
     *
     * @param date 日期对象
     * @return 小时（12制）
     */
    public static int get12Hour(@NonNull Date date) {
        return get24Hour(Preconditions.requireNonNull(date, "date == null")) % 12;
    }

    /**
     * 从日期中获取分钟
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateCalcUtils.getMinute(date); 10
     * </pre>
     *
     * @param date 日期对象
     * @return 分钟
     */
    public static int getMinute(@NonNull Date date) {
        LocalDateTime localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
        return localDateTime.getMinute();
    }

    /**
     * 从日期中获取秒数
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateCalcUtils.getSecond(date); 0
     * </pre>
     *
     * @param date 日期对象
     * @return 秒数
     */
    public static int getSecond(@NonNull Date date) {
        LocalDateTime localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
        return localDateTime.getSecond();
    }

    public static int getMilliSecond(@NonNull Date date) {
        LocalDateTime localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
        return localDateTime.get(ChronoField.MILLI_OF_SECOND);
    }

}
