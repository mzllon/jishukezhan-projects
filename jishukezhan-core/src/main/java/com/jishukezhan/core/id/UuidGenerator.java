package com.jishukezhan.core.id;

import com.jishukezhan.core.lang.StringUtil;

import java.util.UUID;

/**
 * 基于{@linkplain UUID}的ID生成器
 *
 * @author miles.tang
 */
public class UuidGenerator implements IdGenerator<Object> {

    /**
     * 是否忽略中划线
     */
    private final boolean ignoreDash;

    public UuidGenerator() {
        this(false);
    }

    public UuidGenerator(boolean ignoreDash) {
        this.ignoreDash = ignoreDash;
    }

    /**
     * 返回ID
     *
     * @param ignoreObj 参数
     * @return ID
     */
    @Override
    public String get(Object ignoreObj) {
        String id = UUID.randomUUID().toString();
        return ignoreDash ? StringUtil.replace(id, StringUtil.DASH, StringUtil.EMPTY_STRING) : id;
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        return get(null);
    }

}
