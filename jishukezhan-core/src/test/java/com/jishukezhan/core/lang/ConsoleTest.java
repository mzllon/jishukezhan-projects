package com.jishukezhan.core.lang;

import org.testng.annotations.Test;

/**
 *
 */
public class ConsoleTest {

    @Test
    public void log() {
        Console.log();
        Console.log(new Object());
        Console.log("Author:{},Version:{},Expired:{}", "Tony", "2.0.0");
    }

    @Test
    public void print() {
        Console.print("Author:{},Version:{},Expired:{}", "Tony", "2.0.0");
    }

    @Test
    public void error() {
        Console.error();
        Console.error(new Object());
        Console.error("Author:{},Version:{},Expired:{}", "Tony", "2.0.0");
        NullPointerException npe = new NullPointerException("Throw by Program");
        Console.error(npe, "This is a {}", "只是一个演示错误");
    }

}
