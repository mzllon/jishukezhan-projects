package com.jishukezhan.core.converter;

import com.jishukezhan.core.lang.BooleanEvaluator;

/**
 * 布尔转换器
 *
 * @author miles.tang
 */
public class BooleanConverter implements Converter<Object, Boolean> {

    private static final BooleanEvaluator EVALUATOR = BooleanEvaluator.createFalseEvaluator(false, true,
            false, "false", 0, "0", "off", "no", "n", "f", "假", "错", "否");

    // lazy, thread-safe instatiation
    private static class Holder {
        static final BooleanConverter INSTANCE = new BooleanConverter();
    }

    @Override
    public Boolean apply(Object input) {
        return EVALUATOR.evalTrue(input);
    }

    @Override
    public String toString() {
        return "BooleanConverter";
    }

    public static BooleanConverter getInstance() {
        return Holder.INSTANCE;
    }

}
