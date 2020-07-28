package com.jishukezhan.http.support;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.lang.*;
import com.jishukezhan.http.*;
import com.jishukezhan.http.exceptions.CilantroStatusException;
import com.jishukezhan.json.TypeRef;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class RequestSupport<Req extends RequestSupport<Req>> {

    protected String url;

    protected Method method;

    /**
     * 存储请求头信息
     */
    private Map<String, String> headers;

    private Map<String, List<String>> queryParams;

    public RequestSupport() {
        this.headers = new LinkedHashMap<>();
        this.queryParams = new LinkedHashMap<>();
    }

    // region query parameters

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name 参数名
     * @param val  参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    public Req queryString(String name, Number val) {
        return queryString(name, val == null ? null : val.toString());
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name 参数名
     * @param val  参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    public Req queryString(String name, String val) {
        return queryString(name, val, false);
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name    参数名
     * @param val     参数值
     * @param replace 值为[@code true}则替换
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    public Req queryString(String name, Object val, boolean replace) {
        addParams(queryParams, name, val, replace);
        return (Req) this;
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param parameters 参数对
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    public Req queryString(Map<String, Object> parameters) {
        if (CollectionUtil.isNotEmpty(parameters)) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                queryString(entry.getKey(), entry.getValue(), false);
            }
        }
        return (Req) this;
    }

    // endregion

    // region header

    /**
     * 添加请求头信息
     *
     * @param name  请求头键名
     * @param value 请求头值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    public Req header(String name, String value) {
        if (StringUtil.hasLength(name) && null != value) {
            headers.put(name, value);
        }
        return (Req) this;
    }

    /**
     * 从请求头中移除键值
     *
     * @param name 请求头键名
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req removeHeader(String name) {
        if (StringUtil.hasLength(name)) {
            headers.remove(name);
        }
        return (Req) this;
    }

    public Response execute() {
        return execute(Cilantro.getDefault());
    }

    public Response execute(@NonNull Cilantro cilantro) {
        Preconditions.requireNonNull(cilantro, "cilantro == null");
        return cilantro.getClient().execute(genRequest(), options());
    }

    /**
     * 执行请求,并且将响应内容转换为字符串
     *
     * @return 响应内容
     */
    public String string() {
        return string(Cilantro.getDefault());
    }

    /**
     * 自定义{@linkplain Cilantro}执行请求,并且将响应内容转换为字符串
     *
     * @param cilantro 自定义客户端
     * @return 响应内容
     */
    public String string(@NonNull Cilantro cilantro) {
        Response response = execute(cilantro);
        if (response.status() >= 500) {
            String message = response.reason();
            if (StringUtil.isEmpty(message)) {
                message = response.body().string(CharsetUtil.UTF_8);
            }
            throw new CilantroStatusException(response.status(), message, response.request());
        }
        if (response.status() == 404 && !cilantro.isDecode404()) {
            throw new CilantroStatusException(404, "Not Found", response.request());
        }
        ResponseBody body = response.body();
        return body.string(CharsetUtil.UTF_8);
    }

    /**
     * 执行请求,并将响应内容转为Java Bean
     *
     * @param clazz 目标类
     * @param <T>   目标类的泛型
     * @return 响应内容
     */
    public <T> T bean(@NonNull Class<T> clazz) {
        return bean(clazz, Cilantro.getDefault());
    }

    /**
     * 执行请求,并将响应内容转为Java Bean
     * <p>本方法一般适用于目标类型本身就是泛型,比如List,Map等</p>
     *
     * @param typeRef 目标类包装
     * @param <T>     目标类的泛型
     * @return 响应内容
     */
    public <T> T bean(TypeRef<T> typeRef) {
        return bean(typeRef, Cilantro.getDefault());
    }

    /**
     * 执行请求,并将响应内容转为Java Bean
     * <p>本方法一般适用于目标类型本身就是泛型,比如List,Map等</p>
     *
     * @param typeRef  目标类包装
     * @param cilantro 自定义客户端
     * @param <T>      目标类的泛型
     * @return 响应内容
     */
    public <T> T bean(@NonNull TypeRef<T> typeRef, @NonNull Cilantro cilantro) {
        Preconditions.requireNonNull(typeRef, "typeRef == null");
        return bean(typeRef.getType(), cilantro);
    }

    /**
     * 执行请求,并将响应内容转为Java Bean
     *
     * @param clazz    目标类
     * @param cilantro 自定义客户端
     * @param <T>      目标类的泛型
     * @return 响应内容
     */
    public <T> T bean(@NonNull Class<T> clazz, @NonNull Cilantro cilantro) {
        Preconditions.requireNonNull(clazz, "clazz == null");
        return bean((Type) clazz, cilantro);
    }

    // 内部方法
    private <T> T bean(Type typeOfSrc, Cilantro cilantro) {
        String jsonStr = string(cilantro);
        if (StringUtil.hasLength(jsonStr)) {
            return cilantro.getJson().fromJson(jsonStr, typeOfSrc);
        }
        return null;
    }

    private Request genRequest() {
        Request.Builder builder = Request.builder().method(method);
        if (CollectionUtil.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        StringBuilder urlBuilder = new StringBuilder(url);
        if (CollectionUtil.isNotEmpty(queryParams)) {
            if (url.contains("?")) {
                urlBuilder.append(StringUtil.AMP);
            } else {
                urlBuilder.append('?');
            }
            for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                for (String val : entry.getValue()) {
                    urlBuilder.append(entry.getKey()).append(StringUtil.EQUALS).append(val).append(StringUtil.AMP);
                }
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            builder.url(urlBuilder.toString());
        } else {
            builder.url(url);
        }
        builder.body(genBody());
        return builder.build();
    }

    protected abstract RequestBody genBody();

    public Options options() {
        return null;
    }

    protected void addParams(Map<String, List<String>> appendParams, String name, Object val, boolean replace) {
        if (StringUtil.isEmpty(name)) {
            return;
        }
        if (!replace && val == null) {
            return;
        }

        List<String> values = appendParams.computeIfAbsent(name, k -> new LinkedList<>());
        if (replace) {
            values.clear();
        }
        if (val != null) {
            values.add(val.toString());
        }
    }

}
