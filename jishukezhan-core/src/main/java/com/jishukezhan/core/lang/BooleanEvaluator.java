package com.jishukezhan.core.lang;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 布尔
 *  @author miles.tang
 */
public class BooleanEvaluator {

    private final Set<Object> trueFactors = new HashSet<>();

    private final Set<Object> falseFactors = new HashSet<>();

    /**
     * 当比对的对象为空时返回{@code false}
     */
    private boolean nullValue = false;

    /**
     * 比对的字符串是否忽略大小写
     */
    private boolean stringIgnoreCase = true;

    public BooleanEvaluator(boolean nullValue, boolean stringIgnoreCase, Object[] trueFactors, Object[] falseFactors) {
        this(nullValue, stringIgnoreCase, CollectionUtil.newArrayList(trueFactors), CollectionUtil.newArrayList(falseFactors));
    }

    public BooleanEvaluator(boolean nullValue, boolean stringIgnoreCase, List<Object> trueFactors, List<Object> falseFactors) {
        setNullValue(nullValue);
        setStringIgnoreCase(stringIgnoreCase);
        addTrueFactors(trueFactors);
        addFalseFactors(falseFactors);
    }

    public boolean isNullValue() {
        return nullValue;
    }

    public void setNullValue(boolean nullValue) {
        this.nullValue = nullValue;
    }

    public boolean isStringIgnoreCase() {
        return stringIgnoreCase;
    }

    public void setStringIgnoreCase(boolean stringIgnoreCase) {
        this.stringIgnoreCase = stringIgnoreCase;
    }

    public void addTrueFactors(List<Object> trueFactors) {
        if (CollectionUtil.isNotEmpty(trueFactors)) {
            for (Object object : trueFactors) {
                addTrueFactor(object);
            }
        }
    }

    public void addFalseFactors(List<Object> falseFactors) {
        if (CollectionUtil.isNotEmpty(falseFactors)) {
            for (Object object : falseFactors) {
                addFalseFactor(object);
            }
        }
    }

    public void addTrueFactor(Object trueFactor) {
        if (trueFactor != null) {
            if (trueFactor instanceof String) {
                trueFactors.add(stringIgnoreCase ? ((String) trueFactor).toLowerCase() : trueFactor);
            }
            trueFactors.add(trueFactor);
        }
    }

    public void addFalseFactor(Object falseFactor) {
        if (falseFactor != null) {
            if (falseFactor instanceof String) {
                falseFactors.add(stringIgnoreCase ? ((String) falseFactor).toLowerCase() : falseFactor);
                return;
            }
            falseFactors.add(falseFactor);
        }
    }

    public void addFactor(Object trueFactor, Object falseFactor) {
        addTrueFactor(trueFactor);
        addFalseFactor(falseFactor);
    }

    public boolean evalTrue(Object object) {
        if (object == null) {
            return nullValue;
        }
        if (object instanceof String && stringIgnoreCase) {
            object = ((String) object).toLowerCase();
        }
        if (trueFactors.contains(object)) {
            return true;
        }
        if (falseFactors.contains(object)) {
            return false;
        }
        return !nullValue;
    }

    public boolean evalFalse(Object object) {
        if (object == null) {
            return nullValue;
        }
        if (object instanceof String && stringIgnoreCase) {
            object = ((String) object).toLowerCase();
        }
        if (falseFactors.contains(object)) {
            return true;
        }
        if (trueFactors.contains(object)) {
            return false;
        }
        return nullValue;
    }

    public static BooleanEvaluator createFalseEvaluator(boolean nullValue, boolean stringIgnoreCase, Object[] falseArray) {
        return new BooleanEvaluator(nullValue, stringIgnoreCase, null, falseArray);
    }

    public static BooleanEvaluator createFalseEvaluator(Object... falseArray) {
        return new BooleanEvaluator(false, true, null, falseArray);
    }

    public static BooleanEvaluator createTrueEvaluator(Object... truthArray) {
        return new BooleanEvaluator(false, true, truthArray, null);
    }

    public static BooleanEvaluator createTrueEvaluator(boolean nullValue, boolean stringIgnoreCase, Object[] truthArray) {
        return new BooleanEvaluator(nullValue, stringIgnoreCase, truthArray, null);
    }

}
