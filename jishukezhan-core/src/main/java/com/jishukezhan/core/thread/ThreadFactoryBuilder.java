package com.jishukezhan.core.thread;

import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.core.support.Builder;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ThreadFactory Builder
 *
 * @author miles.tang
 */
public class ThreadFactoryBuilder implements Builder<ThreadFactory> {

    /**
     * 用于线程创建的线程工厂类
     */
    private ThreadFactory backingThreadFactory;

    /**
     * 线程名的前缀
     */
    private String namePrefix;

    /**
     * 是否守护线程，默认false
     */
    private Boolean daemon;

    /**
     * 线程优先级
     */
    private Integer priority;

    /**
     * 未捕获异常处理器
     */
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public ThreadFactoryBuilder() {
    }

    public ThreadFactoryBuilder backingThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = Preconditions.requireNonNull(backingThreadFactory);
        return this;
    }

    public ThreadFactoryBuilder namePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    public ThreadFactoryBuilder daemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadFactoryBuilder priority(int priority) {
        if (priority < Thread.MIN_PRIORITY) {
            throw new IllegalArgumentException(StringUtil.format("Thread priority[{}] must be >= {}", priority, Thread.MIN_PRIORITY));
        }
        if (priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException(StringUtil.format("Thread priority[{}] must be <= {}", priority, Thread.MAX_PRIORITY));
        }
        this.priority = priority;
        return this;
    }

    public ThreadFactoryBuilder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler) {
        this.uncaughtExceptionHandler = exceptionHandler;
        return this;
    }

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public ThreadFactory build() {
        return doBuild(this);
    }

    /**
     * 构建{@linkplain ThreadFactory}
     *
     * @param builder 构建参数
     * @return {@link ThreadFactory}
     */
    private static ThreadFactory doBuild(ThreadFactoryBuilder builder) {

        final String namePrefix = builder.namePrefix;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory = (builder.backingThreadFactory != null) ?
                builder.backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = (namePrefix == null) ? null : new AtomicLong(1);

        return (runnable) -> {
            final Thread newThread = backingThreadFactory.newThread(runnable);
            if (namePrefix != null) {
                newThread.setName(namePrefix + count.getAndIncrement());
            }
            if (daemon != null) {
                newThread.setDaemon(daemon);
            }
            if (priority != null) {
                newThread.setPriority(priority);
            }
            if (uncaughtExceptionHandler != null) {
                newThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }
            return newThread;
        };
    }

}
