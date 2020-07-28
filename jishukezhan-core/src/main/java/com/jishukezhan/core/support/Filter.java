package com.jishukezhan.core.support;

/**
 * Filter Api
 *
 * @param <E> Generic Type
 * @author miles.tang
 */
public interface Filter<E> {

    /**
     * 是否接受该对象
     *
     * @param ele 当前对象
     * @return 接受则返回{@code true},否则返回{@code false}
     */
    boolean accept(E ele);

}
