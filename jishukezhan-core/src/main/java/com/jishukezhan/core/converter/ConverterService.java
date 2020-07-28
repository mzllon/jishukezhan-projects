package com.jishukezhan.core.converter;

import com.jishukezhan.core.exceptions.ValueConvertRuntimeException;
import com.jishukezhan.core.lang.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 转换器服务
 *
 * @author miles.tang
 */
public class ConverterService {

    private static final Map<Class<?>, Converter<Object, ?>> BUILTIN = new HashMap<>();

    static {
        BUILTIN.put(Byte.class, ByteConverter.getInstance());
        BUILTIN.put(byte.class, ByteConverter.getInstance());
        BUILTIN.put(Short.class, ShortConverter.getInstance());
        BUILTIN.put(short.class, ShortConverter.getInstance());
        BUILTIN.put(Integer.class, IntegerConverter.getInstance());
        BUILTIN.put(int.class, IntegerConverter.getInstance());
        BUILTIN.put(Long.class, LongConverter.getInstance());
        BUILTIN.put(long.class, LongConverter.getInstance());
        BUILTIN.put(Float.class, FloatConverter.getInstance());
        BUILTIN.put(float.class, FloatConverter.getInstance());
        BUILTIN.put(Double.class, DoubleConverter.getInstance());
        BUILTIN.put(double.class, DoubleConverter.getInstance());
        BUILTIN.put(boolean.class, BooleanConverter.getInstance());
        BUILTIN.put(Boolean.class, BooleanConverter.getInstance());
    }

    private static class Holder {
        static final ConverterService INSTANCE = new ConverterService();
    }

    private ConverterService() {

    }

    private final Map<Class<?>, Converter<Object, ?>> registry = new ConcurrentHashMap<>(BUILTIN);

    /**
     * 注册一个类型转换器
     *
     * @param clazz     类型
     * @param converter 对应的转换器
     */
    public void register(Class<?> clazz, Converter<Object, ?> converter) {
        registry.put(clazz, converter);
    }

    /**
     * 将原对象转为目标类型的对象
     *
     * @param obj         源对象
     * @param targetClass 目标对象类型
     * @param <T>         泛型类
     * @return 目标对象
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(Object obj, Class<T> targetClass) {
        Converter<Object, ?> converter = registry.get(targetClass);
        if (converter == null) {
            throw new ValueConvertRuntimeException(StringUtil.format("Can't cast {} to {}", obj, targetClass));
        }
        return (T) converter.apply(obj);
    }

    public static ConverterService getInstance() {
        return Holder.INSTANCE;
    }

}
