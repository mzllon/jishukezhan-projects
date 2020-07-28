package com.jishukezhan.core.digest;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * https://tool.oschina.net/encrypt?type=2
 */
public class ShaUtilTest {

    @Test
    public void testSha1Hex() {
        String data = "SHA1散列";
        assertEquals(ShaUtil.sha1Hex(data), "a5ea14f15f27944ed666d67eda84f26b2e5c2921");
    }

    @Test
    public void testSha256Hex() {
        String data = "SHA256散列";
        assertEquals(ShaUtil.sha256Hex(data), "92ec3d8b519f13f9f3e97b2094ff0517e66e4066065a66daf084b2e08f9bc968");
    }

    @Test
    public void testSha384Hex() {
        String data = "SHA384散列";
        assertEquals(ShaUtil.sha384Hex(data), "f61456f6f8384e4a444ca22189a912b964b68442afe504b159f6cb650999f6e684262cb197501e218f7d9ef07a272380");
    }

    @Test
    public void testSha512Hex() {
        String data = "SHA512散列";
        assertEquals(ShaUtil.sha512Hex(data), "f46c33baf281a0bd26a65a8b65c3b16e31ba2d5f93318ea09972cb2b6671686959ec54fa7f187de3a26c934adf3c532a67f40b47d4a1092c76f3388fb9f8db09");
    }

}
