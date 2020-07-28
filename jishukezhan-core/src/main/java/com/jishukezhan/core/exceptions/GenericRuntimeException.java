package com.jishukezhan.core.exceptions;

/**
 * 工具的基础异常类
 *
 * @author miles.tang
 */
public class GenericRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1990L;

    public GenericRuntimeException() {
        super();
    }

    public GenericRuntimeException(Throwable cause) {
        super(cause);
    }

    public GenericRuntimeException(String message) {
        super(message);
    }

    public GenericRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
