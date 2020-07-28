package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class HexUtilTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void encodeToStrNull() {
        HexUtil.encodeToStr(null);
    }

    @Test
    public void encodeToStr() {
        // http://www.bejson.com/convert/ox2str/
        assertEquals(HexUtil.encodeToStr("Hex工具-这是一串有中文的字符串".getBytes()),
                "486578e5b7a5e585b72de8bf99e698afe4b880e4b8b2e69c89e4b8ade69687e79a84e5ad97e7aca6e4b8b2");
    }

    @Test
    public void encodeToStrUpper() {
        // http://convertstring.com/EncodeDecode/HexEncode

        String txt = "Hex工具-这是一串有中文的字符串";
        String hexData = HexUtil.encodeToStr(txt.getBytes(), false);
        String expected = "486578E5B7A5E585B72DE8BF99E698AFE4B880E4B8B2E69C89E4B8ADE69687E79A84E5AD97E7ACA6E4B8B2";
        assertNotNull(hexData);
        assertEquals(hexData, expected);
    }

    @Test
    public void decodeToStr() {
        String hexData = "2d4865785574696c546573742d";
        String originalTxt = HexUtil.decodeToStr(hexData);
        String expectedTxt = "-HexUtilTest-";
        assertNotNull(originalTxt);
        assertEquals(originalTxt, expectedTxt);
    }

    @Test
    public void testOtherEncode() {
        String str = "Hex工具-这是一串有中文的字符串";
        byte[] bytes = str.getBytes();
        BigInteger bigInteger = new BigInteger(bytes);
        assertEquals(String.format("%0" + (bytes.length << 1) + "X", bigInteger),
                "486578E5B7A5E585B72DE8BF99E698AFE4B880E4B8B2E69C89E4B8ADE69687E79A84E5AD97E7ACA6E4B8B2");
    }

}
