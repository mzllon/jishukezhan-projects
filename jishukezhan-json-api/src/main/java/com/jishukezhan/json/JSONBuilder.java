package com.jishukezhan.json;

import com.jishukezhan.core.support.CloneSupport;
import com.jishukezhan.json.cfg.Config;

public abstract class JSONBuilder extends CloneSupport<JSONBuilder> {

    protected Config config;


    /**
     * 构建对象
     * @return {@linkplain JSONBuilder}
     */
    public abstract JSON build();


}
