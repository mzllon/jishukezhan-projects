package com.jishukezhan.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jishukezhan.json.Handler;
import com.jishukezhan.json.JsonRuntimeException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JacksonHandler implements Handler {

    private ObjectMapper objectMapper;

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String serialize(Object src, Type typeOfT) throws JsonRuntimeException {
        try {
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new JsonRuntimeException(e);
        }
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T deserialize(String json, Type typeOfT) throws JsonRuntimeException {
        try {
            if (JacksonUtil.isJacksonJavaType(typeOfT)) {
                return objectMapper.readValue(json, JacksonUtil.toJavaType(typeOfT));
            }
            // is primitive ?

            if (JacksonUtil.isClass(typeOfT)) {
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(JacksonUtil.toClass(typeOfT)));
            }

            if (JacksonUtil.isParameterizedType(typeOfT)) {
                ParameterizedType pType = (ParameterizedType) typeOfT;
                Class<?> parametrized = JacksonUtil.toClass(pType.getRawType());
                Type[] parameterTypes = pType.getActualTypeArguments();
                Class<?>[] parameterClasses = new Class[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameterClasses[i] = JacksonUtil.toClass(parameterTypes[i]);
                }
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses));
            }
            return null;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
