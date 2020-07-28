package com.jishukezhan.core.io.resource;

import com.jishukezhan.core.exceptions.ResourceNotFoundRuntimeException;
import com.jishukezhan.core.lang.ClassLoaderUtil;
import com.jishukezhan.core.lang.Preconditions;

public class ClasspathResource extends UrlResource {

    private static final long serialVersionUID = 1990L;

    /**
     * path
     */
    private final String path;

    /**
     * 基于类加载器
     */
    private ClassLoader classLoader;

    /**
     * 基于类
     */
    private Class<?> clazz;

    public ClasspathResource(String path) {
        this(path, (ClassLoader) null);
    }

    public ClasspathResource(String path, ClassLoader classLoader) {
        this(path, classLoader, null);
    }

    public ClasspathResource(String path, Class<?> clazz) {
        this(path, null, clazz);
    }

    public ClasspathResource(String path, ClassLoader classLoader, Class<?> clazz) {
        this.path = Preconditions.requireNonNull(path);
        this.classLoader = classLoader == null ? ClassLoaderUtil.getDefaultClassLoader() : classLoader;
        this.clazz = clazz;
        initUrl();
    }

    /**
     * 根据给定资源初始化URL
     */
    private void initUrl() {
        if (clazz != null) {
            super.url = clazz.getResource(path);
        } else if (classLoader != null) {
            super.url = classLoader.getResource(path);
        } else {
            super.url = ClassLoader.getSystemResource(path);
        }
        if (super.url == null) {
            throw new ResourceNotFoundRuntimeException("resource[" + path + "] not found!");
        }
    }

    /**
     * 返回路径
     *
     * @return 返回URL路径
     */
    @Override
    public String toString() {
        return (path == null) ? super.toString() : "classpath:" + path;
    }

}
