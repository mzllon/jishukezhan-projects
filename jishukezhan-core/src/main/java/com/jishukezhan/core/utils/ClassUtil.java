package com.jishukezhan.core.utils;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.lang.Preconditions;

import java.lang.reflect.*;
import java.util.IdentityHashMap;
import java.util.Map;

public class ClassUtil {
    /**
     * Map with primitive wrapper type as key and corresponding primitive
     * type as value, for example: Integer.class -> int.class.
     */
    static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

    /**
     * Map with primitive type as key and corresponding wrapper
     * type as value, for example: int.class -> Integer.class.
     */
    static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
            PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 判断sourceType是否是targetType父类或接口，其类的本身
     *
     * @param sourceType 被检查的类型
     * @param targetType 被检查的类型
     * @return 如果是则返回{@code true}，否则返回{@code false}
     * @see Class#isAssignableFrom(Class)
     */
    public static boolean isAssignable(Class<?> sourceType, Class<?> targetType) {
        Preconditions.requireNonNull(sourceType, "sourceType == null");
        Preconditions.requireNonNull(targetType, "targetType == null");
        if (sourceType.isAssignableFrom(targetType)) {
            return true;
        }
        if (sourceType.isPrimitive()) {
            Class<?> resolvedPrimitive = PRIMITIVE_WRAPPER_TYPE_MAP.get(targetType);
            return sourceType == resolvedPrimitive;
        } else {
            Class<?> resolvedWrapper = PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(targetType);
            return resolvedWrapper != null && sourceType.isAssignableFrom(resolvedWrapper);
        }
    }

    /**
     * 是否为抽象类
     *
     * @param tClass 类
     * @return 是否为抽象类
     */
    public static boolean isAbstract(@NonNull Class<?> tClass) {
        return Modifier.isAbstract(tClass.getModifiers());
    }

    /**
     * 判断类是否是接口
     *
     * @param ownerClass 对象class
     * @return 如果是接口则返回{@code true},否则返回@{@code false}
     */
    public static boolean isInterface(Class<?> ownerClass) {
        return ownerClass != null && Modifier.isInterface(ownerClass.getModifiers());
    }

    /**
     * 是否为标准的类<br>
     * 这个类必须：
     *
     * <pre>
     * 1. 非接口
     * 2. 非抽象类
     * 3. 非Enum枚举
     * 4. 非数组
     * 5. 非注解
     * 6. 非原始类型
     * 7. 非Java综合类(合成类)
     * </pre>
     *
     * @param tClass 类
     * @return 是否为标准类
     */
    public static boolean isNormalClass(Class<?> tClass) {
        return tClass != null && (!tClass.isInterface() && !isAbstract(tClass) && !tClass.isEnum() &&
                !tClass.isArray() && !tClass.isAnnotation() && !tClass.isPrimitive() && !tClass.isSynthetic());
    }

    /**
     * 返回类的原始类型
     *
     * @param type 所有类的超类型
     * @return Class
     */
    public static Class<?> getRawType(Type type) {
        // type is a normal class
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (!(rawType instanceof Class<?>)) {
                throw new IllegalArgumentException("Can not resolve type " + type);
            }
            return (Class<?>) rawType;
        } else if (type instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(genericComponentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            // we could use the variable's bounds, but that won't work if there are multiple.
            // having a raw type that's more general than necessary is okay
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                    + "GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

}
