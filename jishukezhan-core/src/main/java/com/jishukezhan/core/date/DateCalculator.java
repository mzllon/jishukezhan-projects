package com.jishukezhan.core.date;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.lang.Preconditions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 日期计算
 *
 * @author miles.tang
 */
public class DateCalculator {

    private transient LocalDateTime localDateTime;


    public DateCalculator(@NonNull Date date) {
        localDateTime = DateConverter.to(Preconditions.requireNonNull(date, "date == null"));
    }

    public DateCalculator(@NonNull LocalDateTime localDateTime) {
        this.localDateTime = Preconditions.requireNonNull(localDateTime, "localDateTime == null");
    }

    public DateCalculator(@NonNull LocalDate localDate) {
        localDateTime = Preconditions.requireNonNull(localDate, "localDate == null").atStartOfDay();
    }

    public DateCalculator(long epochMillis) {
        Preconditions.checkArgument(epochMillis > 0, "epochMillis <= 0");
        localDateTime = DateConverter.to(Instant.ofEpochMilli(epochMillis));
    }

    public DateCalculator(int year, int month, int day, int hour, int minute, int second) {
        localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    /**
     * 给指定日期加（减）年份数
     *
     * @return {@link Date}
     */
    public DateCalculator years(int years) {
        if (years > 0) {
            localDateTime = localDateTime.plusYears(years);
        } else if (years < 0) {
            localDateTime = localDateTime.minusYears(years);
        }
        return this;
    }

    /**
     * 给指定日期加（减）月数
     *
     * @param months 月，如果为正整数则添加，否则相减
     * @return {@link Date}
     */
    public DateCalculator months(int months) {
        if (months > 0) {
            localDateTime = localDateTime.plusMonths(months);
        } else if (months < 0) {
            localDateTime = localDateTime.minusMonths(months);
        }
        return this;
    }

    /**
     * 给指定日期加（减）天数
     *
     * @param days 天，如果为正整数则添加，否则相减
     * @return {@link Date}
     */
    public DateCalculator days(int days) {
        if (days > 0) {
            localDateTime = localDateTime.plusDays(days);
        } else if (days < 0) {
            localDateTime = localDateTime.minusDays(days);
        }
        return this;
    }

    /**
     * 给指定日期加（减）小时数
     *
     * @param hours 小时，如果为正整数则添加，否则相减
     * @return {@link Date}
     */
    public DateCalculator hours(int hours) {
        if (hours > 0) {
            localDateTime = localDateTime.plusHours(hours);
        } else if (hours < 0) {
            localDateTime = localDateTime.minusHours(hours);
        }
        return this;
    }

    /**
     * 给指定日期加（减）分钟数
     *
     * @param minutes 分钟，如果为正整数则添加，否则相减
     * @return {@link Date}
     */
    public DateCalculator minutes(int minutes) {
        if (minutes > 0) {
            localDateTime = localDateTime.plusMinutes(minutes);
        } else if (minutes < 0) {
            localDateTime = localDateTime.minusMinutes(minutes);
        }
        return this;
    }

    /**
     * 给指定日期加（减）秒数
     *
     * @param seconds 秒，如果为正整数则添加，否则相减
     * @return {@link Date}
     */
    public DateCalculator seconds(int seconds) {
        if (seconds > 0) {
            localDateTime = localDateTime.plusSeconds(seconds);
        } else if (seconds < 0) {
            localDateTime = localDateTime.minusSeconds(seconds);
        }
        return this;
    }

    public int getSecond() {
        return localDateTime.getSecond();
    }

    public int getMinute() {
        return localDateTime.getMinute();
    }

    public int getHour() {
        return localDateTime.getHour();
    }

    public int getDay() {
        return localDateTime.getDayOfMonth();
    }

    public int getMonthValue() {
        return localDateTime.getMonthValue();
    }

    public Month getMonth() {
        return Month.of(getMonthValue());
    }

    public Week getWeek() {
        return Week.of(localDateTime.getDayOfWeek().getValue());
    }

    public int getYear() {
        return localDateTime.getYear();
    }

    public Date toDate() {
        return DateConverter.from(localDateTime);
    }

    public LocalDate toLocalDate() {
        return toLocalDateTime().toLocalDate();
    }

    public LocalDateTime toLocalDateTime() {
        return localDateTime;
    }

    public String format(String pattern) {
        return DateUtil.format(localDateTime, pattern);
    }

    public boolean isAfter(@NonNull LocalDateTime other) {
        return localDateTime.isAfter(Preconditions.requireNonNull(other, "other == null"));
    }

    public boolean isBefore(@NonNull LocalDateTime other) {
        return localDateTime.isBefore(Preconditions.requireNonNull(other, "other == null"));
    }

}
