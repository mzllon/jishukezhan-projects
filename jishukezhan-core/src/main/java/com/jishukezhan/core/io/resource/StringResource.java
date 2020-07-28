package com.jishukezhan.core.io.resource;

import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.lang.CharsetUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;

public class StringResource implements Resource, Serializable {

    private final String data;

    private Charset charset;

    /**
     * 构造，使用UTF8编码
     *
     * @param data 资源数据
     */
    public StringResource(String data) {
        this(data, null);
    }

    /**
     * 构造，使用UTF8编码
     *
     * @param data    资源数据
     * @param charset 编码
     */
    public StringResource(String data, Charset charset) {
        this.data = data;
        this.charset = CharsetUtil.getCharset(charset);
    }

    /**
     * 返回解析后的{@code URL}，如果不适用则返回{@code null}
     *
     * @return URL
     * @throws IoRuntimeException IO异常包装类
     */
    @Override
    public URL getUrl() throws IoRuntimeException {
        return null;
    }

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public InputStream getInputStream() throws IoRuntimeException {
        return new ByteArrayInputStream(readBytes());
    }

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public long contentLength() throws IoRuntimeException {
        return data.length();
    }

    public byte[] readBytes() throws IoRuntimeException {
        return data.getBytes(charset);
    }

}
