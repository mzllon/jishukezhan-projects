package com.jishukezhan.http;

import com.jishukezhan.core.support.CloneSupport;

public abstract class ClientBuilder extends CloneSupport<ClientBuilder> {

    public abstract Client build(Options options);

    static class Default extends ClientBuilder {

        @Override
        public Client build(Options options) {
            return new Client.DefaultClient();
        }

    }

}
