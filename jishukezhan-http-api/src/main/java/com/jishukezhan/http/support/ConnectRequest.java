package com.jishukezhan.http.support;

import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;

public class ConnectRequest extends RequestSupport<ConnectRequest> {

    public ConnectRequest(String url) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.CONNECT;
    }

    @Override
    protected RequestBody genBody() {
        return null;
    }

}
