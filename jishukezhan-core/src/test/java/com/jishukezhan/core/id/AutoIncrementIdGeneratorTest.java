package com.jishukezhan.core.id;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AutoIncrementIdGeneratorTest {

    @Test
    public void get() {
        AutoIncrementIdGenerator idGenerator = new AutoIncrementIdGenerator();
        String id = idGenerator.get(10);
        System.out.println("id = " + id);
        assertEquals(id.length(), 10);
        assertEquals(id, "0000000000");
    }

}
