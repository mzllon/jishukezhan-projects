package com.jishukezhan.core.id;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DateIdGeneratorTest {

    @Test
    public void get() {
        DateIdGenerator idGenerator = new DateIdGenerator();
        System.out.println("DateIdGenerator = " + idGenerator.get());
    }

}
