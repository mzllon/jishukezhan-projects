package com.jishukezhan.core.converter;

import com.jishukezhan.core.lang.NumberUtil;

/**
 * 字节转换器
 *
 * @author miles.tang
 */
public class ByteConverter implements Converter<Object, Byte> {

    private static final IntegerConverter integerConverter = IntegerConverter.getInstance();

    private static class Holder {
        static final ByteConverter INSTANCE = new ByteConverter();
    }

    public static ByteConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Byte apply(Object input) {
        Integer output = integerConverter.apply(input);
        if (output == null) {
            return null;
        }
        return NumberUtil.convert(output, Byte.class);
    }

    @Override
    public String toString() {
        return "ByteConverter";
    }

}
