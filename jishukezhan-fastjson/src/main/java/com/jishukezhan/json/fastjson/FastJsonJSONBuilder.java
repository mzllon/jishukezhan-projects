package com.jishukezhan.json.fastjson;

import com.jishukezhan.json.JSON;
import com.jishukezhan.json.JSONBuilder;
import com.jishukezhan.json.annotation.JSONName;

@JSONName("fastjson")
public class FastJsonJSONBuilder extends JSONBuilder {

    public FastJsonJSONBuilder() {
        super();
    }

    /**
     * 构建对象
     *
     * @return {@linkplain JSONBuilder}
     */
    @Override
    public JSON build() {
        JSON json = new JSON();
        json.setHandler(new FastJsonHandler());
        return json;
    }

}
