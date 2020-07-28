package com.jishukezhan.core.converter;

public class NoopConverter implements Converter<Object, Object> {

    @Override
    public Object apply(Object input) {
        return input;
    }

}
