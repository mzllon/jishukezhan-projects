package com.jishukezhan.core.http;

import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * URL Utilities
 *
 * @author miles.tang
 */
public class UrlUtil {

    private UrlUtil() {
        throw new AssertionError("No com.jishukezhan.core.http.UrlUtil instances for you!");
    }

    /**
     * url编码，字符集采用{@code UTF-8}
     *
     * @param text 待编码的内容
     * @return 编码后的url
     */
    public static String encode(@Nullable final String text) {
        return encode(text, CharsetUtil.UTF_8);
    }

    /**
     * url编码，指定字符集
     *
     * @param text    待编码的内容
     * @param charset 指定字符集
     * @return 编码后的url
     */
    public static String encode(@Nullable final String text, @Nullable Charset charset) {
        if (StringUtil.isEmpty(text)) {
            return StringUtil.EMPTY_STRING;
        }
        if (charset == null) {
            charset = CharsetUtil.UTF_8;
        }
        try {
            return URLEncoder.encode(text, charset.name());
        } catch (UnsupportedEncodingException e) {
            //never happen
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 为url增加请求参数
     *
     * @param url      原始的请求地址
     * @param name     参数名
     * @param value    参数值
     * @param encoding 编码
     * @return 返回追加了参数的地址
     * @since 2.0.0
     */
    public static String addParameters(String url, String name, String value, String encoding) {
        if (StringUtil.isEmpty(url)) {
            return StringUtil.EMPTY_STRING;
        }
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value)) {
            return url;
        }
        if (encoding == null) {
            encoding = CharsetUtil.UTF_8_VALUE;
        }
        try {
            String queryString = name + "=" + URLEncoder.encode(value, encoding);
            if (url.contains("?")) {
                return url + "&" + queryString;
            } else {
                return url + "?" + queryString;
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * URL解码，字符集采用{@code UTF-8}
     *
     * @param text 待解码的内容
     * @return 解码后的url
     */
    public static String decode(final String text) {
        return decode(text, CharsetUtil.UTF_8);
    }

    /**
     * URL解码，指定字符集
     *
     * @param text    待解码的内容
     * @param charset 指定字符集
     * @return 解码后的url
     */
    public static String decode(final String text, Charset charset) {
        if (StringUtil.isEmpty(text)) {
            return StringUtil.EMPTY_STRING;
        }
        if (charset == null) {
            charset = CharsetUtil.UTF_8;
        }
        try {
            return URLDecoder.decode(text, charset.name());
        } catch (UnsupportedEncodingException e) {
            //never happen
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 从URL中获取流
     *
     * @param url {@link URL}
     * @return InputStream流
     */
    public static InputStream openStream(URL url) {
        try {
            return Preconditions.requireNonNull(url).openStream();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 获得URL，常用于使用绝对路径时的情况
     *
     * @param file URL对应的文件对象
     * @return URL
     * @throws IoRuntimeException MalformedURLException
     */
    public static URL getUrl(File file) {
        try {
            return Preconditions.requireNonNull(file).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IoRuntimeException(e);
        }
    }

}
