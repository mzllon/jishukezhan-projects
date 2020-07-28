package com.jishukezhan.core.exceptions;

public class BeanException extends RuntimeException {

    public BeanException(String msg) {
        super(msg);
    }

    public BeanException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }

}
