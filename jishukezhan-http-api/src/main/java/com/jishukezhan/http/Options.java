package com.jishukezhan.http;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.lang.Preconditions;

import java.util.concurrent.TimeUnit;

/**
 * https://github.com/Arronlong/httpclientutil/blob/master/src/main/java/com/arronlong/httpclientutil/builder/HCB.java
 * https://gitee.com/m310851010/httpkit/blob/master/pom.xml
 * 配置项
 *
 * @author miles.tang
 */
public class Options {

    /**
     * 连接超时时间,单位毫秒
     */
    private int connectTimeoutMillis;

    /**
     * 读取超时时间,单位毫秒
     */
    private int readTimeoutMillis;

    /**
     * 写出超时时间,单位毫秒
     */
    private int writeTimeoutMillis;

    /**
     * 是否支持3xx跳转
     */
    private boolean followRedirects;

    /**
     * 重试（如果请求是幂等的，就再次尝试）
     */
    private int retryCount;

    /**
     * 代理
     */
    private ProxyInfo proxyInfo;

    private SSLConfig sslConfig;

    Options(Builder builder) {
        this.connectTimeoutMillis = builder.connectTimeoutMillis;
        this.readTimeoutMillis = builder.readTimeoutMillis;
        this.writeTimeoutMillis = builder.writeTimeoutMillis;
        this.followRedirects = builder.followRedirects;
        this.retryCount = builder.retryCount;
        this.proxyInfo = builder.proxyInfo;
    }

    public int connectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public int readTimeoutMillis() {
        return readTimeoutMillis;
    }

    public int writeTimeoutMillis() {
        return writeTimeoutMillis;
    }

    public boolean followRedirects() {
        return followRedirects;
    }

    public int retryCount() {
        return retryCount;
    }

    public ProxyInfo proxyInfo() {
        return proxyInfo;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class SSLConfig {

    }

    public static class Builder {

        /**
         * 连接超时时间,单位毫秒
         */
        private int connectTimeoutMillis;

        /**
         * 读取超时时间,单位毫秒
         */
        private int readTimeoutMillis;

        /**
         * 写出超时时间,单位毫秒
         */
        private int writeTimeoutMillis;

        /**
         * 是否支持3xx跳转
         */
        private boolean followRedirects;

        /**
         * 重试（如果请求是幂等的，就再次尝试）
         */
        private int retryCount;

        /**
         * 代理
         */
        private ProxyInfo proxyInfo;

        Builder() {
            this.connectTimeoutMillis = 1000 * 10;
            this.readTimeoutMillis = 1000 * 10;
            this.writeTimeoutMillis = 1000 * 60;
            this.followRedirects = true;
        }

        Builder(@NonNull Options source) {
            this.connectTimeoutMillis = Preconditions.requireNonNull(source).connectTimeoutMillis;
            this.readTimeoutMillis = source.readTimeoutMillis;
            this.writeTimeoutMillis = source.connectTimeoutMillis;
            this.followRedirects = source.followRedirects;
            this.retryCount = source.retryCount;
            this.proxyInfo = source.proxyInfo;
        }

        public Builder connectTimeoutMillis(int connectTimeoutMillis) {
            Preconditions.checkArgument(connectTimeoutMillis >= 0, "'connectTimeoutMillis' must than 0");
            this.connectTimeoutMillis = connectTimeoutMillis;
            return this;
        }

        public Builder connectTimeout(int connectTimeout, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(connectTimeoutMillis >= 0, "'connectTimeout' must than 0");
            Preconditions.requireNonNull(timeUnit, "'timeUnit' must not be null");
            this.connectTimeoutMillis = (int) timeUnit.toMillis(connectTimeout);
            return this;
        }

        public Builder readTimeoutMillis(int readTimeoutMillis) {
            Preconditions.checkArgument(readTimeoutMillis >= 0, "'readTimeoutMillis' must than 0");
            this.readTimeoutMillis = readTimeoutMillis;
            return this;
        }

        public Builder readTimeout(int readTimeout, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(readTimeout >= 0, "'readTimeout' must than 0");
            Preconditions.requireNonNull(timeUnit, "'timeUnit' must not be null");
            this.readTimeoutMillis = (int) timeUnit.toMillis(readTimeout);
            return this;
        }

        public Builder writeTimeoutMillis(int writeTimeoutMillis) {
            Preconditions.checkArgument(writeTimeoutMillis >= 0, "'writeTimeoutMillis' must than 0");
            this.writeTimeoutMillis = writeTimeoutMillis;
            return this;
        }

        public Builder writeTimeout(int writeTimeout, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(writeTimeout >= 0, "'writeTimeout' must than 0");
            Preconditions.requireNonNull(timeUnit, "'timeUnit' must not be null");
            this.writeTimeoutMillis = (int) timeUnit.toMillis(writeTimeout);
            return this;
        }

        public Builder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        /**
         * 设置重试的次数，0则不会重置
         *
         * @param retryCount 重试次数，必须不小于0
         * @return {@linkplain Builder}
         */
        public Builder retryCount(int retryCount) {
            Preconditions.checkArgument(retryCount >= 0, "'retryCount' must than 0");
            this.retryCount = retryCount;
            return this;
        }

        /**
         * 设置HTTP代理信息
         *
         * @param hostOrIp 代理主机或IP
         * @param port     代理端口
         * @return {@linkplain Builder}
         */
        public Builder httpProxy(@NonNull String hostOrIp, int port) {
            Preconditions.requireNotEmpty(hostOrIp, "'hostOrIp' must not be null or empty");
            Preconditions.checkArgument(port > 0 && port <= 65535, "port between 1 and 65535");
            this.proxyInfo = ProxyInfo.http(hostOrIp, port);
            return this;
        }

        public Options build() {
            return new Options(this);
        }

    }

}
