package com.jishukezhan.core.date;

import com.jishukezhan.core.exceptions.DateRuntimeException;

import java.time.DayOfWeek;

/**
 *
 */
public enum Week {


    /**
     * 周一
     */
    MONDAY("Monday", "Mon", "一"),
    /**
     * 周二
     */
    TUESDAY("Tue", "Tuesday", "二"),
    /**
     * 周三
     */
    WEDNESDAY("Wed", "Wednesday", "三"),
    /**
     * 周四
     */
    THURSDAY("Thu", "Thursday", "四"),
    /**
     * 周五
     */
    FRIDAY("Fri", "Friday", "五"),
    /**
     * 周六
     */
    SATURDAY("Sat", "Saturday", "六"),
    /**
     * 周日
     */
    SUNDAY("Sun", "Sunday", "日"),
    ;

    private static final Week[] ENUMS = Week.values();

    private String enName;

    private String abbrEnName;

    private String cn;

    Week(String enName, String abbrEnName, String cn) {
        this.enName = enName;
        this.abbrEnName = abbrEnName;
        this.cn = cn;
    }

    /**
     * 返回星期的英文全名
     *
     * @return 英文全名
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 返回星期的英文缩写
     *
     * @return 英文缩写
     */
    public String getAbbrEnName() {
        return abbrEnName;
    }

    /**
     * 返回星期的数值(1-7),1代表周一
     *
     * @return 数值，范围在1-7
     */
    public int getValue() {
        return ordinal() + 1;
    }

    /**
     * 转换为中文名
     *
     * @return 星期的中文名
     */
    public String toChinese() {
        return toChinese("星期");
    }

    /**
     * 转换为中文名
     *
     * @param weekNamePre 表示星期的前缀，例如前缀为“星期”，则返回结果为“星期一”；前缀为”周“，结果为“周一”
     * @return 星期的中文名
     * @since 4.0.11
     */
    public String toChinese(String weekNamePre) {
        return weekNamePre + this.cn;
    }

    /**
     * 转为JDK8的星期
     *
     * @return 星期
     */
    public DayOfWeek to() {
        return DayOfWeek.of(getValue());
    }

    /**
     * 当天星期的下一天
     *
     * @return 下一天的星期几
     */
    public Week next() {
        return plus(1);
    }

    /**
     * 返回星期几的之后的{@code days}的星期几
     *
     * @param days 增加天数
     * @return 星期几
     */
    public Week plus(int days) {
        int amount = days % 7;
        return ENUMS[(ordinal() + (amount + 7)) % 7];
    }

    /**
     * 返回星期几的之签的{@code days}的星期几
     *
     * @param days 减去天数
     * @return 星期几
     */
    public Week minus(int days) {
        return plus(-(days % 7));
    }

    /**
     * 根据数值返回对应的星期
     *
     * @param weekInt 数值
     * @return 星期
     */
    public static Week of(int weekInt) {
        if (weekInt < 1 || weekInt > 7) {
            throw new DateRuntimeException("Invalid value for weekInt: " + weekInt);
        }
        return ENUMS[weekInt - 1];
    }

}
