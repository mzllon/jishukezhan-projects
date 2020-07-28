package com.jishukezhan.core.exceptions;

import java.io.IOException;

/**
 * IO异常的包装类，转换为运行时异常
 *
 * @author miles.tang
 */
public class IoRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    /**
     * IO异常的构造
     *
     * @param cause IO异常对象
     */
    public IoRuntimeException(IOException cause) {
        super(cause);
    }

    /**
     * 自定义异常信息的构造器
     *
     * @param message 异常信息
     */
    public IoRuntimeException(String message) {
        super(message);
    }

    /**
     * 带有IO异常和自定义异常信息的构造器
     *
     * @param message 自定义异常信息
     * @param cause   IO异常
     */
    public IoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
