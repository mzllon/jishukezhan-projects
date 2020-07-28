package com.jishukezhan.core.exceptions;

/**
 * 克隆异常
 *
 */
public class CloneRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1990L;

    public CloneRuntimeException(CloneNotSupportedException e) {
        super(e);
    }
}
