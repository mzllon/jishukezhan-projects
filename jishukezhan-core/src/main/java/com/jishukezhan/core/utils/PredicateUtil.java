package com.jishukezhan.core.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@linkplain Predicate}工具
 *
 * @author miles.tang
 */
public final class PredicateUtil {

    /**
     * 去重过滤器
     *
     * @param keyExtractor key extractor
     * @param <T>          泛型类型
     * @param <R>          返回类型
     * @return 过滤器
     */
    public static <T, R> Predicate<T> distinctByKey(Function<? super T, R> keyExtractor) {
        Set<R> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
