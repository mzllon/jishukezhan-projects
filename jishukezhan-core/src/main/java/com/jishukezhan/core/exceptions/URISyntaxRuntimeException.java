package com.jishukezhan.core.exceptions;

import java.net.URISyntaxException;

public class URISyntaxRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public URISyntaxRuntimeException(URISyntaxException cause) {
        super(cause);
    }

}
