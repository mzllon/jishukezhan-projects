package com.jishukezhan.core.date;

import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.lang.Preconditions;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 新老日期相互转化，支持如下转换：
 * Date,LocalDate,LocalDateTime,LocalTime,Instant,ZonedDateTime
 *
 * @author miles.tang
 */
public class DateConverter {

    /**
     * 将{@linkplain Date}转为{@linkplain LocalDate}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param date 日期
     * @return 返回JDK8日期对象
     */
    public static LocalDate toLocalDate(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        return to(date).toLocalDate();
    }

    /**
     * 将{@linkplain Date}转为{@linkplain LocalTime}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param date 日期
     * @return 返回JDK8时间对象
     */
    public static LocalTime toLocalTime(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        return to(date).toLocalTime();
    }

    /**
     * 毫秒数转日期时间对象{@linkplain LocalDateTime}
     *
     * @param epochMilliSeconds 毫秒数
     * @return 日期时间对象
     */
    public static LocalDateTime to(final long epochMilliSeconds) {
        Preconditions.checkArgument(epochMilliSeconds > 0, "epochSeconds > 0");
        long epochSeconds = epochMilliSeconds / 1000;
        long millisecond = epochMilliSeconds % 1000;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0,
                ZoneOffsetConstant.BEIJING_ZONE_OFFSET);
        localDateTime = localDateTime.plus(millisecond, ChronoUnit.MILLIS);
        return localDateTime;
    }

    /**
     * 将{@linkplain Date}转为{@linkplain LocalDateTime}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param date 日期
     * @return 返回JDK8日期时间对象
     */
    public static LocalDateTime to(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        return to(date, ZoneOffsetConstant.BEIJING_ZONE_OFFSET);
    }

    /**
     * 将{@linkplain Date}转为{@linkplain LocalDateTime}
     *
     * @param date   日期
     * @param zoneId 指定时区
     * @return 返回JDK8日期时间对象
     */
    public static LocalDateTime to(@Nullable Date date, @Nullable ZoneId zoneId) {
        if (date == null || zoneId == null) {
            return null;
        }
        return to(date.toInstant(), zoneId);
    }

    /**
     * 将{@linkplain Instant}转为{@linkplain LocalDateTime}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param instant 某一时刻
     * @return 返回JDK8日期时间对象
     */
    public static LocalDateTime to(@Nullable Instant instant) {
        return to(instant, ZoneOffsetConstant.BEIJING_ZONE_OFFSET);
    }

    /**
     * 将{@linkplain Instant}转为{@linkplain LocalDateTime}
     *
     * @param instant 某一时刻
     * @param zoneId  指定时区
     * @return 返回JDK8日期时间对象
     */
    public static LocalDateTime to(@Nullable Instant instant, @Nullable ZoneId zoneId) {
        if (instant == null || zoneId == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 将{@linkplain LocalDate}转为{@linkplain Instant},即将某一天的0点0分0秒那一刻
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param localDate 日期
     * @return 时刻
     */
    public static Instant to(@Nullable LocalDate localDate) {
        return to(localDate, ZoneOffsetConstant.BEIJING_ZONE_OFFSET);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param localDateTime 日期时间
     * @return 时刻
     */
    public static Instant to(@Nullable LocalDateTime localDateTime) {
        return to(localDateTime, ZoneOffsetConstant.BEIJING_ZONE_OFFSET);
    }

    /**
     * 将{@linkplain LocalDate}转为{@linkplain Instant},即将某一天的0点0分0秒那一刻
     *
     * @param localDate 日期
     * @param zoneId    指定时区
     * @return 时刻
     */
    public static Instant to(@Nullable LocalDate localDate, @Nullable ZoneId zoneId) {
        if (localDate == null || zoneId == null) {
            return null;
        }
        return to(localDate.atStartOfDay(), zoneId);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     *
     * @param localDateTime 日期时间
     * @param zoneId        指定时区
     * @return 时刻
     */
    public static Instant to(@Nullable LocalDateTime localDateTime, @Nullable ZoneId zoneId) {
        if (localDateTime == null || zoneId == null) {
            return null;
        }
        return localDateTime.atZone(zoneId).toInstant();
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Date}
     *
     * @param localDateTime 日期时间
     * @return 返回老的日期对象
     */
    public static Date from(@Nullable LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(to(localDateTime));
    }

    /**
     * 将{@linkplain LocalDate}转为{@linkplain Date}
     *
     * @param localDate 日期
     * @return 返回老的日期对象
     */
    public static Date from(@Nullable LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(to(localDate));
    }

}
