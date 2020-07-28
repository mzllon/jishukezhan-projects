package com.jishukezhan.core.exceptions;

import java.io.IOException;

public class ZipException extends GenericRuntimeException {

    public ZipException(String message) {
        super(message);
    }

    public ZipException(IOException e) {
        super(e);
    }

}
