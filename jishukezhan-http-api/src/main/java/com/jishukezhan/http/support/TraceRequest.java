package com.jishukezhan.http.support;

import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;

public class TraceRequest extends RequestSupport<TraceRequest> {

    public TraceRequest(String url) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.TRACE;
    }

    @Override
    protected RequestBody genBody() {
        return null;
    }

}
