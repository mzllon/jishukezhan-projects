package com.jishukezhan.http.okhttp;

import com.jishukezhan.http.Client;
import com.jishukezhan.http.ClientBuilder;
import com.jishukezhan.http.Options;

public class OkHttpClientBuilder extends ClientBuilder {

    @Override
    public Client build(Options options) {
        return new OkHttpClient(options);
    }

}
