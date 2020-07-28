package com.jishukezhan.json.jackson;

import com.fasterxml.jackson.databind.JavaType;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 内部的JSON工具类
 *
 * @author miles.tang
 */
class JacksonUtil {

    public static boolean isJacksonJavaType(Type type) {
        return type instanceof JavaType;
    }

    public static JavaType toJavaType(Type type) {
        return (JavaType) type;
    }


    public static boolean isClass(Type type) {
        return type instanceof Class;
    }

    public static Class<?> toClass(Type type) {
        if (type instanceof GenericArrayType) {
            return Array.newInstance(toClass(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        }
        if (isPrimitive(type)) {
            return getPrimitiveWrapClass(type);
        }
        if (isClass(type)) {
            return (Class<?>) type;
        }
        if (isParameterizedType(type)) {
            ParameterizedType o = (ParameterizedType) type;
            return getParameterizedTypeWithOwnerType(o.getOwnerType(), o.getRawType(), o.getActualTypeArguments()).getClass();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns true if this type is a primitive.
     */
    public static boolean isPrimitive(Type type) {
        return PRIMITIVE_TO_WRAPPER_TYPE.containsKey(type);
    }


    /**
     * A map from primitive types to their corresponding wrapper types.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;

    /**
     * A map from wrapper types to their corresponding primitive types.
     */
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;

    // Sad that we can't use a BiMap. :(

    static {
        Map<Class<?>, Class<?>> primToWrap = new HashMap<Class<?>, Class<?>>(16);
        Map<Class<?>, Class<?>> wrapToPrim = new HashMap<Class<?>, Class<?>>(16);

        add(primToWrap, wrapToPrim, boolean.class, Boolean.class);
        add(primToWrap, wrapToPrim, byte.class, Byte.class);
        add(primToWrap, wrapToPrim, char.class, Character.class);
        add(primToWrap, wrapToPrim, double.class, Double.class);
        add(primToWrap, wrapToPrim, float.class, Float.class);
        add(primToWrap, wrapToPrim, int.class, Integer.class);
        add(primToWrap, wrapToPrim, long.class, Long.class);
        add(primToWrap, wrapToPrim, short.class, Short.class);
        add(primToWrap, wrapToPrim, void.class, Void.class);

        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(primToWrap);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(wrapToPrim);
    }

    private static void add(Map<Class<?>, Class<?>> forward,
                            Map<Class<?>, Class<?>> backward, Class<?> key, Class<?> value) {
        forward.put(key, value);
        backward.put(value, key);
    }

    /**
     * wrap a primitive type to warped class
     */
    public static Class<?> getPrimitiveWrapClass(Type type) {
        if (isPrimitive(type)) {
            return PRIMITIVE_TO_WRAPPER_TYPE.get(type);
        }
        return (Class<?>) type;
    }

    /**
     * judge a type is a ParameterizedType
     */
    public static boolean isParameterizedType(Type type) {
        return (type instanceof ParameterizedType);
    }

    public static ParameterizedType getParameterizedTypeWithOwnerType(Type ownerType, Type rawType, Type... typeArguments) {
        return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
    }

}
