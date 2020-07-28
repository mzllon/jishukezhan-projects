package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ObjectUtilTest {

    @Test
    public void testTestEquals() {
        byte[] src = new byte[]{35, 40, 42, 44};
        byte[] dst = new byte[]{35, 40, 42, 44};
        assertFalse(ObjectUtil.equals(src, dst));
        assertTrue(ObjectUtil.nullSafeEquals(src, dst));
    }

}
