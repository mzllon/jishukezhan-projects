package com.jishukezhan.servlet3.response;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.http.ContentType;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * HTTP Response工具类
 *
 * @author miles.tang
 */
public class ResponseUtil {

    private ResponseUtil() {
        throw new AssertionError("No com.jishukezhan.servlet3.ResponseUtil instances for you!");
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,默认采用{@code UTF-8}
     *
     * @param obj      对象
     * @param response HTTP响应对象
     */
    public static void writeJson(@NonNull Object obj, @NonNull HttpServletResponse response) {
        writeJson(JSONUtil.toJson(Preconditions.requireNonNull(obj)), response);
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,默认采用{@code UTF-8}
     *
     * @param json     json字符串
     * @param response HTTP响应对象
     */
    public static void writeJson(@NonNull String json, @NonNull HttpServletResponse response) {
        writeJson(json, null, response);
    }

    public static void writeJson(@NonNull Object obj, @Nullable Charset encoding, @NonNull HttpServletResponse response) {
        writeJson(JSONUtil.toJson(Preconditions.requireNonNull(obj)), encoding, response);
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,采用指定编码
     *
     * @param json     json字符串
     * @param encoding 指定编码,如果为空则使用{@code UTF-8}
     * @param response HTTP响应对象
     */
    public static void writeJson(@NonNull String json, @Nullable Charset encoding, @NonNull HttpServletResponse response) {
        Preconditions.requireNotEmpty(json, "json is null or empty");
        Preconditions.requireNonNull(response, "response == null");
        encoding = CharsetUtil.getCharset(encoding, CharsetUtil.UTF_8);
        write(json, ContentType.APPLICATION_JSON.charset(encoding), response);
    }

    private static void write(String data, ContentType contentType, HttpServletResponse response) {
        response.setCharacterEncoding(contentType.getCharset().name());
        response.setContentType(contentType.toString());
        try {
            response.getWriter().write(data);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
