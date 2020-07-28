package com.jishukezhan.http.support;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.http.Client;
import com.jishukezhan.http.ClientFactory;
import com.jishukezhan.http.Options;
import com.jishukezhan.json.JSON;
import com.jishukezhan.json.JSONFactory;

/**
 * Ginger是生姜的意思，是我们日常常见的配料，
 */
public class Cilantro {

    private static class Holder {
        static final Cilantro INSTANCE = new Builder()
                .json(JSONFactory.create().build())
                .client(ClientFactory.get().build(Options.builder().build()))
                .build();
    }

    private final JSON json;

    private final Client client;

    private final Options options;

    /**
     * 解码404状态码
     */
    private boolean decode404;

    Cilantro(Builder builder) {
        this.json = builder.json;
        this.client = builder.client;
        this.options = builder.options;
    }

    public JSON getJson() {
        return json;
    }

    public Client getClient() {
        return client;
    }

    public Options getOptions() {
        return options;
    }

    public boolean isDecode404() {
        return decode404;
    }

    public void setDecode404(boolean decode404) {
        this.decode404 = decode404;
    }

    public static Cilantro getDefault() {
        return Holder.INSTANCE;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 发起GET请求
     *
     * @param url 地址，非空
     * @return {@linkplain GetRequest}
     */
    public static GetRequest get(@NonNull String url) {
        return new GetRequest(url);
    }

    /**
     * 发起BODY请求，默认采用{@code POST}提交
     *
     * @param url 地址，非空
     * @return {@linkplain BodyRequest}
     */
    public static BodyRequest body(@NonNull String url) {
        return new BodyRequest(url);
    }

    /**
     * 发起POST请求：application/x-www-form-urlencoded和multipart/form-data
     *
     * @param url 地址，非空
     * @return {@linkplain PostRequest}
     */
    public static PostRequest post(@NonNull String url) {
        return new PostRequest(url);
    }

    public static DownloadRequest downloadFile(@NonNull String url) {
        return new DownloadRequest(url);
    }

    public static HeadRequest head(@NonNull String url) {
        return new HeadRequest(url);
    }

    public static BodyRequest put(@NonNull String url) {
        return new BodyRequest(url).put();
    }

    public static DeleteRequest delete(@NonNull String url) {
        return new DeleteRequest(url);
    }

    public static ConnectRequest connect(@NonNull String url) {
        return new ConnectRequest(url);
    }

    public static OptionsRequest options(@NonNull String url) {
        return new OptionsRequest(url);
    }

    public static TraceRequest trace(@NonNull String url) {
        return new TraceRequest(url);
    }

    public static BodyRequest patch(@NonNull String url) {
        return new BodyRequest(url).patch();
    }

    public static class Builder {

        private JSON json;

        private Client client;

        private Options options;

        public Builder() {
        }

        public Builder(Cilantro source) {
            this.json = source.json;
            this.client = source.client;
            this.options = source.options;
        }

        /**
         * 指定JSON引擎
         *
         * @param json
         * @return
         */
        public Builder json(@Nullable JSON json) {
            this.json = json;
            return this;
        }

        public Builder client(Client client) {
            this.client = Preconditions.requireNonNull(client, "client == null");
            return this;
        }

        public Builder options(Options options) {
            this.options = Preconditions.requireNonNull(options, "options == null");
            return this;
        }

        public Cilantro build() {
            return new Cilantro(this);
        }

    }

}
