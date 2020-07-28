package com.jishukezhan.core.date;

import org.testng.annotations.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DateUtilTest {

    @Test
    public void testSmart() {
        String text = "1990-1-1";
        LocalDate expectedDate = LocalDate.of(1990, 1, 1);
        LocalDate actualDate = LocalDate.parse(text, DatePattern.SMART_NORMAL_DATE_FORMATTER);
        assertEquals(actualDate, expectedDate);
        actualDate = DateUtil.parseToLocalDate(text);
        System.out.println("actualDate = " + actualDate);
        assertEquals(actualDate, expectedDate);

        text = "1990-1-10";
        expectedDate = LocalDate.of(1990, 1, 10);
        actualDate = LocalDate.parse(text, DatePattern.SMART_NORMAL_DATE_FORMATTER);
        assertEquals(actualDate, expectedDate);
        actualDate = DateUtil.parseToLocalDate(text);
        System.out.println("actualDate = " + actualDate);
        assertEquals(actualDate, expectedDate);

        text = "1990-10-10";
        expectedDate = LocalDate.of(1990, 10, 10);
        actualDate = LocalDate.parse(text, DatePattern.SMART_NORMAL_DATE_FORMATTER);
        assertEquals(actualDate, expectedDate);
        actualDate = DateUtil.parseToLocalDate(text);
        System.out.println("actualDate = " + actualDate);
        assertEquals(actualDate, expectedDate);

        text = "1990-10-1";
        expectedDate = LocalDate.of(1990, 10, 1);
        actualDate = LocalDate.parse(text, DatePattern.SMART_NORMAL_DATE_FORMATTER);
        assertEquals(actualDate, expectedDate);
        actualDate = DateUtil.parseToLocalDate(text);
        System.out.println("actualDate = " + actualDate);
        assertEquals(actualDate, expectedDate);

        text = "1990-01-1";
        expectedDate = LocalDate.of(1990, 1, 1);
        actualDate = LocalDate.parse(text, DatePattern.SMART_NORMAL_DATE_FORMATTER);
        assertEquals(actualDate, expectedDate);
        actualDate = DateUtil.parseToLocalDate(text);
        System.out.println("actualDate = " + actualDate);
        assertEquals(actualDate, expectedDate);

        System.out.println("=================");

        text = "2:3";
        LocalTime expectedTime = LocalTime.of(2, 3);
        LocalTime actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        assertEquals(actualTime, expectedTime);
        System.out.println("actualTime = " + actualTime);

        text = "2:30";
        expectedTime = LocalTime.of(2, 30);
        actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        System.out.println("actualTime = " + actualTime);
        assertEquals(actualTime, expectedTime);

        text = "12:30";
        expectedTime = LocalTime.of(12, 30);
        actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        System.out.println("actualTime = " + actualTime);
        assertEquals(actualTime, expectedTime);

        text = "12:3";
        expectedTime = LocalTime.of(12, 3);
        actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        System.out.println("actualTime = " + actualTime);
        assertEquals(actualTime, expectedTime);

        text = "12:03";
        expectedTime = LocalTime.of(12, 3);
        actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        System.out.println("actualTime = " + actualTime);
        assertEquals(actualTime, expectedTime);

        text = "12:03:1";
        expectedTime = LocalTime.of(12, 3, 1);
        actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        System.out.println("actualTime = " + actualTime);
        assertEquals(actualTime, expectedTime);

        text = "12:03:11";
        expectedTime = LocalTime.of(12, 3, 11);
        actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        System.out.println("actualTime = " + actualTime);
        assertEquals(actualTime, expectedTime);

        text = "2:30";
        expectedTime = LocalTime.of(2, 30);
        actualTime = LocalTime.parse(text, DatePattern.SMART_NORMAL_TIME_FORMATTER);
        System.out.println("actualTime = " + actualTime);
        assertEquals(actualTime, expectedTime);

        System.out.println("----------------");

        text = "1990-1-1 12:01";
        LocalDateTime expectDateTime = LocalDateTime.of(1990, 1, 1, 12, 1);
        LocalDateTime actualDateTime = LocalDateTime.parse(text, DatePattern.SMART_NORMAL_FORMATTER);
        assertEquals(actualDateTime, expectDateTime);
        System.out.println("actualDateTime = " + actualDateTime);

        text = "1990-1-1 12:01:11";
        expectDateTime = LocalDateTime.of(1990, 1, 1, 12, 1, 11);
        actualDateTime = LocalDateTime.parse(text, DatePattern.SMART_NORMAL_FORMATTER);
        assertEquals(actualDateTime, expectDateTime);
        actualDateTime = DateUtil.parseToLocalDateTime(text);
        System.out.println("actualDateTime = " + actualDateTime);
        assertEquals(actualDateTime, expectDateTime);

        text = "1990-1-1 12:01:11.666";
        expectDateTime = LocalDateTime.of(1990, 1, 1, 12, 1, 11);
        expectDateTime = expectDateTime.plus(666, ChronoUnit.MILLIS);
        actualDateTime = LocalDateTime.parse(text, DatePattern.SMART_NORMAL_FORMATTER);
        assertEquals(actualDateTime, expectDateTime);
        System.out.println("actualDateTime = " + actualDateTime);

    }

    @Test
    public void testUtc() {
        OffsetDateTime now = OffsetDateTime.now();
        String text = DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER.format(now);
        System.out.println(text);
    }

    @Test
    public void formatToDate() {
//        LocalDate now = LocalDate.now();
//        String formatToDate = DateUtil.formatToDate();
//        assertEquals(formatToDate, now.toString());

//        String str = "1990/1/1";
//        String str =  "1990/1/10";
//        String str =  "1990/10/1";
//        String str =  "1990/10/10";
//        String str =  "1990-10-10 12:10";
//        String str =  "1990-10-1 12:10";
////        String str =  "1990-1-10";
////        String str =  "1990-1-1";
////        String str = "20200101";
//
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d[[ hh][:mm][:ss]]");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
//        System.out.println(LocalDate.parse(str, formatter));

        String timeStr = "11:4";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
        System.out.println(LocalTime.parse(timeStr, formatter));
    }

    @Test
    public void formatToDateTime() {
        LocalDateTime now = LocalDateTime.now();
        String formatToDateTime = DateUtil.formatToDateTime();
        int year = now.getYear(),
                month = now.getMonthValue(),
                dayOfMonth = now.getDayOfMonth(),
                hour = now.getHour(),
                minute = now.getMinute(),
                second = now.getSecond();
        String expectedFormat = year +
                (month < 10 ? "-0" : "-") + month +
                (dayOfMonth < 10 ? "-0" : "-") + dayOfMonth +
                (hour < 10 ? " 0" : " ") + hour +
                (minute < 10 ? ":0" : ":") + minute +
                (second < 10 ? ":0" : ":") + second;
        assertEquals(formatToDateTime, expectedFormat);
    }

    @Test
    public void formatToDateForLong() {
        Date date = DateUtil.ofDate(2018, 2, 28);
        String formatToDate = DateUtil.formatToDate(date.getTime());
        assertEquals(formatToDate, "2018-02-28");
    }

    @Test
    public void formatToDateTimeForLong() {
        Date date = DateUtil.ofDate(2018, 2, 28, 18, 0, 0);
        String formatToDate = DateUtil.formatToDateTime(date.getTime());
        assertEquals(formatToDate, "2018-02-28 18:00:00");

    }

    @Test
    public void formatPatternString() {
        String pattern = "yyyy/MM/dd";
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear(),
                month = now.getMonthValue(),
                dayOfMonth = now.getDayOfMonth();
        String format = DateUtil.format(pattern);
        String expectedFormat = year +
                (month < 10 ? "/0" : "/") + month +
                (dayOfMonth < 10 ? "/0" : "/") + dayOfMonth;

        assertEquals(format, expectedFormat);
    }

    @Test
    public void formatToDateForDate() {
        Date date = DateUtil.ofDate(2018, 2, 28, 18, 0, 0);
        String format = DateUtil.formatToDate(date);
        assertEquals(format, "2018-02-28");
    }

    @Test
    public void formatToDateTimeForDate() {
        Date date = DateUtil.ofDate(2018, 2, 28, 18, 0, 0);
        String format = DateUtil.formatToDateTime(date);
        assertEquals(format, "2018-02-28 18:00:00");
    }

    @Test
    public void formatToTime() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear(),
                month = now.getMonthValue(),
                dayOfMonth = now.getDayOfMonth(),
                hour = now.getHour(),
                minute = now.getMinute(),
                second = now.getSecond();
        String expectedFormat =
                (hour < 10 ? "0" : "") + hour +
                        (minute < 10 ? ":0" : ":") + minute +
                        (second < 10 ? ":0" : ":") + second;
        String formatToTime = DateUtil.formatToTime();
        assertEquals(formatToTime, expectedFormat);
    }

    @Test
    public void parseToDate() {
        final String strDate = "2019-01-01";
        Date parseFromDate = DateUtil.parseToDate(strDate);

        Date date = DateUtil.ofDate(2019, 1, 1);
        assertEquals(parseFromDate, date);
    }

    @Test
    public void parseToDateTime() {
        final String strDate = "2019-01-01 23:59:59";
        Date parseFromDate = DateUtil.parseToDateTime(strDate);

        Date date = DateUtil.ofDate(2019, 1, 1, 23, 59, 59);
        assertEquals(parseFromDate, date);
    }

    @Test(expectedExceptions = DateTimeParseException.class)
    public void parseToLocalDateTime() {
        final String strDate = "2019/04/10 12:00:00";
        final String pattern = "yyyy-MM-dd";
        DateUtil.parseToLocalDateTime(strDate, pattern);
    }

    @Test
    public void parseToJUDate() {
        final String strDate = "2019/04/10 12:00:00";
        final String pattern = "yyyy/MM/dd HH:mm:ss";
        Date parse = DateUtil.parseToJUDate(strDate, pattern);
        Date date = DateUtil.ofDate(2019, 4, 10, 12, 0, 0);
        assertEquals(parse, date);
    }

    @Test
    public void swapDate() {
        final String strDate = "2019/04/10 12:00:00";
        final String pattern = "yyyy/MM/dd HH:mm:ss";
        final String destPattern = "yyyy-MM-dd";
        assertEquals(DateUtil.swapFormat(strDate, pattern, destPattern), "2019-04-10");
    }

    @Test
    public void get() {
        Date date = DateUtil.ofDate(2020, 1, 30, 14, 10, 0);
        assertEquals(DateUtil.getYear(date), 2020);
        assertEquals(DateUtil.getMonth(date), 1);
        assertEquals(DateUtil.getDay(date), 30);
        assertEquals(DateUtil.get24Hour(date), 14);
        assertEquals(DateUtil.get12Hour(date), 2);
        assertEquals(DateUtil.getMinute(date), 10);
        assertEquals(DateUtil.getSecond(date), 0);
        assertTrue(DateUtil.isLeapYear(date));
    }

    @Test
    public void test() {
        String text = "2020-04-30T23:59:59+08:00";
        System.out.println(OffsetDateTime.parse(text,DateTimeFormatter.ISO_ZONED_DATE_TIME).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        System.out.println(ZonedDateTime.of(LocalDateTime.of(2020, 4, 16, 10, 10, 10), ZoneOffsetConstant.BEIJING_ZONE_OFFSET)
        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }

}
