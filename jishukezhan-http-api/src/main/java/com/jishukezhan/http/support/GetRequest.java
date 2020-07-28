package com.jishukezhan.http.support;

import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;

public class GetRequest extends RequestSupport<GetRequest> {

    public GetRequest(String url) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.GET;
    }

    @Override
    protected RequestBody genBody() {
        return null;
    }

}
