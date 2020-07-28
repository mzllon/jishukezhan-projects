package com.jishukezhan.servlet3.request;

import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.io.IOUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 比较完善的Request包装类：支持输入流重复读取，支持getParameter等方法调用
 *
 * @author miles.tang
 */
public class RepeatableHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] content;

    public RepeatableHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            this.content = IOUtil.readBytes(request.getInputStream());
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    @Override
    public BufferedReader getReader() {
        return IOUtil.getBufferedReader(getInputStream(), null);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArray = new ByteArrayInputStream(content);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArray.available() <= 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() throws IOException {
                return byteArray.read();
            }
        };
    }

}
