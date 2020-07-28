package com.jishukezhan.core.math;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BigDecimalCalculatorTest {

    @Test
    public void testAdd() {
        double result = BigDecimalCalculator.of(1)
                .add(2.161)
                .add(3.111f)
                .add(4.111d)
                .add(5L)
                .add("10")
                .getResultToDouble();
        assertEquals(result, 25.383);

    }

    @Test
    public void testSubtract() {
        String resultStr = BigDecimalCalculator.of(30.0d)
                .subtract(1)
                .subtract(2L)
                .subtract(3.311)
                .getResultStr();
        assertEquals(resultStr,"23.689");
    }

    @Test
    public void testMultiply() {
        String resultStr = BigDecimalCalculator.of(2)
                .multiply(3)
                .multiply(4)
                .getResultStr();
        assertEquals(resultStr, "24");

        resultStr = BigDecimalCalculator.of(1.1)
                .multiply(1.1)
                .multiply(1.1)
                .getResultStr();
        assertEquals(resultStr, "1.331");
    }

}
