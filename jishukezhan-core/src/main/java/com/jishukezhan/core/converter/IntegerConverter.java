package com.jishukezhan.core.converter;

import com.jishukezhan.core.exceptions.ValueConvertRuntimeException;
import com.jishukezhan.core.lang.NumberUtil;
import com.jishukezhan.core.lang.StringUtil;

/**
 * {@linkplain Integer} 转换器
 *
 * @author miles.tang
 */
public class IntegerConverter implements Converter<Object, Integer> {

    private static class Holder {
        static final IntegerConverter INSTANCE = new IntegerConverter();
    }

    public static IntegerConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Integer apply(Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Boolean) {
            return ((Boolean) input) ? 1 : 0;
        } else if (input instanceof Number) {
            return NumberUtil.convert((Number) input, Integer.class);
        } else if (input instanceof String) {
            Number number = NumberUtil.createNumber(input.toString());
            if (number == null) {
                return null;
            }
            return NumberUtil.convert(number, Integer.class);
        }
        throw new ValueConvertRuntimeException(StringUtil.format("Can't cast {} to java.lang.Integer", input));
    }

    @Override
    public String toString() {
        return "IntegerConverter";
    }

}
