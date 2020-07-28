package com.jishukezhan.core.converter;

import com.jishukezhan.core.lang.NumberUtil;

/**
 * {@linkplain Float} 转换器
 *
 * @author miles.tang
 */
public class FloatConverter implements Converter<Object, Float> {

    private static final DoubleConverter CONVERTER = new DoubleConverter();

    private static class Holder {
        static final FloatConverter INSTANCE = new FloatConverter();
    }

    @Override
    public Float apply(Object input) {
        Double output = CONVERTER.apply(input);
        return NumberUtil.convert(output, Float.class);
    }

    @Override
    public String toString() {
        return "FloatConverter";
    }

    public static FloatConverter getInstance() {
        return Holder.INSTANCE;
    }

}
