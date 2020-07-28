package com.jishukezhan.http.exceptions;

import com.jishukezhan.core.exceptions.GenericRuntimeException;
import com.jishukezhan.http.Request;

/**
 *
 */
public class CilantroStatusException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    private int status;

    private Request request;

    /**
     * @param message the reason for the failure.
     */
    public CilantroStatusException(int status, String message, Request request) {
        super(message);
        this.status = status;
        this.request = request;
    }

    public int getStatus() {
        return status;
    }

    public Request getRequest() {
        return request;
    }

}
