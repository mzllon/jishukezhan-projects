package com.jishukezhan.core.support;

/**
 * Builder Interface
 *
 * @param <T> Generic Type
 * @author miles.tang
 */
@FunctionalInterface
public interface Builder<T> {

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();

}
