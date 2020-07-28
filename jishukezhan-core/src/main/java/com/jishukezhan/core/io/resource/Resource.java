package com.jishukezhan.core.io.resource;

import com.jishukezhan.core.exceptions.IoRuntimeException;

import java.io.InputStream;
import java.net.URL;

/**
 * 各种资源的接口定义
 *
 * @author miles.tang
 */
public interface Resource {

    /**
     * 返回解析后的{@code URL}，如果不适用则返回{@code null}
     *
     * @return URL
     * @throws IoRuntimeException IO异常包装类
     */
    URL getUrl() throws IoRuntimeException;

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    InputStream getInputStream() throws IoRuntimeException;

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    long contentLength() throws IoRuntimeException;

}
