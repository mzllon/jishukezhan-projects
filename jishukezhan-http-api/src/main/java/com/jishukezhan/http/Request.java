package com.jishukezhan.http;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.lang.CollectionUtil;
import com.jishukezhan.core.lang.Preconditions;

import java.util.*;

/**
 * 请求对象
 *
 * @author miles.tang
 */
public final class Request {

    /**
     * 请求方法
     */
    private final Method method;

    /**
     * 请求地址和请求参数
     */
    private final String url;

    /**
     * 请求消息头
     */
    private final Map<String, List<String>> headers;

    /**
     * 请求内容
     */
    private final RequestBody body;

    Request(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.headers = Collections.unmodifiableMap(builder.headers);
        this.body = builder.body;
    }

    /**
     * 返回请求URL
     *
     * @return URL url
     */
    public String url() {
        return url;
    }

    /**
     * Request Headers.
     *
     * @return the request headers.
     */
    public Map<String, List<String>> headers() {
        return headers;
    }

    public Method method() {
        return method;
    }

    @Nullable
    public RequestBody body() {
        return body;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        /**
         * 请求方法
         */
        private Method method;

        /**
         * 请求地址和请求参数
         */
        private String url;

        /**
         * 请求消息头
         */
        private Map<String, List<String>> headers;

        /**
         * 请求内容
         */
        private RequestBody body;

        public Builder() {
            this.method = Method.GET;
            this.headers = new HashMap<>();
        }

        public Builder(Request request) {
            this.method = request.method;
            this.url = request.url;
            this.headers = new HashMap<>(request.headers);
            this.body = request.body;
        }

        public Builder method(@NonNull Method method) {
            this.method = Preconditions.requireNonNull(method, "method == null ");
            return this;
        }

        public Builder url(@NonNull String url) {
            this.url = Preconditions.requireNotEmpty(url, "url is null or empty");
            return this;
        }

        public Builder replaceHeader(String name, String value) {
            Preconditions.requireNotEmpty(name, "name is null or empty");
            Preconditions.requireNonNull(value, "value == null");

            List<String> values = new ArrayList<>();
            values.add(value);
            headers.put(name, values);
            return this;
        }

        public Builder addHeader(String name, String value) {
            Preconditions.requireNotEmpty(name, "name is null or empty");
            Preconditions.requireNonNull(value, "value == null");

            List<String> values = headers.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Builder headers(Map<String, List<String>> headers) {
            if (CollectionUtil.isNotEmpty(headers)) {
                for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                    List<String> values = this.headers.computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
                    values.addAll(entry.getValue());
                }
            }
            return this;
        }

        public Builder body(@Nullable RequestBody body) {
            return method(this.method, body);
        }

        public Builder method(Method method, @Nullable RequestBody body) {
            method(method);
            if (method == Method.GET || method == Method.HEAD) {
                // 不能有body
                if (body != null) {
                    throw new IllegalArgumentException("method " + method.name() + " must not have a request body");
                }
            } else if (method == Method.POST || method == Method.PUT || method == Method.PATCH) {
                if (body == null) {
                    throw new IllegalArgumentException("method " + method.name() + " must have a request body");
                }
            }
            this.body = body;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

}
