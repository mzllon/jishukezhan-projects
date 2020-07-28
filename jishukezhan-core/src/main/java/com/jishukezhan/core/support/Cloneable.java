package com.jishukezhan.core.support;

import com.jishukezhan.core.exceptions.CloneRuntimeException;

/**
 * 克隆接口定义
 *
 * @param <T> 实现克隆接口的类型
 * @author miles.tang
 */
public interface Cloneable<T> extends java.lang.Cloneable {

    /**
     * 克隆当前对象，浅复制
     *
     * @return 克隆后的对象
     * @throws CloneRuntimeException 克隆时抛出的异常
     */
    T clone() throws CloneRuntimeException;

}
