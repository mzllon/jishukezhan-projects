package com.jishukezhan.core.utils;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.lang.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * GZIP Utilities
 *
 * @author miles.tang
 */
public class GzipUtil {

    /**
     * 测试流是否是Gzip
     * 流不会关闭并且会重置游标
     *
     * @param in 输入流
     * @return 如果是{@code gzipped则返回{@code true},否则返回{@code false}
     */
    public static boolean isGzipStream(@NonNull InputStream in) {
        try {
            Preconditions.requireNonNull(in, "in == null").mark(2);
            int b = in.read();
            int magic = in.read() << 8 | b;
            in.reset();
            return magic == GZIPInputStream.GZIP_MAGIC;
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
