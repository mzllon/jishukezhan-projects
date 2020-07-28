package com.jishukezhan.core.id;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UuidGeneratorTest {

    @Test
    public void get() {
        UuidGenerator uuidGenerator = new UuidGenerator();
        String id = uuidGenerator.get();
        System.out.println("UuidGenerator = " + id);
        assertEquals(id.length(), 36);
        uuidGenerator = new UuidGenerator(true);
        id=uuidGenerator.get();
        System.out.println("UuidGenerator = " + id);
        assertEquals(id.length(), 32);
    }

}
