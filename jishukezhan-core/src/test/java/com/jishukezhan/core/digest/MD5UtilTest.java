package com.jishukezhan.core.digest;

import com.jishukezhan.core.lang.CharsetUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MD5UtilTest {

    @Test
    public void testDigestHex() {
        String data = "这是一串需要被MD5运算的字符串";
        assertEquals(MD5Util.digestHex(data),"f4dc6104a65b013d0ad53922f561ac7c");
    }

    @Test
    public void testDigestUpperHex() {
        String data = "这是一串需要被MD5运算的字符串";
        assertEquals(MD5Util.digestUpperHex(data),"F4DC6104A65B013D0AD53922F561AC7C");
    }

    @Test
    public void testDigestHexCharset() {
        String data = "这是一串需要被MD5运算的字符串";
        assertEquals(MD5Util.digestHex(data, CharsetUtil.GBK), "9b7b355368fd31da63dfc8cc82beb1dc");
    }

    @Test
    public void testDigestBase64() {
        String data = "这是一串需要被MD5运算的字符串";
        assertEquals(MD5Util.digestBase64(data), "9NxhBKZbAT0K1Tki9WGsfA==");
    }

}
