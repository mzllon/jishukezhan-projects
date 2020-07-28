package com.jishukezhan.core.math;

import com.jishukezhan.core.lang.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

/**
 * {@linkplain BigDecimal}的计算，有小数的部分才推荐使用
 * 不推荐使用float,double数据类型的，建议将转换为{@linkplain String}再做计算。
 * <p>由于{@linkplain BigDecimal}是不可变对象，如果在大量的计算情况下，不推荐使用。</p>
 *
 * @author miles.tang
 */
public class BigDecimalCalculator {

    private BigDecimal result;

    /**
     * 自动计算出的精度，针对加减法有效，一旦有乘除法运算失效。
     * 保留精度最大值，比如1.2和1.23精度值为2
     * -2 代表有乘除法运算，不再自动计算精度
     * -1 代表初始值
     */
    private int autoScale = -1;

    // region Create Instance

    private BigDecimalCalculator() {
    }

    public static BigDecimalCalculator of(int val) {
        BigDecimalCalculator calculator = new BigDecimalCalculator();
        calculator.result = BigDecimal.valueOf(val);
        return calculator;
    }

    public static BigDecimalCalculator of(float val) {
        return of(Double.toString(val));
    }

    public static BigDecimalCalculator of(double val) {
        return of(Double.toString(val));
    }

    public static BigDecimalCalculator of(long val) {
        BigDecimalCalculator calculator = new BigDecimalCalculator();
        calculator.result = BigDecimal.valueOf(val);
        return calculator;
    }

    public static BigDecimalCalculator of(String val) {
        BigDecimalCalculator calculator = new BigDecimalCalculator();
        calculator.result = new BigDecimal(Preconditions.requireNotEmpty(val));
        calculator.autoScale(val);
        return calculator;
    }

    public static BigDecimalCalculator of(String val, int newScale) {
        return of(val, newScale, RoundingMode.UNNECESSARY);
    }

    public static BigDecimalCalculator of(String val, int newScale, RoundingMode mode) {
        BigDecimalCalculator bigDecimalCalculator = new BigDecimalCalculator();
        bigDecimalCalculator.result = new BigDecimal(Preconditions.requireNotEmpty(val)).setScale(newScale, mode);
        return bigDecimalCalculator;
    }


    public static BigDecimalCalculator of(BigDecimal val) {
        BigDecimalCalculator bigDecimalCalculator = new BigDecimalCalculator();
        bigDecimalCalculator.result = Preconditions.requireNonNull(val);
        return bigDecimalCalculator;
    }

    private void autoScale(String val) {
        if (autoScale > 0 || autoScale == -1) {
            int index = val.indexOf(CharUtil.DOT);
            if (index >= 0) {
                autoScale = val.substring(index + 1).length();
            }
        }
    }

//    /**
//     * 设置精度，超过精度会自动四舍五入
//     *
//     * @param defaultScale 精度值
//     * @return {@linkplain BigDecimalCalculator}
//     */
//    public BigDecimalCalculator setDefaultScale(int defaultScale) {
//        return setDefaultScale(defaultScale, RoundingMode.HALF_UP);
//    }
//
//    /**
//     * 设置精度和舍去模式
//     *
//     * @param defaultScale        精度值
//     * @param defaultRoundingMode 舍去模式
//     * @return {@linkplain BigDecimalCalculator}
//     */
//    public BigDecimalCalculator setDefaultScale(int defaultScale, RoundingMode defaultRoundingMode) {
//        this.defaultScale = defaultScale;
//        this.defaultRoundingMode = defaultRoundingMode;
//        return this;
//    }

    // endregion


    // region Addition With Short

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(short val) {
        return add(BigDecimal.valueOf(val));
    }

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Short val) {
        return val == null ? this : add((short) val);
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(short... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (short value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Short[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Short value : values) {
                add(value);
            }
        }
        return this;
    }

    // endregion


    // region Addition With Integer

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(int val) {
        return add(BigDecimal.valueOf(val));
    }

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Integer val) {
        return val == null ? this : add((int) val);
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(int... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (int value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Integer[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator addInts(Collection<Integer> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                add(value);
            }
        }
        return this;
    }

    // endregion


    // region Addition With Float

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(float val) {
        return add(Float.toString(val));
    }

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Float val) {
        return val == null ? this : add(val.toString());
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(float... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (float value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Float[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Float value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator addFloats(Collection<Float> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Float value : values) {
                add(value);
            }
        }
        return this;
    }

    // endregion


    // region Addition With Long

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(long val) {
        return add(BigDecimal.valueOf(val));
    }

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Long val) {
        return val == null ? this : add((long) val);
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(long... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (long value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Long[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Long value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator addLongs(Collection<Long> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Long value : values) {
                add(value);
            }
        }
        return this;
    }

    // endregion


    // region Addition With Double

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(double val) {
        return add(Double.toString(val));
    }

    /**
     * 加多个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Double val) {
        return val != null ? add(val.toString()) : this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(double... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (double value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Double[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Double value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator addDoubles(Collection<Double> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Double value : values) {
                add(value);
            }
        }
        return this;
    }

    // endregion


    // region Addition With String

    /**
     * 加一个数
     *
     * @param val 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(String val) {
        //非空
        if (StringUtil.hasText(val)) {
            //去除左右两侧空白
            val = StringUtil.trim(val);
            add(new BigDecimal(val));
            autoScale(val);
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(String... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (String value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator addStrs(Collection<String> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (String value : values) {
                add(value);
            }
        }
        return this;
    }

    // endregion


    // region Addition With BigDecimal

    /**
     * 加多个数
     *
     * @param value 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(BigDecimal value) {
        if (value != null) {
            result = result.add(value);
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(BigDecimal... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (BigDecimal value : values) {
                add(value);
            }
        }
        return this;
    }

    /**
     * 加多个数
     *
     * @param values 被加数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator add(Collection<BigDecimal> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (BigDecimal value : values) {
                add(value);
            }
        }
        return this;
    }

    //endregion


    // region Subtraction With Short

    /**
     * 减一个数
     *
     * @param val 数值
     * @return 当前对象
     */
    public BigDecimalCalculator subtract(short val) {
        return subtract(BigDecimal.valueOf(val));
    }

    public BigDecimalCalculator subtract(Short val) {
        return val == null ? this : subtract((short) val);
    }

    // endregion


    // region Subtraction With Integer

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(int val) {
        return subtract(BigDecimal.valueOf(val));
    }

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Integer val) {
        return val == null ? this : subtract((int) val);
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(int... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (int value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Integer[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtractInts(Collection<Integer> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                subtract(value);
            }
        }
        return this;
    }

    // endregion


    //region Subtraction With Float

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(float val) {
        return subtract(Float.toString(val));
    }

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Float val) {
        return val == null ? this : subtract(val.toString());
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(float... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (float value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Float[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Float value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtractFloats(Collection<Float> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Float value : values) {
                subtract(value);
            }
        }
        return this;
    }

    // endregion


    // region Subtraction With Long

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(long val) {
        return subtract(BigDecimal.valueOf(val));
    }

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Long val) {
        return val == null ? this : subtract((long) val);
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(long... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (long value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Long[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Long value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtractLongs(Collection<Long> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Long value : values) {
                subtract(value);
            }
        }
        return this;
    }

    // endregion


    // region Subtraction With Double

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(double val) {
        return subtract(Double.toString(val));
    }

    /**
     * 减一个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Double val) {
        return val == null ? this : subtract(val.toString());
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(double... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (double value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Double[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Double value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtractDoubles(Collection<Double> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Double value : values) {
                subtract(value);
            }
        }
        return this;
    }

    // endregion


    // region Subtraction With String

    /**
     * 减多个数
     *
     * @param val 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(String val) {
        if (StringUtil.hasText(val)) {
            val = StringUtil.trim(val);
            subtract(new BigDecimal(val));
            autoScale(val);
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(String... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (String value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtractStrs(Collection<String> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (String value : values) {
                subtract(value);
            }
        }
        return this;
    }

    // endregion


    // region Subtraction With BigDecimal

    /**
     * 减多个数
     *
     * @param value 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(BigDecimal value) {
        if (value != null) {
            result = result.subtract(value);
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(BigDecimal... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (BigDecimal value : values) {
                subtract(value);
            }
        }
        return this;
    }

    /**
     * 减多个数
     *
     * @param values 被减数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator subtract(Collection<BigDecimal> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (BigDecimal value : values) {
                subtract(value);
            }
        }
        return this;
    }

    //endregion


    //region Multiplication With Integer

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(int val) {
        return multiply(BigDecimal.valueOf(val));
    }

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Integer val) {
        return val == null ? this : multiply((int) val);
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(int... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (int value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Integer[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiplyInts(Collection<Integer> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                multiply(value);
            }
        }
        return this;
    }

    //endregion


    //region Multiplication With Float

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(float val) {
        return multiply(BigDecimal.valueOf(val));
    }

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Float val) {
        return val == null ? this : multiply((float) val);
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(float... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (float value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Float[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Float value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiplyFloats(Collection<Float> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Float value : values) {
                multiply(value);
            }
        }
        return this;
    }

    //endregion


    //region Multiplication With Long

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(long val) {
        return multiply(BigDecimal.valueOf(val));
    }

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Long val) {
        return val == null ? this : multiply((long) val);
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(long... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (long value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Long[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Long value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiplyLongs(Collection<Long> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Long value : values) {
                multiply(value);
            }
        }
        return this;
    }

    //endregion


    //region Multiplication With Double

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(double val) {
        return multiply(BigDecimal.valueOf(val));
    }

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Double val) {
        return val == null ? this : multiply((double) val);
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(double... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (double value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Double[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Double value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiplyDoubles(Collection<Double> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Double value : values) {
                multiply(value);
            }
        }
        return this;
    }

    //endregion


    //region Multiplication With String

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(String val) {
        if (StringUtil.hasText(val)) {
            val = StringUtil.trim(val);
            multiply(new BigDecimal(val));
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(String... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (String value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiplyStrs(Collection<String> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (String value : values) {
                multiply(value);
            }
        }
        return this;
    }

    //endregion


    //region Multiplication With BigDecimal

    /**
     * 乘多个数
     *
     * @param val 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(BigDecimal val) {
        if (val != null) {
            result = result.multiply(val);
            autoScale = -2;
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(BigDecimal... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (BigDecimal value : values) {
                multiply(value);
            }
        }
        return this;
    }

    /**
     * 乘多个数
     *
     * @param values 被乘数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator multiply(Collection<BigDecimal> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (BigDecimal value : values) {
                multiply(value);
            }
        }
        return this;
    }

    //endregion


    //region Division With Short

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int newScale, RoundingMode mode, Short val) {
        return val == null ? this : divide(newScale, mode, (int) val);
    }

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int newScale, RoundingMode mode, short val) {
        return divide(newScale, mode, (int) val);
    }

    //endregion


    //region Division With Integer

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, int val) {
        return divide(scale, roundingMode, BigDecimal.valueOf(val));
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, int... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (int value : values) {
                divide(scale, roundingMode, value);
            }
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(RoundingMode roundingMode, Integer[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                divide(value, roundingMode, value);
            }
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int newScale, RoundingMode roundingMode, Collection<Integer> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (Integer value : values) {
                divide(newScale, roundingMode, value);
            }
        }
        return this;
    }

    //endregion


    //region Division With Float

    /**
     * 除以一个数
     *
     * @param val          被除数
     * @param newScale     精度
     * @param roundingMode 舍去模式
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int newScale, RoundingMode roundingMode, float val) {
        return divide(newScale, roundingMode, (double) val);
    }

    /**
     * 除以一个数
     *
     * @param val          被除数
     * @param newScale     精度
     * @param roundingMode 舍去模式
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int newScale, RoundingMode roundingMode, Float val) {
        if (val != null) {
            return divide(newScale, roundingMode, BigDecimal.valueOf(val));
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int newScale, RoundingMode mode, float... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (float value : values) {
                divide(newScale, mode, value);
            }
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int newScale, RoundingMode mode, Float[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Float value : values) {
                divide(newScale, mode, value);
            }
        }
        return this;
    }

    //endregion


    //region Division With Long

    /**
     * 除以一个数
     *
     * @param scale        精度
     * @param roundingMode 舍去模式
     * @param val          被除数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, long val) {
        return divide(scale, roundingMode, BigDecimal.valueOf(val));
    }

    /**
     * 除以一个数
     *
     * @param scale        精度
     * @param roundingMode 舍去模式
     * @param val          被除数
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, Long val) {
        return val == null ? this : divide(scale, roundingMode, (long) val);
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, long... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (long value : values) {
                divide(scale, roundingMode, value);
            }
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, Long... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Long value : values) {
                divide(scale, roundingMode, value);
            }
        }
        return this;
    }

    //endregion


    //region Division With Double

    /**
     * 除以一个数
     *
     * @param val          被除数
     * @param scale        精度
     * @param roundingMode 舍去模式
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, double val) {
        return divide(scale, roundingMode, BigDecimal.valueOf(val));
    }

    /**
     * 除以一个数
     *
     * @param val          被除数
     * @param scale        精度
     * @param roundingMode 舍去模式
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, Double val) {
        return val == null ? this : divide(scale, roundingMode, (double) val);
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, double... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (double value : values) {
                divide(scale, roundingMode, value);
            }
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, Double[] values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (Double value : values) {
                divide(scale, roundingMode, value);
            }
        }
        return this;
    }

    //endregion


    //region Division With String

    /**
     * 连续除以一个数
     *
     * @param val 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, String val) {
        if (StringUtil.hasText(val)) {
            val = StringUtil.trim(val);
            return divide(scale, roundingMode, new BigDecimal(val));
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, String... values) {
        if (ArrayUtil.isNotEmpty(values)) {
            for (String value : values) {
                divide(scale, roundingMode, value);
            }
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain BigDecimalCalculator}
     */
    public BigDecimalCalculator divide(int scale, RoundingMode roundingMode, List<String> values) {
        if (CollectionUtil.isNotEmpty(values)) {
            for (String value : values) {
                divide(scale, roundingMode, value);
            }
        }
        return this;
    }

    // endregion


    // region Division With BigDecimal

    public BigDecimalCalculator divide(int newScale, RoundingMode mode, BigDecimal val) {
        if (val != null) {
            result = result.divide(val, newScale, mode);
            autoScale = -2;
        }
        return this;
    }

    //endregion


    // region With BigDecimalCalculator

    public BigDecimalCalculator add(BigDecimalCalculator value) {
        return value == null ? this : add(value.getResult());
    }

    public BigDecimalCalculator multiply(BigDecimalCalculator value) {
        return value == null ? this : multiply(value.getResult());
    }

    public BigDecimalCalculator divide(int newScale, RoundingMode mode, BigDecimalCalculator value) {
        if (value != null) {
            result = result.divide(value.getResult(), newScale, mode);
        }
        return this;
    }

    //endregion

    //region 返回结果

    /**
     * 最终计算结果值
     *
     * @return {@linkplain BigDecimal}
     */
    public BigDecimal getResult() {
        return result;
    }

    /**
     * 将计算强制转为int，如果计算结果带有小数会强制舍弃
     *
     * @return {@code int}
     */
    public int getResultToInt() {
        return result.intValue();
    }

    /**
     * 将计算强制转为int，如果计算结果带有小数会强制舍弃
     *
     * @return {@code int}
     */
    public int getResultToInt(int newScale, RoundingMode roundingMode) {
        return result.setScale(newScale, roundingMode).intValue();
    }

    /**
     * 最终计算结果值
     *
     * @return {@code long}
     */
    public long getResultToLong() {
        return result.longValue();
    }

    /**
     * 最终计算结果值
     *
     * @return {@code long}
     */
    public long getResultToLong(int newScale, RoundingMode roundingMode) {
        return result.setScale(newScale, roundingMode).longValue();
    }

    /**
     * 最终计算结果值
     *
     * @return {@code double}
     */
    public double getResultToDouble() {
        if (autoScale > 0) {
            return result.setScale(autoScale, RoundingMode.HALF_EVEN).doubleValue();
        }
        return result.doubleValue();
    }

    /**
     * 最终计算结果值
     *
     * @return {@code double}
     */
    public double getResultToDouble(int newScale, RoundingMode roundingMode) {
        return result.setScale(newScale, roundingMode).doubleValue();
    }

    /**
     * 最终计算结果值
     * 保留2位小数，超出2位小数部分采用四舍五入模式
     *
     * @return {@code String}
     */
    public String getResultStr() {
        if (autoScale > 0) {
            return result.setScale(autoScale, RoundingMode.HALF_EVEN).toString();
        }
        return result.toString();
    }

    /**
     * 获得最终计算结果
     *
     * @param newScale     精度
     * @param roundingMode 舍去模式
     * @return 结果
     */
    public String getResultStr(int newScale, RoundingMode roundingMode) {
        return result.setScale(newScale, roundingMode).toString();
    }

    //endregion

}
