package com.jishukezhan.core.lang;

import com.jishukezhan.core.math.BigDecimalCalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * Number Utilities
 *
 * @author miles.tang
 */
public class NumberUtil {

    private NumberUtil() {
        throw new AssertionError("No com.jishukezhan.core.lang.NumberUtil instances for you!");
    }

    private static final int DEFAULT_SCALE = 2;

    private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);

    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

    /**
     * 将科学计数法转为字符串
     *
     * @param val 以科学计数法形式的数字
     * @return 字符串
     */
    public static String avoidScientificNotation(double val) {
        return avoidScientificNotation(String.valueOf(val));
    }

    /**
     * 将科学计数法转为字符串
     *
     * @param val 以科学计数法形式的数字
     * @return 字符串
     */
    public static String avoidScientificNotation(String val) {
        if (val == null) {
            return null;
        }
        if (val.length() == 0) {
            return StringUtil.EMPTY_STRING;
        }
        return val.matches("^\\d(.\\d+)?[eE](\\d+)$") ? new BigDecimal(val).toPlainString() : val;
    }

    /**
     * 判断字符串是否为纯数字组成
     *
     * @param str 数值字符串
     * @return {@link Boolean}
     * @see #isNumeric(String) 带有小数点的判断请调用该方法
     */
    public static boolean isDigits(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是数值,如果是则返回true,否则返回false
     * <p>
     * 支持类似'1','-1','0.01','.01'等格式的数值字符串,但是不支持进制数字字符串
     * </p>
     *
     * @param str 数值字符串
     * @return {@linkplain Boolean}
     */
    public static boolean isNumeric(String str) {
        if (StringUtil.hasText(str)) {
            String regex = "^[-+]?(\\d+)?(\\.\\d+)?$";
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(str).find();
        }
        return false;
    }

    /**
     * 人民币金额转为分
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(long yuan) {
        return yuan * 100;
    }

    /**
     * 人民币金额转为分
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(double yuan) {
        return yuan2Fen(Double.toString(yuan));
    }

    /**
     * 人民币金额转为分
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(String yuan) {
        return yuan2Fen(yuan, true);
    }

    /**
     * 人民币金额转为分,保留小数位位数
     *
     * @param yuan  金额,单位为元
     * @param round 如果值{@code true}则超出部分四舍五入，否则直接忽略
     * @return 金额分
     */
    public static long yuan2Fen(double yuan, boolean round) {
        return yuan2Fen(Double.toString(yuan), round);
    }

    /**
     * 人民币金额转为分,保留小数位位数
     *
     * @param yuan  金额,单位为元
     * @param round 如果值{@code true}则超出部分四舍五入，否则直接忽略
     * @return 金额分
     */
    public static long yuan2Fen(String yuan, boolean round) {
        return BigDecimalCalculator
                .of(yuan, 2, RoundingMode.HALF_UP)
                .multiply(100)
                .getResultToLong();
    }

    /**
     * 将人民币金额(字符串)转成分(结果为仍为字符串)
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额，单位为元
     * @return 金额，单位为分
     */
    public static String yuan2FenString(String yuan) {
        return String.valueOf(yuan2Fen(yuan, true));
    }

    /**
     * 将人民币金额(字符串)转成分(结果为仍为字符串)
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额，单位为元
     * @return 金额，单位为分
     */
    public static String yuan2FenString(long yuan) {
        return String.valueOf(yuan2Fen(yuan));
    }

    /**
     * 将人民币金额(字符串)转成分(结果为仍为字符串)
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额，单位为元
     * @return 金额，单位为分
     */
    public static String yuan2FenString(double yuan) {
        return String.valueOf(yuan2Fen(yuan));
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static double fen2Yuan(long fen) {
        return fen2Yuan(Long.toString(fen));
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static double fen2Yuan(String fen) {
        return BigDecimalCalculator.of(fen)
                .divide(DEFAULT_SCALE, RoundingMode.HALF_UP, 100)
                .getResultToDouble();
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static String fen2YuanString(String fen) {
        return BigDecimalCalculator.of(fen)
                .divide(DEFAULT_SCALE, RoundingMode.HALF_UP, 100)
                .getResultStr();
    }


    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see ArrayUtil#min(long...)
     */
    public static long min(long... numberArray) {
        return ArrayUtil.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see ArrayUtil#min(int...)
     */
    public static int min(int... numberArray) {
        return ArrayUtil.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see ArrayUtil#min(short...)
     */
    public static short min(short... numberArray) {
        return ArrayUtil.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see ArrayUtil#min(double...)
     */
    public static double min(double... numberArray) {
        return ArrayUtil.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数字数组
     * @return 最小值
     * @see ArrayUtil#min(float...)
     */
    public static float min(float... numberArray) {
        return ArrayUtil.min(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see ArrayUtil#max(long...)
     * @since 4.0.7
     */
    public static long max(long... numberArray) {
        return ArrayUtil.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see ArrayUtil#max(int...)
     * @since 4.0.7
     */
    public static int max(int... numberArray) {
        return ArrayUtil.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see ArrayUtil#max(short...)
     * @since 4.0.7
     */
    public static short max(short... numberArray) {
        return ArrayUtil.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see ArrayUtil#max(double...)
     * @since 4.0.7
     */
    public static double max(double... numberArray) {
        return ArrayUtil.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see ArrayUtil#max(float...)
     */
    public static float max(float... numberArray) {
        return ArrayUtil.max(numberArray);
    }

    /**
     * 将给定的数值转换为目标类型的实例
     *
     * @param number      数值
     * @param targetClass 目标类型
     * @param <T>         泛型
     * @return 目标类型实例
     * @throws IllegalArgumentException 非JDK{@linkplain Number}实现类
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T convert(Number number, Class<T> targetClass)
            throws IllegalArgumentException {
        Preconditions.requireNonNull(number, "Number must not be null");
        Preconditions.requireNonNull(targetClass, "Target class must not be null");

        if (targetClass.isInstance(number)) {
            return (T) number;
        } else if (Byte.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Byte.valueOf(number.byteValue());
        } else if (Short.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Short.valueOf(number.shortValue());
        } else if (Integer.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Integer.valueOf(number.intValue());
        } else if (Float.class == targetClass) {
            return (T) Float.valueOf(number.floatValue());
        } else if (Double.class == targetClass) {
            return (T) Double.valueOf(number.doubleValue());
        } else if (Long.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            return (T) Long.valueOf(value);
        } else if (BigInteger.class == targetClass) {
            if (number instanceof BigDecimal) {
                // do not lose precision - use BigDecimal's own conversion
                return (T) ((BigDecimal) number).toBigInteger();
            } else {
                // original value is not a Big* number - use standard long conversion
                return (T) BigInteger.valueOf(number.longValue());
            }
        } else if (BigDecimal.class == targetClass) {
            return (T) new BigDecimal(number.toString());
        }
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
                number.getClass().getName() + "] to unsupported target class [" + targetClass.getName() + "]");
    }


    /**
     * Check for a {@code BigInteger}/{@code BigDecimal} long overflow
     * before returning the given number as a long value.
     *
     * @param number      the number to convert
     * @param targetClass the target class to convert to
     * @return the long value, if convertible without overflow
     * @throws IllegalArgumentException if there is an overflow
     * @see #raiseOverflowException
     */
    private static long checkedLongValue(Number number, Class<? extends Number> targetClass) {
        BigInteger bi = null;
        if (number instanceof BigInteger) {
            bi = (BigInteger) number;
        } else if (number instanceof BigDecimal) {
            bi = ((BigDecimal) number).toBigInteger();
        }
        // Effectively analogous to JDK 8's BigInteger.longValueExact()
        if (bi != null && (bi.compareTo(LONG_MIN) < 0 || bi.compareTo(LONG_MAX) > 0)) {
            raiseOverflowException(number, targetClass);
        }
        return number.longValue();
    }

    /**
     * Raise an <em>overflow</em> exception for the given number and target class.
     *
     * @param number      the number we tried to convert
     * @param targetClass the target class we tried to convert to
     * @throws IllegalArgumentException if there is an overflow
     */
    private static void raiseOverflowException(Number number, Class<?> targetClass) {
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
                number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
    }

    /**
     * 将字符串转为数值
     *
     * @param str 字符串数值
     * @return 数值
     * @throws NumberFormatException 转换异常
     */
    public static Number createNumber(String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        if (StringUtil.isEmpty(str)) {
            return null;
            //throw new NumberFormatException("A blank string is not a valid number");
        }
        if (str.startsWith("--")) {
            // this is protection for poorness in java.lang.BigDecimal.
            // it accepts this as a legal value, but it does not appear
            // to be in specification of class. OS X Java parses it to
            // a wrong value.
            return null;
        }
        if (str.startsWith("0x") || str.startsWith("-0x")) {
            return createInteger(str);
        }
        char lastChar = str.charAt(str.length() - 1);
        String mant;
        String dec;
        String exp;
        int decPos = str.indexOf('.');
        int expPos = str.indexOf('e') + str.indexOf('E') + 1;

        if (decPos > -1) {

            if (expPos > -1) {
                if (expPos < decPos) {
                    throw new NumberFormatException(str + " is not a valid number.");
                }
                dec = str.substring(decPos + 1, expPos);
            } else {
                dec = str.substring(decPos + 1);
            }
            mant = str.substring(0, decPos);
        } else {
            if (expPos > -1) {
                mant = str.substring(0, expPos);
            } else {
                mant = str;
            }
            dec = null;
        }
        if (!Character.isDigit(lastChar) && lastChar != '.') {
            if (expPos > -1 && expPos < str.length() - 1) {
                exp = str.substring(expPos + 1, str.length() - 1);
            } else {
                exp = null;
            }
            //Requesting a specific type..
            String numeric = str.substring(0, str.length() - 1);
            boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
            switch (lastChar) {
                case 'l':
                case 'L':
                    if (dec == null
                            && exp == null
                            && (numeric.charAt(0) == '-' && isDigits(numeric.substring(1)) || isDigits(numeric))) {
                        try {
                            return createLong(numeric);
                        } catch (NumberFormatException nfe) {
                            //Too big for a long
                        }
                        return createBigInteger(numeric);

                    }
                    throw new NumberFormatException(str + " is not a valid number.");
                case 'f':
                case 'F':
                    try {
                        Float f = createFloat(numeric);
                        if (!(f.isInfinite() || (f == 0.0F && !allZeros))) {
                            //If it's too big for a float or the float value = 0 and the string
                            //has non-zeros in it, then float does not have the precision we want
                            return f;
                        }

                    } catch (NumberFormatException nfe) {
                        // ignore the bad number
                    }
                    //$FALL-THROUGH$
                case 'd':
                case 'D':
                    try {
                        Double d = createDouble(numeric);
                        if (!(d.isInfinite() || (d.floatValue() == 0.0D && !allZeros))) {
                            return d;
                        }
                    } catch (NumberFormatException nfe) {
                        // ignore the bad number
                    }
                    try {
                        return createBigDecimal(numeric);
                    } catch (NumberFormatException e) {
                        // ignore the bad number
                    }
                    //$FALL-THROUGH$
                default:
                    throw new NumberFormatException(str + " is not a valid number.");

            }
        } else {
            //User doesn't have a preference on the return type, so let's start
            //small and go from there...
            if (expPos > -1 && expPos < str.length() - 1) {
                exp = str.substring(expPos + 1);
            } else {
                exp = null;
            }
            if (dec == null && exp == null) {
                //Must be an int,long,bigint
                try {
                    return createInteger(str);
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }
                try {
                    return createLong(str);
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }
                return createBigInteger(str);

            } else {
                //Must be a float,double,BigDecimal
                boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
                try {
                    Float f = createFloat(str);
                    if (!(f.isInfinite() || (f == 0.0F && !allZeros))) {
                        return f;
                    }
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }
                try {
                    Double d = createDouble(str);
                    if (!(d.isInfinite() || (d == 0.0D && !allZeros))) {
                        return d;
                    }
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }

                return createBigDecimal(str);

            }
        }
    }

    public static Integer createInteger(String str) {
        if (str == null) {
            return null;
        }
        // decode() handles 0xAABD and 0777 (hex and octal) as well.
        return Integer.decode(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>Float</code>.</p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>Float</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Float createFloat(String str) {
        if (str == null) {
            return null;
        }
        return Float.valueOf(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>Long</code>.</p>
     * <p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>Long</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Long createLong(String str) {
        if (str == null) {
            return null;
        }
        return Long.valueOf(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>Double</code>.</p>
     * <p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>Double</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Double createDouble(String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>BigInteger</code>.</p>
     * <p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>BigInteger</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static BigInteger createBigInteger(String str) {
        if (str == null) {
            return null;
        }
        return new BigInteger(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>BigDecimal</code>.</p>
     * <p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>BigDecimal</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static BigDecimal createBigDecimal(String str) {
        if (str == null) {
            return null;
        }
        // handle JDK1.3.1 bug where "" throws IndexOutOfBoundsException
        if (StringUtil.isEmpty(str)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        return new BigDecimal(str);
    }

    private static boolean isAllZeros(String str) {
        if (str == null) {
            return true;
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) != '0') {
                return false;
            }
        }
        return str.length() > 0;
    }


}
