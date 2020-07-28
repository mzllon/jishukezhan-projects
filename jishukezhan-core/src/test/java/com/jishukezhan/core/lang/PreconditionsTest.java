package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PreconditionsTest {

    @Test
    public void testCheckArgument() {
    }

    @Test
    public void testTestCheckArgument() {
    }

    @Test
    public void testRequireNonNull() {
    }

    @Test
    public void testTestRequireNonNull() {
    }

    @Test
    public void testTestRequireNonNull1() {
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testRequireNotEmpty() {
        String str = "";
        Preconditions.requireNotEmpty(str);

    }

    @Test
    public void testRequireNotEmpty2() {
        String str = "124";
        assertEquals(Preconditions.requireNotEmpty(str), str);
    }

}