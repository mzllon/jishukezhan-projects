package com.jishukezhan.http.support;

import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.http.ContentType;
import com.jishukezhan.core.lang.CollectionUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.http.FormBody;
import com.jishukezhan.http.Method;
import com.jishukezhan.http.MultipartBody;
import com.jishukezhan.http.RequestBody;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * POST提交：支持 application/x-www-form-urlencoded和multipart/form-data
 *
 * @author miles.tang
 */
public class PostRequest extends RequestSupport<PostRequest> {

    private List<MultipartBody.Part> parts;

    private String boundary;

    protected Map<String, List<String>> postParams;

    public PostRequest(String url) {
        this.url = Preconditions.requireNotEmpty(url, "url == null");
        this.method = Method.POST;
        this.postParams = new LinkedHashMap<>();
        this.parts = new LinkedList<>();
    }

    /**
     * 设置提交的请求参数及其值
     *
     * @param name  参数名
     * @param value 参数值
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, String value) {
        return param(name, value, false);
    }

    /**
     * 设置提交的请求参数及其值
     *
     * @param name    参数名
     * @param value   参数值
     * @param replace 值为[@code true}则替换处理
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, String value, boolean replace) {
        addParams(postParams, name, value, replace);
        return this;
    }

    public PostRequest param(String name, String value, ContentType contentType) {
        if (StringUtil.isEmpty(name)) {
            return this;
        }
        if (value == null) {
            return this;
        }
        parts.add(MultipartBody.Part.create(name, value, contentType));
        return this;
    }

    /**
     * 设置提交的请求参数及其值
     *
     * @param parameters 键值对列表
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(Map<String, String> parameters) {
        if (CollectionUtil.isNotEmpty(parameters)) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                param(entry.getKey(), entry.getValue(), false);
            }
        }
        return this;
    }

    /**
     * 自定义分隔符
     *
     * @param boundary 义分隔符
     * @return {@linkplain PostRequest}
     */
    public PostRequest boundary(String boundary) {
        this.boundary = Preconditions.requireNotEmpty(boundary, "boundary is null or empty");
        return this;
    }

    /**
     * 设置提交的文件
     *
     * @param name       参数名
     * @param uploadFile 上传的文件
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, File uploadFile) {
        return param(name, uploadFile, uploadFile.getName());
    }

    /**
     * 设置提交的文件
     *
     * @param name       参数名
     * @param uploadFile 上传的文件
     * @param filename   文件名
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(@Nullable String name, @Nullable File uploadFile, @Nullable String filename) {
        if (StringUtil.isEmpty(name)) {
            return this;
        }
        if (uploadFile == null) {
            return this;
        }
        if (StringUtil.isEmpty(filename)) {
            return this;
        }

        parts.add(MultipartBody.Part.create(name, filename, uploadFile));
        return this;
    }

    /**
     * 设置提交的文件
     *
     * @param name     参数名
     * @param in       上传数据流
     * @param filename 文件名
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(@Nullable String name, @Nullable InputStream in, @Nullable String filename) {
        if (StringUtil.isEmpty(name)) {
            return this;
        }
        if (in == null) {
            return this;
        }
        if (StringUtil.isEmpty(filename)) {
            return this;
        }

        parts.add(MultipartBody.Part.create(name, filename, in));

        return this;
    }

    /**
     * 设置提交的文件二进制内容
     *
     * @param name     参数名
     * @param data     字节内容
     * @param filename 文件名
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, byte[] data, String filename) {
        if (StringUtil.isEmpty(name)) {
            return this;
        }
        if (data == null) {
            return this;
        }
        if (StringUtil.isEmpty(filename)) {
            return this;
        }
        return param(name, new ByteArrayInputStream(data), filename);
    }

    @Override
    protected RequestBody genBody() {
        if (CollectionUtil.isNotEmpty(parts)) {
            MultipartBody.Builder builder = MultipartBody.builder()
                    .boundary(this.boundary);
            for (MultipartBody.Part part : parts) {
                builder.add(part);
            }
            if (CollectionUtil.isNotEmpty(postParams)) {
                for (Map.Entry<String, List<String>> entry : postParams.entrySet()) {
                    for (String val : entry.getValue()) {
                        builder.add(entry.getKey(), val);
                    }
                }
            }
            return builder.build();
        } else if (CollectionUtil.isNotEmpty(postParams)) {
            FormBody.Builder builder = FormBody.builder();
            for (Map.Entry<String, List<String>> entry : postParams.entrySet()) {
                for (String val : entry.getValue()) {
                    builder.add(entry.getKey(), val);
                }
            }
            return builder.build();
        }
        return RequestBody.create(null, new byte[0]);
    }

}
