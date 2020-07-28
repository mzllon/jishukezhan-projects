package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

import java.util.function.Consumer;

import static org.testng.Assert.assertEquals;

public class TupleTest {

    @Test
    public void get() {
        Tuple tuple = new Tuple(1, "2", "String", false);
        assertEquals(tuple.getElements().length, 4);
        assertEquals(tuple.get(0), new Integer(1));
        assertEquals(tuple.get(1), "2");
        assertEquals(tuple.get(2), "String");
        assertEquals(tuple.get(3), Boolean.FALSE);
    }

    @Test
    public void iterator() {
        Tuple tuple = new Tuple(1, "2", "String", false);
        tuple.forEach(o -> System.out.println("o = " + o));
    }
}
