package com.jishukezhan.servlet3.request;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.io.FastByteArrayOutputStream;
import com.jishukezhan.core.io.IOUtil;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.json.JSONUtil;
import com.jishukezhan.json.TypeRef;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public final class RequestUtil {

    /**
     * 获取用户的真正IP地址
     *
     * @param request request对象
     * @return 返回用户的IP地址
     * @see IpUtil#getClientIp(HttpServletRequest)
     */
    public static String getClientIp(HttpServletRequest request) {
        return IpUtil.getClientIp(request);
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容，并转为对应的JavaBean
     *
     * @param request HTTP请求对象
     * @param clazz   目标类型
     * @param <T>     泛型类型
     * @return 目标对象
     */
    public static <T> T read2Bean(@NonNull HttpServletRequest request, Class<T> clazz) {
        String body = read2Bean(request);
        return (StringUtil.isEmpty(body) ? null : JSONUtil.fromJson(body,
                Preconditions.requireNonNull(clazz, "clazz == null")));
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容，并转为对应的JavaBean
     *
     * @param request HTTP请求对象
     * @param typeRef 目标类型
     * @param <T>     泛型类型
     * @return 目标对象
     */
    public static <T> T read2Bean(@NonNull HttpServletRequest request, TypeRef<T> typeRef) {
        String body = read2Bean(request);
        return (StringUtil.isEmpty(body) ? null : JSONUtil.fromJson(body, typeRef.getType()));
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容
     *
     * @param request HTTP请求对象
     * @return body的内容
     */
    public static String read2Bean(@NonNull HttpServletRequest request) {
        int contentLength = Preconditions.requireNonNull(request.getContentLength(), "request == null");
        if (contentLength <= 0) {
            return null;
        }
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream(contentLength);
        try {
            IOUtil.copy(request.getInputStream(), outputStream);
            return outputStream.toString(CharsetUtil.forName(request.getCharacterEncoding()));
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
