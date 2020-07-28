package com.jishukezhan.core.date;

import com.jishukezhan.core.exceptions.DateRuntimeException;

/**
 * 季度枚举类
 *
 * @author miles.tang
 */
public enum Quarter {

    /**
     * 一季度
     */
    Q1,
    /**
     * 二季度
     */
    Q2,
    /**
     * 三季度
     */
    Q3,
    /**
     * 四季度
     */
    Q4,
    ;

    private static Quarter[] ENUMS = Quarter.values();

    private int getValue() {
        return ordinal() + 1;
    }

    public String toCn() {
        switch (this) {
            case Q1:
                return "一季度";
            case Q2:
                return "二季度";
            case Q3:
                return "三季度";
            case Q4:
                return "四季度";
        }
        throw new DateRuntimeException("Error quarter");
    }

    public static Quarter of(int quarter) {
        if (quarter < 1 || quarter > 4) {
            throw new DateRuntimeException("Invalid value for quarter:" + quarter);
        }
        return ENUMS[quarter - 1];
    }

}
