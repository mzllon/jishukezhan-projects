package com.jishukezhan.http.support;

import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;

public class HeadRequest extends RequestSupport<HeadRequest> {

    public HeadRequest(String url) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.HEAD;
    }

    @Override
    protected RequestBody genBody() {
        return null;
    }

}
