package com.jishukezhan.core.date;

import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;

public class DateCalculatorTest {

    @Test
    public void testSeconds() {
        Date date = DateUtil.ofDate(2020, 1, 30, 14, 0, 0);
        DateCalculator calculator = new DateCalculator(date).seconds(242);
        assertEquals(calculator.getSecond(), 2);
        assertEquals(calculator.getMinute(), 4);
    }

    @Test
    public void testMinutes() {
        Date date = DateUtil.ofDate(2020, 1, 30, 14, 0, 0);
        DateCalculator calculator = new DateCalculator(date).minutes(242);
        assertEquals(calculator.getSecond(), 0);
        assertEquals(calculator.getMinute(), 2);
        assertEquals(calculator.getHour(), 18);
    }

    @Test
    public void testHours() {
        Date date = DateUtil.ofDate(2020, 1, 30, 14, 0, 0);
        DateCalculator calculator = new DateCalculator(date).hours(242);// 10 + 24 * 9 + 16
        assertEquals(calculator.getSecond(), 0);
        assertEquals(calculator.getMinute(), 0);
        assertEquals(calculator.getHour(), 16);
        assertEquals(calculator.getDay(), 9);
        assertEquals(calculator.getMonthValue(), 2);
        assertEquals(calculator.getYear(), 2020);
    }

    @Test
    public void testDays() {
        Date date = DateUtil.ofDate(2020, 1, 30, 14, 0, 0);
        DateCalculator calculator = new DateCalculator(date).days(31);
        assertEquals(calculator.getHour(), 14);
        assertEquals(calculator.getDay(), 1);
        assertEquals(calculator.getMonthValue(), 3);
        assertEquals(calculator.getYear(), 2020);
    }

    @Test
    public void testMonths() {
        Date date = DateUtil.ofDate(2020, 1, 30, 14, 0, 0);
        DateCalculator calculator = new DateCalculator(date).months(1);
        assertEquals(calculator.getSecond(), 0);
        assertEquals(calculator.getMinute(), 0);
        assertEquals(calculator.getHour(), 14);
        assertEquals(calculator.getDay(), 29);
        assertEquals(calculator.getMonthValue(), 2);
        assertEquals(calculator.getYear(), 2020);
    }

    @Test
    public void testYears() {
    }

}
