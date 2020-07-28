package com.jishukezhan.http.support;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.http.ContentType;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.RequestBody;
import com.jishukezhan.json.JSON;
import com.jishukezhan.json.JSONFactory;

import java.io.File;
import java.io.InputStream;

public class BodyRequest extends RequestSupport<BodyRequest> {

    private RequestBody body;

    private JSON json;

    public BodyRequest(String url) {
        this(url, JSONFactory.create().build());
    }

    public BodyRequest(String url, @NonNull JSON json) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.POST;
        this.json = Preconditions.requireNonNull(json, "json == null");
    }

    public BodyRequest put() {
        this.method = Method.PUT;
        return this;
    }

    public BodyRequest patch() {
        this.method = Method.PATCH;
        return this;
    }


    public BodyRequest json(String content) {
        body = RequestBody.create(ContentType.APPLICATION_JSON, content);
        return this;
    }

    public BodyRequest json(Object obj) {
        Preconditions.requireNonNull(obj, "obj == null");
        return json(json.toJson(obj));
    }

    public BodyRequest xml(String content) {
        Preconditions.requireNonNull(content, "content == null");
        body = RequestBody.create(ContentType.APPLICATION_XML, content);
        return this;
    }

    public BodyRequest file(File file) {
        Preconditions.requireNonNull(file, "file == null");
        body = RequestBody.create(ContentType.APPLICATION_OCTET_STREAM, file);
        return this;
    }

    public BodyRequest bytes(byte[] data) {
        Preconditions.requireNonNull(data, "data == null");
        body = RequestBody.create(ContentType.APPLICATION_OCTET_STREAM, data);
        return this;
    }

    public BodyRequest stream(InputStream in) {
        Preconditions.requireNonNull(in, "in == null");
        body = RequestBody.create(ContentType.APPLICATION_OCTET_STREAM, in);
        return this;
    }

    @Override
    protected RequestBody genBody() {
        if (body == null) {
            body = RequestBody.create(null, new byte[0]);
        }
        return body;
    }

}
