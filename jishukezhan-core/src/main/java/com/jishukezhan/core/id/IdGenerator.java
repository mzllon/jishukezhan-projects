package com.jishukezhan.core.id;

import java.util.function.Supplier;

/**
 * ID生成器
 *
 * @author miles.tang
 */
public interface IdGenerator<O> extends Supplier<String> {

    /**
     * 返回ID
     *
     * @param obj 参数
     * @return ID
     */
    String get(O obj);

    /**
     * 返回ID
     *
     * @return ID
     */
    String get();

}
