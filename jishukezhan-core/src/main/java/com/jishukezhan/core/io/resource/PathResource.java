package com.jishukezhan.core.io.resource;

import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.exceptions.URISyntaxRuntimeException;
import com.jishukezhan.core.lang.Preconditions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 基于{@linkplain Path}资源实现
 *
 * @author miles.tang
 */
public class PathResource extends UrlResource {

    private final Path path;

    public PathResource(Path path) {
        this.path = Preconditions.requireNonNull(path);
        try {
            url = this.path.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new IoRuntimeException(e);
        }
    }

    public PathResource(String path) {
        this.path = Paths.get(Preconditions.requireNotEmpty(path)).normalize();
        try {
            url = this.path.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new IoRuntimeException(e);
        }
    }

    public PathResource(URL url) {
        try {
            this.path = Paths.get(url.toURI());
            super.url = url;
        } catch (URISyntaxException e) {
            throw new URISyntaxRuntimeException(e);
        }
    }

    public final String getPath() {
        return path.toString();
    }

    public boolean exists() {
        return Files.exists(path);
    }

    public boolean isFile() {
        return true;
    }

    public File getFile() {
        return path.toFile();
    }

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public InputStream getInputStream() throws IoRuntimeException {
        if (!exists()) {
            throw new IoRuntimeException("Path [" + getPath() + "] not found");
        }
        if (Files.isDirectory(path)) {
            throw new IoRuntimeException("Path [" + getPath() + "] is a directory");
        }
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public long contentLength() throws IoRuntimeException {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
