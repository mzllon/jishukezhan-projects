package com.jishukezhan.core.lang;

import com.jishukezhan.core.exceptions.GenericRuntimeException;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Array Utilities
 *
 * @author miles.tang
 */
public class ArrayUtil {

    private ArrayUtil() {
        throw new AssertionError("No com.jishukezhan.core.lang.ArrayUtil instances for you!");
    }

    //region 数组相关的常量

    /**
     * 数组中元素未找到的下标，值为-1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 空数组(字符串类型)
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 空数组(字符类型)
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];

    /**
     * 空数组(Long类型)
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];

    /**
     * 空数组(int类型)
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * 空数组(short类型)
     */
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];

    /**
     * 空数组(byte类型)
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * 空数组(double类型)
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    /**
     * 空数组(float类型)
     */
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

    /**
     * 空数组(boolean类型)
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

    /**
     * 空数组(字符类型)
     */
    public static final Character[] EMPTY_CHAR_OBJECT_ARRAY = new Character[0];

    /**
     * 空数组(Long类型)
     */
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

    /**
     * 空数组(Integer类型)
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

    /**
     * 空数组(Short类型)
     */
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];

    /**
     * 空数组(Byte类型)
     */
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

    /**
     * 空数组(Double类型)
     */
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

    /**
     * 空数组(Float类型)
     */
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];

    /**
     * 空数组(Boolean类型)
     */
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];

    //endregion


    //region 判断对象是否是数组

    /**
     * 判断是否是数组类型
     * <pre>
     *     ObjectUtil.isArray(null);                                 = false
     *     ObjectUtil.isArray("123");                                = false
     *     ObjectUtil.isArray(new String[]{"aa","bb"});              = true
     * </pre>
     *
     * @param obj 对象
     * @return 如果是数组类型则返回{@code true},否则返回{@code false}
     */
    public static boolean isArray(final Object obj) {
        return (obj != null && obj.getClass().isArray());
    }

    /**
     * 判断是否是数组类型
     * <pre>
     *     ObjectUtil.isPrimitiveArray(null)                         = false
     *     ObjectUtil.isPrimitiveArray(1)                            = false
     *     ObjectUtil.isPrimitiveArray(1L)                           = false
     *     ObjectUtil.isPrimitiveArray("1")                          = false
     *     ObjectUtil.isPrimitiveArray(new String[]{})               = false
     *     ObjectUtil.isPrimitiveArray(new int[]{})                  = true
     *     ObjectUtil.isPrimitiveArray(new byte[]{})                 = true
     * </pre>
     *
     * @param obj 对象实例
     * @return 如果是原生数组类型则返回{@code true},否则返回{@code false}
     */
    public static boolean isPrimitiveArray(final Object obj) {
        return isArray(obj) && obj.getClass().getComponentType().isPrimitive();
    }

    //endregion


    //region 数组是否为空判断

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final char... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final boolean... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final byte... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final short... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final int... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final long... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final float... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final double... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static <T> boolean isEmpty(final T... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final char... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final boolean... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final byte... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final short... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final int... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final float... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final double... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final long... array) {
        return array != null && array.length > 0;
    }

    /**
     * 判断是否不为空或不为{@code null}
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 当数组不为空且不是{@code null}时返回{@code true}
     */
    public static <T> boolean isNotEmpty(final T... array) {
        return array != null && array.length != 0;
    }

    /**
     * 判断数组对象是否为{@code null}或者长度为0
     *
     * @param array 数组对象
     * @return 是否为{@code null}或长度为0
     */
    public static boolean isEmpty(final Object array) {
        if (array == null) {
            return true;
        } else if (isArray(array)) {
            return getLength(array) == 0;
        }
        throw new GenericRuntimeException("Object to provide is not a array!");
    }

    /**
     * 判断数组是否为空或数组列表有任意一个元素为{@code null}则返回{@code true}，否则返回{@code false}
     *
     * @param array 数组
     * @param <T>   泛型类型
     * @return {@code true} or {@code false}
     */
    public static <T> boolean isAnyNull(final T... array) {
        if (isEmpty(array)) {
            return true;
        }
        for (T element : array) {
            if (element == null) {
                return true;
            }
        }
        return false;
    }

    //endregion


    //region 数组转为String

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static <T> String join(T[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static <T> String join(T[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(char[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(char[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(boolean[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(boolean[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(byte[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code ,}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(byte[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(short[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(short[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(int[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(int[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(long[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(long[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(float[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(float[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(double[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(double[] array) {
        return join(array, StringUtil.COMMA);
    }

    // endregion


    // region 类型相互转换

    /**
     * Character[]转为char[]
     *
     * @param array Character array
     * @return char array
     * @throws NullPointerException 如果Character[]存在{@code null}则抛出该异常
     */
    public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            //may be null
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Character[]转为char[]
     *
     * @param array Character array
     * @return char array
     * @throws NullPointerException 如果Character[]存在{@code null}则抛出该异常
     */
    public static char[] toPrimitive(final Character[] array, final char valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            //may be null
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * 将Long[]转为long[]
     *
     * @param array Long array
     * @return long array
     * @throws NullPointerException 如果Long[]存在{@code null}则抛出该异常
     */
    public static long[] toPrimitive(final Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            //may be null
            result[i] = array[i];
        }
        return result;
    }

    /**
     * 将Long[]转为long[]
     *
     * @param array        Long array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return long array
     */
    public static long[] toPrimitive(final Long[] array, final long valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            result[i] = (array[i] == null) ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * 将Integer[]转为int[]
     *
     * @param array Integer array
     * @return int array
     * @throws NullPointerException 如果Long[]存在{@code null}则抛出该异常
     */
    public static int[] toPrimitive(final Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int length = array.length;
        final int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * 将Integer[]转为int[]
     *
     * @param array        Integer array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return int array
     */
    public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int length = array.length;
        final int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = (array[i] == null) ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Short[]转为short[]
     *
     * @param array Short array
     * @return short array
     * @throws NullPointerException 如果Short[]存在{@code null}则抛出该异常
     */
    public static short[] toPrimitive(final Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int length = array.length;
        final short[] result = new short[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Short[]转为short[]
     *
     * @param array        Short array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return short array
     */
    public static short[] toPrimitive(final Short[] array, final short valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int length = array.length;
        final short[] result = new short[length];
        for (int i = 0; i < length; i++) {
            result[i] = (array[i] == null) ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Byte[]转为byte[]
     *
     * @param array Byte array
     * @return byte array
     * @throws NullPointerException 如果Byte[]存在{@code null}则抛出该异常
     */
    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        int length = array.length;
        final byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Byte[]转为byte[]
     *
     * @param array        Byte array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return byte array
     */
    public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        int length = array.length;
        final byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Double[]转为double[]
     *
     * @param array Double array
     * @return double array
     * @throws NullPointerException 如果Double[]存在{@code null}则抛出该异常
     */
    public static double[] toPrimitive(final Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        int length = array.length;
        final double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Double[]转为double[]
     *
     * @param array        Double array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return double array
     */
    public static double[] toPrimitive(final Double[] array, final double valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        int length = array.length;
        final double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Float[]转为float[]
     *
     * @param array Float array
     * @return float array
     * @throws NullPointerException 如果Float[]存在{@code null}则抛出该异常
     */
    public static float[] toPrimitive(final Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        int length = array.length;
        final float[] result = new float[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Float[]转为float[]
     *
     * @param array        Float array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return float array
     */
    public static float[] toPrimitive(final Float[] array, final float valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        int length = array.length;
        final float[] result = new float[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Boolean[]转为boolean[]
     *
     * @param array Boolean array
     * @return boolean array
     * @throws NullPointerException 如果Boolean[]存在{@code null}则抛出该异常
     */
    public static boolean[] toPrimitive(final Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        int length = array.length;
        final boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Boolean[]转为boolean[]
     *
     * @param array        Boolean array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return boolean array
     */
    public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        int length = array.length;
        final boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * char[] 转为 Character[]
     *
     * @param array the char array
     * @return the Character array
     */
    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_OBJECT_ARRAY;
        }
        int length = array.length;
        final Character[] result = new Character[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * long[] 转为 Long[]
     *
     * @param array the long array
     * @return the Long array
     */
    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        int length = array.length;
        final Long[] result = new Long[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * int[] 转为 Integer[]
     *
     * @param array the int array
     * @return the Integer array
     */
    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        int length = array.length;
        final Integer[] result = new Integer[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * short[] 转为 Short[]
     *
     * @param array the short array
     * @return the Short array
     */
    public static Short[] toObject(final short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        int length = array.length;
        final Short[] result = new Short[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * byte[] 转为 Byte[]
     *
     * @param array the byte array
     * @return the Byte array
     */
    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        int length = array.length;
        final Byte[] result = new Byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * double[] 转为 Double[]
     *
     * @param array the double array
     * @return the Double array
     */
    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        int length = array.length;
        final Double[] result = new Double[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * float[] 转为 Float[]
     *
     * @param array the float array
     * @return the Float array
     */
    public static Float[] toObject(final float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        int length = array.length;
        final Float[] result = new Float[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * boolean[] 转为 Boolean[]
     *
     * @param array the boolean array
     * @return the Boolean array
     */
    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        int length = array.length;
        final Boolean[] result = new Boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    //endregion


    /**
     * 新建一个空数组
     *
     * @param componentType 数组类型
     * @param newSize       数组大小
     * @param <T>           数组元素类型
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * 获取数组对象的元素类型
     *
     * @param array 数组
     * @return 元素类型
     */
    public static Class<?> getComponentType(Object array) {
        return array == null ? null : array.getClass().getComponentType();
    }

    /**
     * 获取数组的元素类型
     *
     * @param arrayClass 数组类
     * @return 元素类型
     */
    public static Class<?> getComponentType(Class<?> arrayClass) {
        return arrayClass == null ? null : arrayClass.getComponentType();
    }


    /**
     * <p>判断数组中是否包含了指定的元素</p>
     * <pre class="code">
     * ObjectUtil.containsElement(new String[]{"aaaa","bbb","cc",null},null); //--- true
     * ObjectUtil.containsElement(new String[]{"aaaa","bbb","cc"},"cc"); //--- true
     * ObjectUtil.containsElement(new String[]{"aaaa","bbb","cc",null},"xx"); //--- false
     * </pre>
     *
     * @param array   数组
     * @param element 检查的元素对象
     * @param <T>     泛型类型声明
     * @return 如果数组中存在则返回{@code true},否则返回{@code false}
     * @see ObjectUtil#nullSafeEquals(Object, Object)
     */
    public static <T> boolean containsElement(final T[] array, T element) {
        if (isEmpty(array)) {
            return false;
        }
        for (Object arrayEle : array) {
            if (ObjectUtil.nullSafeEquals(arrayEle, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组中是否包含了指定元素的值
     *
     * @param array   数组
     * @param element 检测的元素
     * @return 存在返回{@code true}，否则均为{@code false}
     */
    public static boolean containsElement(final int[] array, int element) {
        if (isEmpty(array)) {
            return false;
        }

        return Arrays.stream(array).anyMatch(value -> value == element);
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 存在返回{@code true}，否则均为{@code false}
     */
    public static boolean containsElement(final char[] array, char value) {
        if (isEmpty(array)) {
            return false;
        }
        for (char ch : array) {
            if (ch == value) {
                return true;
            }
        }
        return false;
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }

        if (isArray(obj)) {
            // check for primitive array types because they unfortunately cannot be cast to Object[]
            if (obj instanceof boolean[]) {
                return Arrays.toString((boolean[]) obj);
            } else if (obj instanceof byte[]) {
                return Arrays.toString((byte[]) obj);
            } else if (obj instanceof char[]) {
                return Arrays.toString((char[]) obj);
            } else if (obj instanceof short[]) {
                return Arrays.toString((short[]) obj);
            } else if (obj instanceof int[]) {
                return Arrays.toString((int[]) obj);
            } else if (obj instanceof float[]) {
                return Arrays.toString((float[]) obj);
            } else if (obj instanceof double[]) {
                return Arrays.toString((double[]) obj);
            } else if (obj instanceof long[]) {
                return Arrays.toString((long[]) obj);
            } else {
                return Arrays.deepToString((Object[]) obj);
            }
        }
        return obj.toString();
    }

    /**
     * 获取数组的长度,如果参数为{@code null}则返回0
     *
     * @param array 数组对象
     * @return 数组的长度
     * @throws IllegalArgumentException 如果参数不是数组，则抛出该异常
     */
    public static int getLength(final Object array) throws IllegalArgumentException {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static <T> T[] requireNotEmpty(final T[] array) {
        return requireNotEmpty(array, "The array must not be null or empty");
    }

    public static <T> T[] requireNotEmpty(final T[] array, final String message) {
        if (isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
        return array;
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     */
    public static long min(long... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        long min = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     */
    public static int min(int... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        int min = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     */
    public static short min(short... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        short min = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     *
     * @param charArray 数组
     * @return 最小值
     */
    public static char min(char... charArray) {
        if (isEmpty(charArray)) {
            throw new IllegalArgumentException("Char array must not be null or empty");
        }
        char min = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            if (min > charArray[i]) {
                min = charArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     *
     * @param byteArray 数组
     * @return 最小值
     */
    public static byte min(byte... byteArray) {
        if (isEmpty(byteArray)) {
            throw new IllegalArgumentException("Byte array must not be null or empty");
        }
        byte min = byteArray[0];
        for (int i = 1; i < byteArray.length; i++) {
            if (min > byteArray[i]) {
                min = byteArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     */
    public static double min(double... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        double min = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     */
    public static float min(float... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        float min = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     */
    public static long max(long... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        long max = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     */
    public static int max(int... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        int max = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     */
    public static short max(short... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        short max = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     */
    public static char max(char... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Char array must not be null or empty");
        }
        char max = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     */
    public static byte max(byte... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Byte array must not be null or empty");
        }
        byte max = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     */
    public static double max(double... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        double max = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     */
    public static float max(float... numberArray) {
        if (isEmpty(numberArray)) {
            throw new IllegalArgumentException("Number array must not be null or empty");
        }
        float max = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }


//
//    /**
//     * 连接两个或多个数组,忽略数组为{@code null}
//     *
//     * @param arrays 数组集合
//     * @param <T>    数组元素类型
//     * @return 连接后的数组
//     */
//    public static <T> T[] concat(T[]... arrays) {
//        if (arrays == null) {
//            return null;
//        }
//        if (arrays.length == 1) {
//            return arrays[0];
//        }
//        int length = 0;
//        for (T[] array : arrays) {
//            if (null != array) {
//                length += array.length;
//            }
//        }
//        T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);
//        length = 0;
//        for (T[] array : arrays) {
//            if (null != array) {
//                System.arraycopy(array, 0, result, length, array.length);
//            }
//        }
//        return result;
//    }

    // 数组的操作，
    //      连接多个数组 concat
    //      删除最后一个元素 pop
    //      向末尾添加一个或多个数组元素 push
    //      数组元素反转 reverse
    //      删除数组第一个元素 shift
    //      返回指定的元素 slice
    //      数组排序 sort
    //      删除指定位置的元素个数，并且还可以添加元素 splice
    //      向数组的开头添加一个或多个数组元素 unshift
    //  以上整理来自于JavaScript Array API https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Array#%E6%96%B9%E6%B3%95

    /**
     * 反转数组
     *
     * @param array 原数组
     * @param <T>   数组元素类型
     * @return 反转后的数组
     */
    public static <T> T[] reverse(final T[] array) {
        if (isEmpty(array)) {
            return array;
        }
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组
     *
     * @param array               原数组
     * @param <T>                 数组元素类型
     * @param startIndexInclusive 开始位置（包含）
     * @param endIndexExclusive   结束位置（不包含）
     * @return 反转后的数组
     */
    public static <T> T[] reverse(final T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int start = max(startIndexInclusive, 0), end = min(endIndexExclusive, array.length);
        T[] result = newArray(getComponentType(array), end - start);

        for (int j = 0, i = end - 1; i >= start; i--) {
            result[j++] = array[i];
        }
        return result;
    }

}
