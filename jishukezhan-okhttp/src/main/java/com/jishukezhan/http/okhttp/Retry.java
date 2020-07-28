package com.jishukezhan.http.okhttp;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Retry implements Interceptor {

    /**
     * 最大重试次数
     */
    public int maxRetry;

    public Retry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        // 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
        int retryNum = 0;
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            response = chain.proceed(request);
        }

        return response;
    }

}
