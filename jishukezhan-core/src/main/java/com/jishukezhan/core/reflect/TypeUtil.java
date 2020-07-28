package com.jishukezhan.core.reflect;

import com.jishukezhan.core.utils.ClassUtil;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Stream;

public class TypeUtil {

    private static final Map<Class<?>, Object> EMPTIES;

    static {
        final Map<Class<?>, Object> empties = new LinkedHashMap<>();
        empties.put(boolean.class, false);
        empties.put(Boolean.class, false);
        empties.put(byte[].class, new byte[0]);
        empties.put(Collection.class, Collections.emptyList());
        empties.put(Iterator.class, Collections.emptyIterator());
        empties.put(List.class, Collections.emptyList());
        empties.put(Map.class, Collections.emptyMap());
        empties.put(Set.class, Collections.emptySet());
        empties.put(Optional.class, Optional.empty());
        empties.put(Stream.class, Stream.empty());
        EMPTIES = Collections.unmodifiableMap(empties);
    }

    public static Object emptyValueOf(Type type) {
        return EMPTIES.getOrDefault(ClassUtil.getRawType(type), null);
    }

}
