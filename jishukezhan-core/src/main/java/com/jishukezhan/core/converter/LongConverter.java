package com.jishukezhan.core.converter;

import com.jishukezhan.core.exceptions.ValueConvertRuntimeException;
import com.jishukezhan.core.lang.NumberUtil;
import com.jishukezhan.core.lang.StringUtil;

import java.util.Date;

/**
 * {@linkplain Long} 转换器
 *
 * @author miles.tang
 */
public class LongConverter implements Converter<Object, Long> {

    private static class Holder {
        static final LongConverter INSTANCE = new LongConverter();
    }

    @Override
    public Long apply(Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Boolean) {
            return ((Boolean) input) ? 1L : 0L;
        } else if (input instanceof Number) {
            return NumberUtil.convert((Number) input, Long.class);
        } else if (input instanceof String) {
            return NumberUtil.convert(NumberUtil.createNumber(input.toString()), Long.class);
        } else if (input instanceof Date) {
            return ((Date) input).getTime();
        }
        throw new ValueConvertRuntimeException(StringUtil.format("Can't cast {} to java.lang.Long", input));
    }

    @Override
    public String toString() {
        return "LongConverter";
    }

    public static LongConverter getInstance() {
        return Holder.INSTANCE;
    }

}
