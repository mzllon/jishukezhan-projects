package com.jishukezhan.core.date;

import com.jishukezhan.core.exceptions.DateRuntimeException;

/**
 * 月份枚举
 *
 * @author miles.tang
 */
public enum Month {

    /**
     * 一月
     */
    JANUARY("January", "Jan", "一月"),
    /**
     * 二月
     */
    FEBRUARY("February", "Feb", "二月"),
    /**
     * 三月
     */
    MARCH("March", "Mar", "三月"),
    /**
     * 四月
     */
    APRIL("April", "Apr", "四月"),
    /**
     * 五月
     */
    MAY("May", "May", "五月"),
    /**
     * 六月
     */
    JUNE("June", "Jun", "六月"),
    /**
     * 七月
     */
    JULY("July", "Jul", "七月"),
    /**
     * 八月
     */
    AUGUST("August", "Aug", "八月"),
    /**
     * 九月
     */
    SEPTEMBER("September", "Sep", "九月"),
    /**
     * 十月
     */
    OCTOBER("October", "Oct", "十月"),
    /**
     * 十一月
     */
    NOVEMBER("November", "Nov", "十一月"),
    /**
     * 十二月
     */
    DECEMBER("December", "Dec", "十二月"),
    ;

    private String enName;

    private String abbrEnName;

    private String cn;

    private static final Month[] ENUMS = Month.values();

    Month(String enName, String abbrEnName, String cn) {
        this.enName = enName;
        this.abbrEnName = abbrEnName;
        this.cn = cn;
    }

    public String getEnName() {
        return enName;
    }

    public String getAbbrEnName() {
        return abbrEnName;
    }

    public String getCn() {
        return cn;
    }

    /**
     * 返回月份的数值,范围在1-12
     *
     * @return 月份的数值
     */
    public int getValue() {
        return ordinal() + 1;
    }

    /**
     * 增加月份
     *
     * @param months 可以为负数，表示减少月份
     * @return 新的月份，非空
     */
    public Month plus(int months) {
        int amount = (months % 12);
        // amount+12为了保证但months为负数时修正
        return ENUMS[(ordinal() + (amount + 12)) % 12];
    }

    /**
     * 减少月份
     *
     * @param months 可以为负数，表示增加月份
     * @return 新的月份，非空
     */
    public Month minus(int months) {
        return plus(-(months % 12));
    }

    public java.time.Month to() {
        return java.time.Month.of(getValue());
    }

    public static Month of(int month) {
        if (month < 1 || month > 12) {
            throw new DateRuntimeException("Invalid value for MonthOfYear: " + month);
        }
        return ENUMS[month - 1];
    }

}
