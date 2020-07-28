package com.jishukezhan.core.converter;

import com.jishukezhan.core.lang.NumberUtil;

public class ShortConverter implements Converter<Object, Short> {

    private static final IntegerConverter CONVERTER = new IntegerConverter();

    private static class Holder {
        static final ShortConverter INSTANCE = new ShortConverter();
    }

    @Override
    public Short apply(Object input) {
        Integer output = CONVERTER.apply(input);
        return NumberUtil.convert(output, Short.class);
    }

    @Override
    public String toString() {
        return "ShortConverter";
    }

    public static ShortConverter getInstance() {
        return Holder.INSTANCE;
    }

}
