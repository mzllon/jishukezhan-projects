package com.jishukezhan.http;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.http.ContentType;
import com.jishukezhan.core.io.FileUtil;
import com.jishukezhan.core.io.IOUtil;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.Preconditions;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

public class RequestBody {

    protected ContentType contentType;

    private byte[] data;

    protected RequestBody() {
        this.contentType = ContentType.ALL;
    }

    protected RequestBody(ContentType contentType, byte[] data) {
        this.contentType = contentType;
        this.data = data;
    }

    public int contentLength() {
        return data != null ? data.length : 0;
    }

    public ContentType contentType() {
        return contentType;
    }

    public byte[] getData() {
        return data;
    }

    /**
     * 返回编码
     *
     * @return 编码，可能为{@code null}
     */
    public Charset getCharset() {
        return contentType != null ? contentType.getCharset() : CharsetUtil.UTF_8;
    }

    public static RequestBody create(@Nullable ContentType contentType, @NonNull String content) {
        Charset encoding = CharsetUtil.UTF_8;
        if (contentType != null) {
            encoding = contentType.getCharset();
        }
        return new RequestBody(contentType, content.getBytes(encoding));
    }

    public static RequestBody create(@Nullable ContentType contentType, @NonNull byte[] data) {
        return new RequestBody(contentType, data);
    }

    public static RequestBody create(@Nullable ContentType contentType, @NonNull File file) {
        Preconditions.requireNonNull(file, "file == null");
        return new RequestBody(contentType, FileUtil.readBytes(file));
    }

    public static RequestBody create(@Nullable ContentType contentType, InputStream in) {
        Preconditions.requireNonNull(in, "in == null");
        return new RequestBody(contentType, IOUtil.readBytes(in));
    }

}
