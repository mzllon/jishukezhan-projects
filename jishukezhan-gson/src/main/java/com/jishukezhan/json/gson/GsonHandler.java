package com.jishukezhan.json.gson;

import com.google.gson.Gson;
import com.jishukezhan.json.Handler;
import com.jishukezhan.json.JsonRuntimeException;

import java.lang.reflect.Type;

public class GsonHandler implements Handler {

    private Gson gson;

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
        return gson.toJson(src, typeOfT);
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
        return gson.fromJson(json, typeOfT);
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
