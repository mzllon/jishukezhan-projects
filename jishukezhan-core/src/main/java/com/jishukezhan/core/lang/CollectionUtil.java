package com.jishukezhan.core.lang;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection Utilities
 *
 * @author miles.tang
 */
public class CollectionUtil {

    private CollectionUtil() {
        throw new AssertionError("Cannot create instance");
    }

    // region 判断集合是否为空

    /**
     * 判断集合是否为空
     * <pre class="code">CollectionUtils.isEmpty(list);</pre>
     *
     * @param collection 集合
     * @return 如果集合为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 判断map是否为空
     * <pre class="code">CollectionUtils.isEmpty(hashmap);</pre>
     *
     * @param map map集合
     * @return 如果map为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断集合是否为不为空
     * <pre class="code">CollectionUtils.isNotEmpty(list);</pre>
     *
     * @param collection 集合
     * @return 如果集合不为{@code null}且不为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map是否为不为空
     * <pre class="code">CollectionUtils.isNotEmpty(hashmap);</pre>
     *
     * @param map map集合
     * @return 如果map不为{@code null}且不为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    // endregion


    // region JOIN相关方法

    /**
     * 将集合数据转为字符串，每个元素之间采用,拼接。
     * <p>元素的值为{@code null}会忽略</p>
     *
     * @param src 集合数据
     * @return 字符串
     */
    public static String join(final Collection<String> src) {
        return join(src, true);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     * <p>元素的值为{@code null}会忽略</p>
     *
     * @param src       集合数据
     * @param separator 分隔符
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator) {
        return join(src, separator, true);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用,拼接。
     * {@code sortable}参数可以使集合{@code src}先进行正序排序，然后各个元素在拼接。
     *
     * @param src        集合数据
     * @param ignoreNull 值为{@code null}忽略
     * @return 字符串
     */
    public static String join(final Collection<String> src, final boolean ignoreNull) {
        return join(src, StringUtil.COMMA, ignoreNull);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     *
     * @param src        集合数据
     * @param separator  分隔符
     * @param ignoreNull 忽略null
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator, final boolean ignoreNull) {
        return join(src, separator, ignoreNull, false);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     * {@code sortable}参数可以使集合{@code src}先进行正序排序，然后各个元素在拼接。
     *
     * <p>Note:如果Key要先排序则集合元素中不能存在{@code null}，因为{@linkplain TreeSet}数据结构规定</p>
     *
     * @param src        集合数据
     * @param separator  分隔符
     * @param ignoreNull 值为{@code null}忽略
     * @param sortable   值为{@code true}则正序排序，否则默认
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator, final boolean ignoreNull, final boolean sortable) {
        if (src == null) {
            return null;
        }
        if (src.isEmpty()) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }

        Collection<String> resultColl = src;
        if (sortable) {
            resultColl = new TreeSet<>(src);
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String str : resultColl) {
            if (ignoreNull && str == null) {
                continue;
            }
            if (isFirst) {
                sb.append(str);
                isFirst = false;
            } else {
                sb.append(separator).append(str);
            }
        }
        return sb.toString();
    }

    /**
     * <p>
     * 将Map对象转成字符串，其中Key与Value的连接使用{@code keySeparator}拼接,键值对之间采用采用{@code entrySeparator}拼接
     * </p>
     * <pre>
     *     Map&lt;String,String&gt; params = new HashMap&lt;&gt;();
     *     params.put("username","admin");
     *     params.put("password","123456");
     *     String str = CollectionUtils.toString(params,"=","|");
     *     //则得到的字符串结果为 username=admin|password=123456
     * </pre>
     *
     * @param data           数据
     * @param keySeparator   Key和Value的拼接字符串，默认值为空
     * @param entrySeparator 键值对的拼接字符串，默认值为空
     * @return 返回字符串
     */
    public static String join(final Map<String, String> data, String keySeparator, String entrySeparator) {
        return join(data, keySeparator, entrySeparator, false, true);
    }

    /**
     * <p>
     * 将Map对象转成字符串，其中Key与Value的连接使用{@code keySeparator}拼接,键值对之间采用采用{@code entrySeparator}拼接.
     * </p>
     * <pre>
     *     Map&lt;String,String&gt; params = new HashMap&lt;&gt;();
     *     params.put("username","admin");
     *     params.put("password","123456");
     *     String str = CollectionUtils.toString(params,"=","|",true);
     *     //则得到的字符串结果为 password=123456|username=admin
     * </pre>
     *
     * @param data           Map数据
     * @param keySeparator   Key和Value的拼接字符串，默认值为空
     * @param entrySeparator 键值对的拼接字符串，默认值为空
     * @param keySortable    如果值为{@code true}则按自然排序排序,否则不处理
     * @param ignoreNull     如果键或值为{@code true}则忽略空值
     * @return 返回字符串
     */
    private static String join(final Map<String, String> data, String keySeparator, String entrySeparator,
                               boolean keySortable, boolean ignoreNull) {
        if (data == null) {
            return null;
        }
        if (data.isEmpty()) {
            return StringUtil.EMPTY_STRING;
        }
        if (keySeparator == null) {
            keySeparator = StringUtil.EMPTY_STRING;
        }
        if (entrySeparator == null) {
            entrySeparator = StringUtil.EMPTY_STRING;
        }
        Set<String> keys = data.keySet();
        if (keySortable) {
            keys = new TreeSet<>(keys);
        }
        final String _keySeparator = keySeparator;
        return keys.stream().map(key -> {
            String value = data.get(key);
            if (ignoreNull && key != null && value != null) {
                return null;
            }
            return key + _keySeparator + value;
        }).collect(Collectors.joining(entrySeparator));
    }

    /**
     * 将Map对象的数据转为通用签名字符串(目前通用签名规则为：将key和value用'='连接，每个键值对之间用'&nbsp;'连接，并且键列表按照自然排序)
     *
     * @param params Map对象数据
     * @return 返回字符串
     */
    public static String asCommonSignString(final Map<String, String> params) {
        return join(params, "=", StringUtil.AMP, true, true);
    }

    // endregion

    /**
     * 新建一个ArrayList
     *
     * @param <T>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        return (ArrayList<T>) list(false, values);
    }

    /**
     * 新建一个List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param values   数组
     * @return List对象
     */
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (ArrayUtil.isEmpty(values)) {
            return list(isLinked);
        }
        final List<T> list = isLinked ? new LinkedList<>() : new ArrayList<>(values.length);
        Collections.addAll(list, values);
        return list;
    }

    /**
     * 新建一个空List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @return List对象
     */
    public static <T> List<T> list(boolean isLinked) {
        return isLinked ? new LinkedList<>() : new ArrayList<>();
    }

    /**
     * 判断集合非空
     *
     * @param list 被校验的集合
     * @param <T>  集合元素类型
     * @return 原List对象
     */
    public static <T> List<T> requireNonEmpty(List<T> list) {
        if (isEmpty(list)) {
            throw new IllegalArgumentException("The list is null or empty");
        }
        return list;
    }

}
