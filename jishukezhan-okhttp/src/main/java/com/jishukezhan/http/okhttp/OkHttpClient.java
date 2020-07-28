package com.jishukezhan.http.okhttp;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.http.ContentType;
import com.jishukezhan.core.io.FastByteArrayOutputStream;
import com.jishukezhan.core.io.IOUtil;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.CollectionUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.http.*;
import okhttp3.Credentials;
import okhttp3.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于{@code okhttp}提供的{@code Client}
 *
 * @author miles.tang
 */
public class OkHttpClient implements Client {

    private final okhttp3.OkHttpClient delegate;

    public OkHttpClient() {
        this(new okhttp3.OkHttpClient());
    }

    public OkHttpClient(@NonNull okhttp3.OkHttpClient delegate) {
        this.delegate = Preconditions.requireNonNull(delegate);
    }

    public OkHttpClient(@NonNull Options options) {
        Preconditions.requireNonNull(options, "options == null");

        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
                // 连接超时
                .connectTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                // 读取超时
                .readTimeout(options.readTimeoutMillis(), TimeUnit.MILLISECONDS)
                // 写出超时
                .writeTimeout(options.writeTimeoutMillis(), TimeUnit.MILLISECONDS)
                // 3XX自动跳转
                .followRedirects(options.followRedirects());
//        // debugger
//        DebugLoggingInterceptor debugLoggingInterceptor = DebugLoggingInterceptor.INSTANCE;
//        debugLoggingInterceptor.setLoggingLevel(DebugLoggingInterceptor.Level.ALL);
//        builder.addNetworkInterceptor(debugLoggingInterceptor);

        // 代理
        ProxyInfo proxyInfo = options.proxyInfo();
        if (proxyInfo != null) {
            builder.proxy(proxyInfo.toJdkProxy());
            if (StringUtil.haveAnyLength(proxyInfo.username(), proxyInfo.password())) {
                builder.proxyAuthenticator((route, response) -> response.request().newBuilder()
                        .header("Proxy-Authorization", Credentials.basic(proxyInfo.username(), proxyInfo.password()))
                        .build());
            }
        }

        // 重试机制
        if (options.retryCount() > 0) {
            builder.addNetworkInterceptor(new Retry(options.retryCount()));
        }

        this.delegate = builder.build();
    }

    /**
     * 执行HTTP请求
     *
     * @param request 请求对象
     * @param options 请求选项
     * @return 执行结果
     * @throws IoRuntimeException HTTP请求异常¬
     */
    @Override
    public Response execute(Request request, Options options) throws IoRuntimeException {
        okhttp3.OkHttpClient okHttpClientScoped = null;
        if (options != null) {
            okhttp3.OkHttpClient.Builder builder = delegate.newBuilder();
            if (delegate.connectTimeoutMillis() != options.connectTimeoutMillis() ||
                    delegate.readTimeoutMillis() != options.readTimeoutMillis() ||
                    delegate.writeTimeoutMillis() != options.writeTimeoutMillis() ||
                    delegate.followRedirects() != options.followRedirects()) {
                builder.connectTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .readTimeout(options.readTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .writeTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .followRedirects(options.followRedirects())
                        .build();
            }
            ProxyInfo proxyInfo = options.proxyInfo();
            if (proxyInfo != null) {
                builder.proxy(proxyInfo.toJdkProxy());
                if (StringUtil.haveAnyLength(proxyInfo.username(), proxyInfo.password())) {
                    builder.proxyAuthenticator((route, response) -> response.request().newBuilder()
                            .header("Proxy-Authorization", Credentials.basic(proxyInfo.username(), proxyInfo.password()))
                            .build());
                }
            }
            if (options.retryCount() > 0) {
                builder.addNetworkInterceptor(new Retry(options.retryCount()));
            }
            okHttpClientScoped = builder.build();
        } else {
            okHttpClientScoped = delegate;
        }
        okhttp3.Request okRequest = toOkHttpRequest(request);
        try {
            okhttp3.Response okResponse = okHttpClientScoped.newCall(okRequest).execute();
            return toHttpResponse(okResponse, request).newBuilder().request(request).build();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    private static okhttp3.Request toOkHttpRequest(Request input) {
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        requestBuilder.url(input.url());

        ContentType contentType = null;
        if (input.body() != null) {
            contentType = input.body().contentType();
        }

        // header
        boolean hasAcceptHeader = false;
        for (Map.Entry<String, List<String>> entry : input.headers().entrySet()) {
            if (StringUtil.equalsIgnoreCase(entry.getKey(), "Accept")) {
                hasAcceptHeader = true;
            }

            for (String value : entry.getValue()) {
                requestBuilder.addHeader(entry.getKey(), value);
                if (StringUtil.equalsIgnoreCase("Content-Type", entry.getKey())) {
                    contentType = ContentType.parse(value);
                }
            }
        }
        // Some servers choke on the default accept string.
        if (!hasAcceptHeader) {
            requestBuilder.addHeader("Accept", "*/*");
        }

        MediaType okhttp3MediaType = contentType == null ? null : MediaType.parse(contentType.toString());

        // process body
        boolean isMethodWithBody = Method.POST == input.method() || Method.PUT == input.method() || Method.PATCH == input.method();
        if (isMethodWithBody) {
            requestBuilder.removeHeader("Content-Type");
        }
        if (input.body() == null) {
            if (isMethodWithBody) {
                requestBuilder.method(input.method().name(), okhttp3.RequestBody.create(okhttp3MediaType, new byte[0]));
            } else {
                requestBuilder.method(input.method().name(), null);
            }
        } else if (input.body() instanceof FormBody) {
            FormBody formBody = (FormBody) input.body();
            okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder(formBody.getCharset());
            for (FormBody.Item item : formBody.getItems()) {
                builder.add(item.getName(), item.getValue());
            }
            requestBuilder.method(input.method().name(), builder.build());
        } else if (input.body() instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) input.body();
            okhttp3.MultipartBody.Builder builder;
            if (StringUtil.hasLength(multipartBody.getBoundary())) {
                builder = new okhttp3.MultipartBody.Builder(multipartBody.getBoundary());
            } else {
                builder = new okhttp3.MultipartBody.Builder();
            }
            builder.setType(okhttp3MediaType);

            if (CollectionUtil.isEmpty(multipartBody.getParts())) {
                builder.addPart(okhttp3.RequestBody.create(okhttp3.MultipartBody.FORM, new byte[0]));
            } else {
                for (MultipartBody.Part part : multipartBody.getParts()) {
                    okhttp3.MediaType partMediaType = okhttp3.MediaType.parse(part.getContentType().toString());
                    if (part.getFile() != null) {
                        builder.addFormDataPart(part.getName(), part.getValue(),
                                okhttp3.RequestBody.create(partMediaType, part.getFile()));
                    } else if (part.getIn() != null) {
                        builder.addFormDataPart(part.getName(), part.getValue(),
                                okhttp3.RequestBody.create(partMediaType, RequestBody.create(null, part.getIn()).getData()));
                    } else if (part.getBody() != null) {
                        builder.addFormDataPart(part.getName(), part.getValue(),
                                okhttp3.RequestBody.create(partMediaType, part.getBody().getData()));
                    } else if (part.getValue() != null) {
                        builder.addFormDataPart(part.getName(), null,
                                okhttp3.RequestBody.create(partMediaType, part.getValue()));
                    }
                }
            }
            requestBuilder.method(input.method().name(), builder.build());
        } else {
            requestBuilder.method(input.method().name(), okhttp3.RequestBody.create(okhttp3MediaType, input.body().getData()));
        }
        return requestBuilder.build();
    }

    private static Response toHttpResponse(okhttp3.Response okResponse, Request input) {
        return Response.builder()
                .status(okResponse.code())
                .reason(okResponse.message())
                .request(input)
                .headers(okResponse.headers().toMultimap())
                .body(toBody(okResponse))
                .build();
    }

    private static ResponseBody toBody(final okhttp3.Response okResponse) {
        okhttp3.ResponseBody okBody = okResponse.body();
        if (okBody == null) {
            return null;
        }
        long contentLength = okBody.contentLength();
        if (contentLength == 0) {
            okBody.close();
            return null;
        }
        final Integer length = contentLength > Integer.MAX_VALUE ? null : (int) contentLength;
        return new ResponseBody() {
            @Override
            public Integer length() {
                return length;
            }

            @Override
            public boolean isRepeatable() {
                return false;
            }

            @Override
            public InputStream byteStream() throws IoRuntimeException {
                return okBody.byteStream();
            }

            @Override
            public Reader charStream(Charset charset) throws IoRuntimeException {
                return okBody.charStream();
            }

            @Override
            public String string(Charset charset) throws IoRuntimeException {
                if (okResponse.code() == 404 || okResponse.code() == 204) {
                    return null;
                }

                okhttp3.MediaType okMediaType = okBody.contentType();
                Charset encoding = null;
                if (okMediaType != null) {
                    encoding = okMediaType.charset(charset);
                }
                if (encoding == null) {
                    encoding = CharsetUtil.UTF_8;
                }

                InputStream in = null;
                try {
                    in = byteStream();
                    FastByteArrayOutputStream out = new FastByteArrayOutputStream();
                    IOUtil.copy(in, out);
                    return out.toString(encoding);
                } finally {
                    IOUtil.closeQuietly(in);
                }
            }

            @Override
            public void close() {
                okBody.close();
            }
        };

    }

}
