package com.jishukezhan.core.lang;

import com.jishukezhan.core.exceptions.ClassNotFoundRuntimeException;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.io.FastByteArrayOutputStream;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

/**
 * Object Utilities
 *
 * @author miles.tang
 */
public class ObjectUtil {

    private ObjectUtil() {
        throw new AssertionError("No com.jishukezhan.core.lang.ObjectUtil instances for you!");
    }

    /**
     * <p>判断对象是否为空或{@code null}</p>
     * 支持以下类型
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map>是否为空</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return 是否为空
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return StringUtil.isEmpty((CharSequence) obj);
        } else if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.isEmpty(obj);
        } else if (obj instanceof Collection<?>) {
            return CollectionUtil.isEmpty((Collection<?>) obj);
        } else if (obj instanceof Map<?, ?>) {
            return CollectionUtil.isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof Buffer) {
            return ((Buffer) obj).hasRemaining();
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 如果对象为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean isNull(final Object obj) {
        return Objects.isNull(obj);
    }

    /**
     * 如果对象不为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean nonNull(final Object obj) {
        return Objects.nonNull(obj);
    }

    /**
     * <p>
     * 比较两个对象的内容(equals),如果两个对象相等则返回{@code true},如果两个中有一个为{@code null}则返回{@code false}.
     * 如果两个对象都是{@code null}则返回{@code true}.如果传入的参数类型是数组,则比较的数组里的对象内容,而不是数组引用比较.
     * </p>
     * <pre class="code">
     * ObjectUtils.nullSafeEquals("hello","hello"); //--- true
     * ObjectUtils.nullSafeEquals("hello","hell"); //--- false;
     * ObjectUtils.nullSafeEquals(4,4); //--- true
     * ObjectUtils.nullSafeEquals(new String[]{"aaaa","bbb"},new String[]{"aaaa","bbb"}); //--- true
     * </pre>
     *
     * @param a 第一个比较对象
     * @param b 第二个比较对象
     * @return 判断两个对象内容是否相等
     * @see Arrays#equals(Object[], Object[])
     */
    public static boolean nullSafeEquals(final Object a, final Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.equals(b)) {
            return true;
        }
        if (a.getClass().isArray() && b.getClass().isArray()) {
            if (a instanceof Object[] && b instanceof Object[]) {
                return Arrays.equals((Object[]) a, (Object[]) b);
            }
            if (a instanceof boolean[] && b instanceof boolean[]) {
                return Arrays.equals((boolean[]) a, (boolean[]) b);
            }
            if (a instanceof byte[] && b instanceof byte[]) {
                return Arrays.equals((byte[]) a, (byte[]) b);
            }
            if (a instanceof char[] && b instanceof char[]) {
                return Arrays.equals((char[]) a, (char[]) b);
            }
            if (a instanceof double[] && b instanceof double[]) {
                return Arrays.equals((double[]) a, (double[]) b);
            }
            if (a instanceof float[] && b instanceof float[]) {
                return Arrays.equals((float[]) a, (float[]) b);
            }
            if (a instanceof int[] && b instanceof int[]) {
                return Arrays.equals((int[]) a, (int[]) b);
            }
            if (a instanceof long[] && b instanceof long[]) {
                return Arrays.equals((long[]) a, (long[]) b);
            }
            if (a instanceof short[] && b instanceof short[]) {
                return Arrays.equals((short[]) a, (short[]) b);
            }
        }
        return false;
    }

    /**
     * 判断两个对象是否相等
     *
     * @param a 对象a
     * @param b 对象b
     * @return 如果两个对象相等这返回{@code true},否则返回{@code false}
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * 判断两个对象的大小,注意空{@code null}比非空小
     *
     * @param a   对象a
     * @param b   对象b
     * @param <T> 对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T extends Comparable<? super T>> int compare(T a, T b) {
        return compare(a, b, false);
    }

    /**
     * 判断两个对象的大小
     *
     * @param a           对象a
     * @param b           对象b
     * @param nullGreater 当被比对为{@code null}时是否排序在前面,{@code true}则表示{@code null}比任何非{@code null}大,{@code false}反之
     * @param <T>         对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T extends Comparable<? super T>> int compare(final T a, final T b, final boolean nullGreater) {
        if (a == b) {
            return 0;
        } else if (a == null) {
            return nullGreater ? 1 : -1;
        } else if (b == null) {
            return nullGreater ? -1 : 1;
        }
        return a.compareTo(b);
    }

    /**
     * 判断两个对象大小
     *
     * @param a   对象a
     * @param b   对象b
     * @param c   比较器
     * @param <T> 对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        if (a == b) {
            return 0;
        }
        if (c == null) {
            throw new NullPointerException();
        }
        return c.compare(a, b);
    }

    /**
     * @param obj
     * @return
     */
    public static int hashCode(final Object obj) {
        return Objects.hashCode(obj);
    }

    /**
     * 序列化对象为字节码
     *
     * @param obj 待序列化的对象
     * @throws IoRuntimeException 当序列化失败抛出该异常
     */
    public static byte[] serialize(Serializable obj) throws IoRuntimeException {
        if (obj == null) {
            return null;
        }
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(obj);
            oos.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 将字节码反序列化为对象
     *
     * @param data 字节码
     * @param <T>  对象类型
     * @return 反序列化的对象
     * @throws IoRuntimeException            反序列化失败时抛出该异常
     * @throws ClassNotFoundRuntimeException 对象类型找不到时抛出该异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] data) throws IoRuntimeException, ClassNotFoundRuntimeException {
        if (ArrayUtil.isEmpty(data)) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (T) ois.readObject();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }

}
