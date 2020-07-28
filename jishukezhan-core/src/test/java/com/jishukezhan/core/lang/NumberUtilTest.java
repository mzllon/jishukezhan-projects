package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class NumberUtilTest {

    @Test
    public void avoidScientificNotation() {
        String str = null;
        assertNull(NumberUtil.avoidScientificNotation(str));

        str = "";
        assertEquals(NumberUtil.avoidScientificNotation(str), "");

        str = "7.823E5";
        assertEquals(NumberUtil.avoidScientificNotation(str), "782300");

    }

    @Test
    public void isDigits() {
        String str = null;
        assertFalse(NumberUtil.isDigits(str));

        str = "";
        assertFalse(NumberUtil.isDigits(str));

        str = " ";
        assertFalse(NumberUtil.isDigits(str));

        str = "123";
        assertTrue(NumberUtil.isDigits(str));

        str = "123a";
        assertFalse(NumberUtil.isDigits(str));

        // 暂不支持科学计数法
        str = "7.82E2";
        assertFalse(NumberUtil.isDigits(str));
    }

    @Test
    public void isNumeric() {
        String str = null;
        assertFalse(NumberUtil.isNumeric(str));

        str = "";
        assertFalse(NumberUtil.isNumeric(str));

        str = " ";
        assertFalse(NumberUtil.isNumeric(str));

        str = "123";
        assertTrue(NumberUtil.isNumeric(str));

        str = "123a";
        assertFalse(NumberUtil.isNumeric(str));

        str = "3.13";
        assertTrue(NumberUtil.isNumeric(str));

    }

    @Test
    public void yuan2Fen1() {
        double yuan = 1.234;
        assertEquals(NumberUtil.yuan2Fen(yuan), 123);

        yuan = 1.236;
        assertEquals(NumberUtil.yuan2Fen(yuan), 124);
    }

    @Test
    public void fen2YuanString() {
        String fen = "0";
        assertEquals(NumberUtil.fen2YuanString(fen), "0.00");

        fen = "1234";
        assertEquals(NumberUtil.fen2YuanString(fen), "12.34");
    }

}
