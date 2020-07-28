package com.jishukezhan.core.thread;

import java.util.concurrent.TimeUnit;

/**
 * Thread Utilities
 *
 * @author miles.tang
 */
public class ThreadUtil {

    /**
     * 使当前线程挂起（睡一会儿）
     *
     * @param millis 挂起的毫秒数
     * @return 被中断返回{@code false},否则返回{@code true}
     */
    public static boolean sleep(Number millis) {
        if (millis == null) {
            return true;
        }
        try {
            Thread.sleep(millis.longValue());
            return true;
        } catch (InterruptedException e) {
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * 使当前线程挂起（睡一会儿）
     *
     * @param seconds 挂起的秒数
     * @return 被中断返回{@code false},否则返回{@code true}
     */
    public static boolean sleepSeconds(Number seconds) {
        if (seconds == null) {
            return true;
        }
        return sleep(seconds.longValue() * 1000);
    }

    /**
     * 使当前线程挂起（睡一会儿）
     *
     * @param timeout  挂起的时长
     * @param timeUnit 时长单位
     * @return 被中断返回{@code false},否则返回{@code true}
     */
    public static boolean sleep(Number timeout, TimeUnit timeUnit) {
        if (timeout == null || timeUnit == null) {
            return true;
        }
        try {
            timeUnit.sleep(timeout.longValue());
            return true;
        } catch (InterruptedException e) {
            //e.printStackTrace();
            return false;
        }
    }

}
