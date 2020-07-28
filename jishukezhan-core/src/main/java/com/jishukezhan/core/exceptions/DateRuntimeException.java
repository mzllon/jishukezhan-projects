package com.jishukezhan.core.exceptions;

import java.time.temporal.UnsupportedTemporalTypeException;

public class DateRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public DateRuntimeException(String message) {
        super(message);
    }

    public DateRuntimeException(UnsupportedTemporalTypeException cause) {
        super(cause);
    }

}
