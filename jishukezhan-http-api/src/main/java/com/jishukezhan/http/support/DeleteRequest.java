package com.jishukezhan.http.support;

import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;

public class DeleteRequest extends RequestSupport<DeleteRequest> {

    public DeleteRequest(String url) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.DELETE;
    }

    @Override
    protected RequestBody genBody() {
        return null;
    }

}
