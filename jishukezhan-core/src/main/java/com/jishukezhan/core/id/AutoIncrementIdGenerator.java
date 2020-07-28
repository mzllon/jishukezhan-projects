package com.jishukezhan.core.id;

import com.jishukezhan.core.lang.StringUtil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 实现ID自增长的生成器
 *
 * @author miles.tang
 */
public class AutoIncrementIdGenerator implements IdGenerator<Object> {

    private final AtomicLong val;

    public AutoIncrementIdGenerator() {
        this(0);
    }

    public AutoIncrementIdGenerator(long initVal) {
        this.val = new AtomicLong(initVal);
    }

    /**
     * 返回ID
     *
     * @param obj 参数
     * @return ID
     */
    @Override
    public String get(Object obj) {
        return Long.toString(val.getAndIncrement());
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        return get(null);
    }

    /**
     * 返回指定位数的ID，如果不够则左补零
     * 生成ID长度超出给定的长度，则返回{@code null}
     *
     * @param size ID长度
     * @return
     */
    public String get(int size) {
        long next = val.getAndIncrement();
        String id = Long.toString(next);
        if (size == id.length()) {
            return id;
        } else if (size < id.length()) {
            return StringUtil.EMPTY_STRING;
        }
        return String.format("%0" + size + "d", next);
    }

}
