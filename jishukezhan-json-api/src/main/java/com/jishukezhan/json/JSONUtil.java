package com.jishukezhan.json;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.lang.reflect.Type;

/**
 * 提供默认的工具类
 *
 * @author miles.tang
 */
public class JSONUtil {

    /**
     * 默认JSON引擎
     */
    private static JSON defaultJson = JSONFactory.create().build();

    /**
     * 自定义的JSON引擎
     */
    private static JSON customJson = null;

    /**
     * 设置自定义的JSON引擎
     *
     * @param customJson 自定义的JSON引擎
     */
    public static void setCustomJson(@Nullable JSON customJson) {
        JSONUtil.customJson = customJson;
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src 对象
     * @return JSON字符串或空字符串
     * @see #toJson(Object, Type)
     */
    public static String toJson(Object src) {
        return toJson(src, src.getClass());
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src       对象
     * @param typeOfSrc 对象的某个类型
     * @return JSON字符串或空字符串
     */
    public static String toJson(Object src, Type typeOfSrc) {
        if (src == null) {
            return StringUtil.EMPTY_STRING;
        }
        return getJSON(customJson).toJson(src, typeOfSrc);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json  字符串，可以为空
     * @param clazz 类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return getJSON(customJson).fromJson(json, clazz);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return getJSON(customJson).fromJson(json, typeOfT);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src  对象
     * @param json 指定JSON引擎
     * @return JSON字符串或空字符串
     */
    public static String toJson(Object src, JSON json) {
        return toJson(src, src.getClass(), json);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src       对象
     * @param typeOfSrc 类型
     * @param json      指定JSON引擎
     * @return JSON字符串或空字符串
     */
    public static String toJson(Object src, Type typeOfSrc, JSON json) {
        if (src == null) {
            return StringUtil.EMPTY_STRING;
        }
        return getJSON(json).toJson(src, typeOfSrc);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json  字符串，可以为空
     * @param clazz 类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, Class<T> clazz, JSON json) {
        return getJSON(json).fromJson(text, clazz);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, Type typeOfT, JSON json) {
        return getJSON(json).fromJson(text, typeOfT);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param text    字符串，可为空
     * @param typeRef 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, @NonNull TypeRef<T> typeRef) {
        return getJSON(null).fromJson(text, Preconditions.requireNonNull(typeRef).getType());
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param text    字符串，可为空
     * @param typeRef 类型
     * @param jsonObj 指定JSON引擎
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, @NonNull TypeRef<T> typeRef, JSON jsonObj) {
        return getJSON(jsonObj).fromJson(text, Preconditions.requireNonNull(typeRef).getType());
    }

    /**
     * 返回JSON引擎
     *
     * @param json 指定一个JSON引擎，可以为{@code null}
     * @return JSON引擎
     */
    private static JSON getJSON(JSON json) {
        return json != null ? json : defaultJson;
    }

}
