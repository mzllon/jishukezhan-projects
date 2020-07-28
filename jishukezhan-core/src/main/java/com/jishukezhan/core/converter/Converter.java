package com.jishukezhan.core.converter;

import java.util.function.Function;

/**
 * 转换器接口
 *
 * @param <I> 输入参数类型
 * @param <O> 输出参数类型
 * @author miles.tang
 */
public interface Converter<I, O> extends Function<I, O> {
}
