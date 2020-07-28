package com.jishukezhan.core.utils;

import org.testng.annotations.Test;

public class ClassUtilTest {

    @Test
    public void testIsAssignable() {
    }

    @Test
    public void testIsInterface() {
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testGetRawType() {
        ClassUtil.getRawType(null);
    }

}
