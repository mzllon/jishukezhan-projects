package com.jishukezhan.core.lang;

import com.jishukezhan.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 设置键值对时自动忽略null或空的操作
 *
 * @param <K> key
 * @param <V> value
 * @author miles.tang
 */
public class IgnoreNullHashMap<K, V> extends HashMap<K, V> {

    private boolean ignoreNull = true;

    private boolean ignoreStrEmpty = true;

    public boolean isIgnoreNull() {
        return ignoreNull;
    }

    public void setIgnoreNull(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
    }

    public boolean isIgnoreStrEmpty() {
        return ignoreStrEmpty;
    }

    public void setIgnoreStrEmpty(boolean ignoreStrEmpty) {
        this.ignoreStrEmpty = ignoreStrEmpty;
    }

    public IgnoreNullHashMap() {
        super();
    }

    public IgnoreNullHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public IgnoreNullHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public IgnoreNullHashMap(Map<? extends K, ? extends V> m) {
        this.putAll(m);
    }

    public IgnoreNullHashMap(int initialCapacity, boolean ignoreNull, boolean ignoreStrEmpty) {
        super(initialCapacity);
        this.ignoreNull = ignoreNull;
        this.ignoreStrEmpty = ignoreStrEmpty;
    }

    public IgnoreNullHashMap(Map<? extends K, ? extends V> m, boolean ignoreNull, boolean ignoreStrEmpty) {
        this.ignoreNull = ignoreNull;
        this.ignoreStrEmpty = ignoreStrEmpty;
        this.putAll(m);
    }

    @Override
    public V put(K key, V value) {
        if (checkKeyValue(key, value)) {
            return null;
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(@NonNull Map<? extends K, ? extends V> m) {
        Preconditions.requireNonNull(m, "m == null")
                .entrySet()
                .removeIf((Predicate<Entry<? extends K, ? extends V>>) entry -> checkKeyValue(entry.getKey(), entry.getValue()));
        super.putAll(m);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (checkKeyValue(key, value)) {
            return null;
        }
        return super.putIfAbsent(key, value);
    }

    private boolean checkKeyValue(K key, V value) {
        if (ignoreNull) {
            return ArrayUtil.isAnyNull(key, value);
        }
        if (ignoreStrEmpty) {
            if (key instanceof String) {
                return StringUtil.isEmpty((String) key);
            }
            if (value instanceof String) {
                return StringUtil.isEmpty((String) value);
            }
        }
        return false;
    }

}
