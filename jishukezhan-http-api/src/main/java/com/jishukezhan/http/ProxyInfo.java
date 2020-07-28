package com.jishukezhan.http;

import com.jishukezhan.annotation.NonNull;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyInfo {

    private String hostOrIP;

    private int port;

    private ProxyType type;

    private String username;

    private String password;

    ProxyInfo(String hostOrIP, int port, ProxyType type) {
        this.hostOrIP = hostOrIP;
        this.port = port;
        this.type = type;
    }

    ProxyInfo(ProxyType type, String hostOrIP, int port, String username, String password) {
        this.hostOrIP = hostOrIP;
        this.port = port;
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public Proxy.Type toJdkType() {
        switch (type) {
            case HTTP:
                return Proxy.Type.HTTP;
            case SOCKS:
                return Proxy.Type.SOCKS;
            case DIRECT:
                return Proxy.Type.DIRECT;
            default:
                throw new IllegalArgumentException("Invalid ProxyType:" + type);
        }
    }

    public Proxy toJdkProxy() {
        return new Proxy(toJdkType(), new InetSocketAddress(hostOrIP, port));
    }

    public String hostOrIP() {
        return hostOrIP;
    }

    public int port() {
        return port;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public ProxyType type() {
        return type;
    }

    public static ProxyInfo direct() {
        return new ProxyInfo(ProxyType.NONE, null, 0, null, null);
    }

    public static ProxyInfo http(@NonNull String hostOrIP, int port) {
        return new ProxyInfo(ProxyType.HTTP, hostOrIP, port, null, null);
    }

    public static ProxyInfo http(@NonNull String hostOrIP, int port, String username, String pwd) {
        return new ProxyInfo(ProxyType.HTTP, hostOrIP, port, username, pwd);
    }

    public static ProxyInfo socks(@NonNull String hostOrIP, int port) {
        return new ProxyInfo(ProxyType.SOCKS, hostOrIP, port, null, null);
    }

    public static ProxyInfo socks(@NonNull String hostOrIP, int port, String username, String pwd) {
        return new ProxyInfo(ProxyType.SOCKS, hostOrIP, port, username, pwd);
    }

    /**
     * Represents the proxy type.
     */
    public enum ProxyType {

        /**
         * No Proxy
         */
        NONE,
        /**
         * Represents a direct connection, or the absence of a proxy.
         */
        DIRECT,
        /**
         * Represents proxy for high level protocols such as HTTP or FTP.
         */
        HTTP,
        /**
         * Represents a SOCKS (V4 or V5) proxy.
         */
        SOCKS,

    }

}
