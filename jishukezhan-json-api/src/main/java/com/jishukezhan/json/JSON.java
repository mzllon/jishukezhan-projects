package com.jishukezhan.json;

import java.lang.reflect.Type;

public class JSON {

    private Handler handler;

    public String toJson(Object src) {
        if (src == null) {
            return "";
        }
        return toJson(src, src.getClass());
    }

    public String toJson(Object src, Type typeOfSrc) {
        if (src == null) {
            return "";
        }
        return handler.serialize(src, typeOfSrc);
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(String json, Class<T> clazz) {
        Object obj = fromJson(json, (Type) clazz);
        return (T) obj;
    }

    public <T> T fromJson(String json, Type typeOfT) {
        if (json == null) {
            return null;
        }
        return handler.deserialize(json, typeOfT);
    }

    public JSON setHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

}
