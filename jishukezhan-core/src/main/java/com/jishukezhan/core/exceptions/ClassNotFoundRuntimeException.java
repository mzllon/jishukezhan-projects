package com.jishukezhan.core.exceptions;

public class ClassNotFoundRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public ClassNotFoundRuntimeException(ClassNotFoundException cause) {
        super(cause);
    }

}
