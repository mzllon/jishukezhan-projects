package com.jishukezhan.core.converter;

import com.jishukezhan.core.exceptions.ValueConvertRuntimeException;
import com.jishukezhan.core.lang.NumberUtil;
import com.jishukezhan.core.lang.StringUtil;

/**
 * {@linkplain Double} 转换器
 *
 * @author miles.tang
 */
public class DoubleConverter implements Converter<Object, Double> {

    private static class Holder {
        static final DoubleConverter INSTANCE = new DoubleConverter();
    }

    @Override
    public Double apply(Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof Boolean) {
            return ((Boolean) input) ? 1D : 0D;
        } else if (input instanceof Number) {
            return NumberUtil.convert((Number) input, Double.class);
        } else if (input instanceof String) {
            return NumberUtil.convert(NumberUtil.createNumber(input.toString()), Double.class);
        }
        throw new ValueConvertRuntimeException(StringUtil.format("Can't cast {} to java.lang.Double", input));
    }

    @Override
    public String toString() {
        return "DoubleConverter";
    }

    public static DoubleConverter getInstance() {
        return Holder.INSTANCE;
    }

}
