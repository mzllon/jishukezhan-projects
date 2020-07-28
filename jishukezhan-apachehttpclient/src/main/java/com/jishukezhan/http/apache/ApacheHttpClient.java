package com.jishukezhan.http.apache;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.io.FastByteArrayOutputStream;
import com.jishukezhan.core.io.IOUtil;
import com.jishukezhan.core.lang.CharsetUtil;
import com.jishukezhan.core.lang.CollectionUtil;
import com.jishukezhan.core.lang.Preconditions;
import com.jishukezhan.core.lang.StringUtil;
import com.jishukezhan.http.*;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于{@code Apache HttpClient}包装实现
 *
 * @author miles.tang
 */
public class ApacheHttpClient implements Client {
    private static final String ACCEPT_HEADER_NAME = "Accept";

    /**
     * The HTTP Content-Length header field name.
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    private final HttpClient httpClient;

    public ApacheHttpClient() {
        this((Options) null);
    }

    public ApacheHttpClient(@NonNull HttpClient httpClient) {
        this.httpClient = Preconditions.requireNonNull(httpClient, "httpClient == null");
    }

    public ApacheHttpClient(@Nullable Options options) {
        this.httpClient = build(options, null);
    }

    HttpClient build(@Nullable Options options, @Nullable HttpClient httpClient) {
        if (options == null) {
            return HttpClientBuilder.create().build();
        } else {
            HttpClientBuilder builder = HttpClientBuilder.create();
            RequestConfig.Builder customBuilder = ((httpClient instanceof Configurable) ?
                    RequestConfig.copy(((Configurable) httpClient).getConfig()) : RequestConfig.custom())
                    .setConnectTimeout(options.connectTimeoutMillis())
                    .setSocketTimeout(options.readTimeoutMillis())
                    .setRedirectsEnabled(options.followRedirects());

            // 代理
            ProxyInfo proxyInfo = options.proxyInfo();
            if (proxyInfo != null) {
                builder.setProxy(new HttpHost(proxyInfo.hostOrIP(), proxyInfo.port(), proxyInfo.type().name()));

                if (StringUtil.haveAnyLength(proxyInfo.username(), proxyInfo.password())) {
                    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyInfo.username(), proxyInfo.password()));
                    builder.setDefaultCredentialsProvider(credentialsProvider);
                }
            }

            // 重试机制
            if (options.retryCount() > 0) {
                builder.setRetryHandler(new DefaultHttpRequestRetryHandler(options.retryCount()));
            }

            builder.setDefaultRequestConfig(customBuilder.build());
            return builder.build();
        }
    }

    @Override
    public Response execute(Request request, Options options) throws IoRuntimeException {
        try {
            HttpUriRequest httpUriRequest = toHttpUriRequest(request);
            HttpClient httpClientScoped;

            if (options == null) {
                httpClientScoped = httpClient;
            } else {
                httpClientScoped = build(options, httpClient);
            }
            HttpResponse httpResponse = httpClientScoped.execute(httpUriRequest);
            return toApiResponse(httpResponse, request);
        } catch (URISyntaxException e) {
            throw new IoRuntimeException("URL '" + request.url() + "' couldn't be parsed into a URI", e);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    private HttpUriRequest toHttpUriRequest(Request request) throws URISyntaxException {
        RequestBuilder requestBuilder = RequestBuilder.create(request.method().name());

        requestBuilder.setUri(new URIBuilder(request.url()).build());

        // request headers
        boolean hasAcceptHeader = false;
        String headerName;
        for (Map.Entry<String, List<String>> entry : request.headers().entrySet()) {
            headerName = entry.getKey();
            if (headerName.equalsIgnoreCase(ACCEPT_HEADER_NAME)) {
                hasAcceptHeader = true;
            }

            if (StringUtil.equalsIgnoreCase(headerName, CONTENT_LENGTH)) {
                // The 'Content-Length' header is always set by the Apache client and it
                // doesn't like us to set it as well.
                continue;
            }

            for (String value : entry.getValue()) {
                requestBuilder.addHeader(headerName, value);
            }
        }
        if (!hasAcceptHeader) {
            requestBuilder.addHeader(ACCEPT_HEADER_NAME, "*/*");
        }

        // request body
        if (request.body() != null) {
            ContentType contentType = getContentType(request);
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                List<NameValuePair> nvpList = formBody.getItems().stream()
                        .map(item -> new BasicNameValuePair(item.getName(), item.getValue()))
                        .collect(Collectors.toList());
                requestBuilder.setEntity(new UrlEncodedFormEntity(nvpList, formBody.getCharset()));
            } else if (request.body() instanceof MultipartBody) {
                MultipartBody multipartBody = (MultipartBody) request.body();
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                        .setBoundary(multipartBody.getBoundary())
                        .setCharset(multipartBody.getCharset())
                        .setContentType(contentType)
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                for (MultipartBody.Part part : multipartBody.getParts()) {
                    ContentType partContentType = ContentType.create(part.getContentType().toString());
                    if (part.getFile() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getFile());
                    } else if (part.getIn() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getIn(), partContentType, part.getValue());
                    } else if (part.getBody() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getBody().getData(),
                                partContentType, part.getValue());
                    } else if (part.getValue() != null) {
                        multipartEntityBuilder.addTextBody(part.getName(), part.getValue(), partContentType);
                    }
                }
                requestBuilder.setEntity(multipartEntityBuilder.build());
            } else {
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(request.body().getData());
                if (request.body().contentType() != null) {
                    byteArrayEntity.setContentType(contentType.toString());
                }
                requestBuilder.setEntity(byteArrayEntity);
            }
        } else {
            requestBuilder.setEntity(new ByteArrayEntity(new byte[0]));
        }
        return requestBuilder.build();
    }

    private ContentType getContentType(Request request) {
        ContentType contentType = null;
        RequestBody body = request.body();
        if (body != null) {
            contentType = ContentType.parse(body.contentType().toString());
        }
        if (contentType == null) {
            for (Map.Entry<String, List<String>> entry : request.headers().entrySet()) {
                if (entry.getKey().equalsIgnoreCase("Content-Type")) {
                    List<String> values = entry.getValue();
                    if (CollectionUtil.isNotEmpty(values)) {
                        contentType = ContentType.parse(values.get(0));
                        if (contentType.getCharset() == null) {
                            contentType = contentType.withCharset(CharsetUtil.UTF_8);
                        }
                        break;
                    }
                }
            }
        }
        return contentType;
    }

    private Response toApiResponse(HttpResponse httpResponse, Request request) {
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        String reason = statusLine.getReasonPhrase();

        Map<String, List<String>> headers = new LinkedHashMap<>();
        for (Header header : httpResponse.getAllHeaders()) {
            headers.computeIfAbsent(header.getName(), k -> new ArrayList<>()).add(header.getValue());
        }

        return Response.builder()
                .status(statusCode)
                .reason(reason)
                .headers(headers)
                .request(request)
                .body(toBody(httpResponse))
                .build();
    }

    private ResponseBody toBody(HttpResponse httpResponse) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return null;
        }
        return new ResponseBody() {
            @Override
            public Integer length() {
                long contentLength = entity.getContentLength();
                return contentLength >= 0 && contentLength <= Integer.MAX_VALUE ? (int) contentLength : null;
            }

            @Override
            public boolean isRepeatable() {
                return entity.isRepeatable();
            }

            @Override
            public InputStream byteStream() throws IoRuntimeException {
                try {
                    return entity.getContent();
                } catch (IOException e) {
                    throw new IoRuntimeException(e);
                }
            }

            @Override
            public Reader charStream(Charset charset) throws IoRuntimeException {
                return new InputStreamReader(byteStream(), getCharset(charset));
            }

            @Override
            public String string(Charset charset) throws IoRuntimeException {
                try {
                    FastByteArrayOutputStream out = new FastByteArrayOutputStream();
                    IOUtil.copy(charStream(charset), out);
                    return out.toString();
                } finally {
                    try {
                        close();
                    } catch (IOException e) {
                        //ignore
                    }
                }
            }

            @Override
            public void close() throws IOException {
                EntityUtils.consume(entity);
            }

            @NonNull
            Charset getCharset(Charset charset) {
                Charset resultEncoding = charset;
                if (resultEncoding == null) {
                    ContentType contentType = ContentType.get(entity);
                    resultEncoding = contentType.getCharset();
                }
                if (resultEncoding == null) {
                    resultEncoding = CharsetUtil.UTF_8;
                }
                return resultEncoding;
            }

        };
    }

}
