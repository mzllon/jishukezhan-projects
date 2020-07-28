package com.jishukezhan.http.apache;

import com.jishukezhan.http.Client;
import com.jishukezhan.http.ClientBuilder;
import com.jishukezhan.http.Options;

public class ApacheHttpClientBuilder extends ClientBuilder {

    @Override
    public Client build(Options options) {
        return new ApacheHttpClient(options);
    }

}
