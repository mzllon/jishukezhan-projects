package com.jishukezhan.json;

import com.jishukezhan.annotation.NonNull;

import java.lang.reflect.Type;

public interface Handler {

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    String serialize(@NonNull Object src, @NonNull Type typeOfT) throws JsonRuntimeException;

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @param <T>     泛型类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    <T> T deserialize(@NonNull String json, @NonNull Type typeOfT) throws JsonRuntimeException;

}
