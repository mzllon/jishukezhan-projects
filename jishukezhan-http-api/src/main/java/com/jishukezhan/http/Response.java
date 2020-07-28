package com.jishukezhan.http;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.io.FastByteArrayOutputStream;
import com.jishukezhan.core.io.IOUtil;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.CollectionUtil;
import com.jishukezhan.core.lang.Preconditions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class Response implements Closeable {

    /**
     * HTTP状态码
     */
    int status;

    /**
     * HTTP状态消息
     */
    String reason;

    /**
     * 响应头
     */
    Map<String, List<String>> headers;

    /**
     * 响应内容
     */
    ResponseBody body;

    /**
     * 原始HTTP请求
     */
    Request request;

    private Response(Builder builder) {
        this.status = builder.status;
        this.reason = builder.reason;
        this.headers = builder.headers;
        this.body = builder.body;
        this.request = builder.request;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * status code. ex {@code 200}
     * <p>
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html" >rfc2616</a>
     */
    public int status() {
        return status;
    }

    /**
     * Nullable and not set when using http/2
     * <p>
     * See https://github.com/http2/http2-spec/issues/202
     */
    public String reason() {
        return reason;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }

    public ResponseBody body() {
        return body;
    }

    public Request request() {
        return request;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("HTTP/1.1 ").append(status);
        if (reason != null) {
            builder.append(' ').append(reason);
        }
        builder.append('\n');

        if (CollectionUtil.isNotEmpty(headers)) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (CollectionUtil.isNotEmpty(entry.getValue())) {
                    for (String value : entry.getValue()) {
                        builder.append(entry.getKey()).append(": ").append(value).append('\n');
                    }
                }
            }
        }

        if (body != null) {
            builder.append('\n').append(body);
        }
        return builder.toString();
    }

    @Override
    public void close() {
        IOUtil.closeQuietly(body);
    }

    public static class Builder {

        /**
         * HTTP状态码
         */
        int status;

        /**
         * HTTP状态消息
         */
        String reason;

        /**
         * 响应头
         */
        Map<String, List<String>> headers;

        /**
         * 响应内容
         */
        ResponseBody body;

        /**
         * 原始HTTP请求
         */
        Request request;

        Builder() {
        }

        Builder(@NonNull Response source) {
            this.status = Preconditions.requireNonNull(source).status;
            this.reason = source.reason;
            this.headers = source.headers;
            this.body = source.body;
            this.request = source.request;
        }

        /**
         * @see Response#status
         */
        public Builder status(int status) {
            this.status = status;
            return this;
        }

        /**
         * @see Response#reason
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * @see Response#headers
         */
        public Builder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(ResponseBody body) {
            this.body = body;
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(InputStream inputStream, int length) {
            this.body = InputStreamResponseBody.create(inputStream, length);
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(byte[] data) {
            this.body = ByteArrayResponseBody.create(data);
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(String text, Charset charset) {
            this.body = ByteArrayResponseBody.create(text, charset);
            return this;
        }

        /**
         * @see Response#request
         */
        public Builder request(Request request) {
            this.request = Preconditions.requireNonNull(request, "request is required");
            return this;
        }


        public Response build() {
            return new Response(this);
        }
    }

    static class ByteArrayResponseBody implements ResponseBody {

        private final byte[] data;

        private ByteArrayResponseBody(@NonNull byte[] data) {
            this.data = data;
        }

        @Override
        public Integer length() {
            return data.length;
        }

        @Override
        public boolean isRepeatable() {
            return true;
        }

        @Override
        public InputStream byteStream() throws IoRuntimeException {
            return new ByteArrayInputStream(data);
        }

        @Override
        public Reader charStream(Charset charset) throws IoRuntimeException {
            return new BufferedReader(new InputStreamReader(byteStream(), charset));
        }

        /**
         * 将响应内容转为字符串，并且会自动关闭流
         *
         * @param charset 编码字符集，可空，
         * @return 响应字符串
         * @throws IoRuntimeException IO异常
         */
        @Override
        public String string(Charset charset) throws IoRuntimeException {
            return new String(data, charset);
        }

        @Override
        public void close() {
            // NOP
        }

        public static ResponseBody create(byte[] data) {
            return new ByteArrayResponseBody(Preconditions.requireNonNull(data));
        }

        public static ResponseBody create(@NonNull String text, @Nullable Charset charset) {
            return create(Preconditions.requireNotEmpty(text).getBytes(CharsetUtil.getCharset(charset)));
        }
    }

    static class InputStreamResponseBody implements ResponseBody {

        private final InputStream in;

        private final int length;

        private InputStreamResponseBody(@NonNull InputStream in, int length) {
            this.in = in;
            this.length = length;
        }


        /**
         * 字节的长度，可能为{@code null}
         *
         * @return 字节长度
         */
        @Override
        public Integer length() {
            return length;
        }

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public InputStream byteStream() throws IoRuntimeException {
            return in;
        }

        @Override
        public Reader charStream(Charset charset) throws IoRuntimeException {
            Charset encoding = CharsetUtil.getCharset(charset, CharsetUtil.UTF_8);
            return new BufferedReader(new InputStreamReader(in, encoding));
        }

        /**
         * 将响应内容转为字符串，并且会自动关闭流
         *
         * @param charset 编码字符集，可空，
         * @return 响应字符串
         * @throws IoRuntimeException IO异常
         */
        @Override
        public String string(Charset charset) throws IoRuntimeException {
            FastByteArrayOutputStream out = new FastByteArrayOutputStream();
            IOUtil.copy(charStream(charset), out);
            return out.toString();
        }

        @Override
        public void close() throws IOException {
            in.close();
        }

        public static ResponseBody create(@NonNull InputStream in, int length) {
            return new InputStreamResponseBody(Preconditions.requireNonNull(in), length);
        }

    }

}
