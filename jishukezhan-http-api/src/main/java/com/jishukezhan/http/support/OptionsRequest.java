package com.jishukezhan.http.support;

import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;

public class OptionsRequest extends RequestSupport<OptionsRequest> {

    public OptionsRequest(String url) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.OPTIONS;
    }

    @Override
    protected RequestBody genBody() {
        return null;
    }

}
