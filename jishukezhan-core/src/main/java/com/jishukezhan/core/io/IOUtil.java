package com.jishukezhan.core.io;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.digest.MD5Util;
import com.jishukezhan.core.digest.ShaUtil;
import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.lang.*;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * IO Utilities
 *
 * @author miles.tang
 */
public class IOUtil {

    private IOUtil() {
        throw new AssertionError("No com.jishukezhan.core.io.IOUtil instances for you!");
    }

    /**
     * 文件结束标记
     */
    private static final int EOF = -1;

    /**
     * 默认缓存大小
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    /**
     * 默认中等缓存大小
     */
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 4096;
    /**
     * 默认大缓存大小
     */
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 8192;

    /**
     * 关闭<code>Closeable</code>,该方法等效于{@linkplain Closeable#close()}
     * <p>
     * 该方法主要用于finally块中，并且忽略所有的异常
     * </p>
     * Example code:
     * <pre>
     *   Closeable closeable = null;
     *   try {
     *       closeable = new FileReader("foo.txt");
     *       // process closeable
     *       closeable.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(closeable);
     *   }
     * </pre>
     *
     * @param closeable the object to close, may be null or already closed
     */
    public static void closeQuietly(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * 流的md5，结果由16进制字符串返回
     *
     * @param in 输入流
     * @return md5之后的16进制字符串
     */
    public static String md5Hex(final InputStream in) {
        return MD5Util.digestHex(in);
    }

    /**
     * 流的SHA-1，结果由16进制字符串返回
     *
     * @param in 输入流
     * @return SHA-1之后的16进制字符串
     */
    public static String sha1Hex(final FileInputStream in) {
        return ShaUtil.sha1Hex(in);
    }

    //region ================ Read to Byte array ================

    /**
     * 将输入流转为字节数组，并且自动关闭输入流
     *
     * @param in 输入流
     * @return 如果转换成功则返回字节数组，否则返回{@code null}
     */
    public static byte[] readBytes(final InputStream in) {
        return readBytes(in, true);
    }

    /**
     * 将输入流转为字节数组
     *
     * @param in      输入流
     * @param closeIn 关闭流
     * @return 如果转换成功则返回字节数组，否则返回{@code null}
     */
    public static byte[] readBytes(final InputStream in, boolean closeIn) {
        if (in == null) {
            return null;
        }
        try {
            FastByteArrayOutputStream out = new FastByteArrayOutputStream();
            copy(in, out);
            return out.toByteArray();
        } finally {
            closeQuietly(in);
        }
    }

    /**
     * 从输入流中读取指定长度的字节数组
     *
     * @param in   输入流
     * @param size 读取长度，不能小于0
     * @return 返回读取的数据
     */
    public static byte[] readBytes(final InputStream in, final int size) {
        if (in == null) {
            return null;
        }
        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }
        if (size == 0) {
            return new byte[0];
        }

        byte[] data = new byte[size];
        int readLength;

        try {
            readLength = in.read(data);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }

        if (readLength > 0 && readLength < size) {
            byte[] buf = new byte[size];
            System.arraycopy(data, 0, buf, 0, readLength);
            return buf;
        }
        return data;
    }

    /**
     * 将<code>Reader</code>的内容转为字节数组，否转换异常则返回{@code null}
     *
     * @param reader read from
     * @return 转换异常时返回{@code null}，否则返回字节数组
     */
    public static byte[] readBytes(final Reader reader) {
        return readBytes(reader, (Charset) null);
    }

    /**
     * 将<code>Reader</code>的内容转为字节数组，否转换异常则返回{@code null}
     *
     * @param reader   read from
     * @param encoding 编码
     * @return 转换异常时返回{@code null}，否则返回字节数组
     */
    public static byte[] readBytes(final Reader reader, final String encoding) {
        return readBytes(reader, CharsetUtil.forName(encoding));
    }

    /**
     * 将<code>Reader</code>的内容转为字节数组，否转换异常则返回{@code null}
     *
     * @param reader   read from
     * @param encoding 编码
     * @return 转换异常时返回{@code null}，否则返回字节数组
     */
    public static byte[] readBytes(final Reader reader, final Charset encoding) {
        if (reader == null) {
            return null;
        }
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        if (!copy(reader, out, CharsetUtil.getCharset(encoding))) {
            return null;
        }
        return out.toByteArray();
    }

    //endregion

    //region ================ Read to String ================

    /**
     * 读取输入流并转为字符串
     *
     * @param in 输入流
     * @return 流内容
     */
    public static String toString(final InputStream in) {
        return toString(in, (String) null);
    }

    /**
     * 读取输入流并转为字符串
     *
     * @param in       输入流
     * @param encoding 字符编码
     * @return 流内容
     */
    public static String toString(InputStream in, String encoding) {
        return toString(in, CharsetUtil.forName(encoding));
    }

    /**
     * 读取输入流并转为字符串
     *
     * @param in       输入流
     * @param encoding 字符编码
     * @return 流内容
     */
    public static String toString(InputStream in, Charset encoding) {
        if (in == null) {
            return null;
        }
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        copy(in, out);
        return out.toString(CharsetUtil.getCharset(encoding));
    }

    /**
     * 读取输入流并转为字符串
     *
     * @param reader 输入流
     * @return 流内容
     */
    public static String toString(Reader reader) {
        return toString(reader, (Charset) null);
    }

    /**
     * 读取输入流并转为字符串
     *
     * @param reader   输入流
     * @param encoding 编码
     * @return 流内容
     */
    public static String toString(Reader reader, String encoding) {
        return toString(reader, CharsetUtil.forName(encoding));
    }

    /**
     * 读取输入流并转为字符串
     *
     * @param reader   输入流
     * @param encoding 编码
     * @return 流内容
     */
    public static String toString(Reader reader, Charset encoding) {
        if (reader == null) {
            return null;
        }
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        copy(reader, out);
        return out.toString(CharsetUtil.getCharset(encoding));
    }

    //endregion

    //region ================ Copy InputStream to OutputStream ================

    /**
     * 流拷贝
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败或流过大均返回-1
     */
    public static long copy(final InputStream in, final OutputStream out) {
        return copy(in, out, false);
    }

    /**
     * 流拷贝
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败或流过大均返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final boolean autoClose) {
        return copy(in, out, DEFAULT_LARGE_BUFFER_SIZE, autoClose);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1.
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final int bufferSize) {
        return copy(in, out, bufferSize, false);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1.
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final int bufferSize, final boolean autoClose) {
        return copy(in, out, new byte[bufferSize], autoClose);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1.
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final byte[] buffer) {
        return copy(in, out, buffer, false);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1.
     *
     * @param in     输入流
     * @param out    输出流
     * @param buffer 缓冲区
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final byte[] buffer, final boolean autoClose) {
        if (in == null) {
            return -1;
        }
        if (out == null) {
            return -1;
        }
        if (ArrayUtil.isEmpty(buffer)) {
            return -1;
        }
        long count = 0;
        int n;
        try {
            while (EOF != (n = in.read(buffer))) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
            return count;
        } catch (IOException e) {
            throw new IoRuntimeException("Copy bytes from a large InputStream to an OutputStream error", e);
        } finally {
            if (autoClose) {
                closeQuietly(out);
                closeQuietly(in);
            }
        }
    }

    /**
     * 拷贝文件流，使用NIO
     *
     * @param in  文件输入流
     * @param out 文件输出流
     * @return 拷贝的文件大小
     */
    public static long copy(final FileInputStream in, final FileOutputStream out) {
        return copy(in, out, false);
    }

    /**
     * 拷贝文件流，使用NIO
     *
     * @param in        文件输入流
     * @param out       文件输出流
     * @param autoClose 拷贝完毕后是否关闭输入/输出流
     * @return 拷贝的文件大小
     */
    public static long copy(final FileInputStream in, final FileOutputStream out, final boolean autoClose) {
        if (in == null) {
            return -1;
        }
        if (out == null) {
            return -1;
        }

        FileChannel inFC = in.getChannel();
        FileChannel outFC = out.getChannel();

        try {
            return inFC.transferTo(0, inFC.size(), outFC);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            if (autoClose) {
                closeQuietly(outFC);
                closeQuietly(out);
                closeQuietly(inFC);
                closeQuietly(in);
            }
        }
    }

    //endregion

    //region ================ Copy InputStream to Writer ================

    /**
     * 将输入流的字节数组转换为<code>Writer</code>字符内容，使用系统默认编码。
     *
     * @param in     字节输入流
     * @param writer 字符输出流
     * @return 拷贝成功则返回{@code true},否则返回{@code false}
     */
    public static boolean copy(final InputStream in, final Writer writer) {
        return copy(in, writer, Charset.defaultCharset());
    }

    /**
     * 将输入流的字节数组转换为<code>Writer</code>字符内容，使用系统默认编码。
     *
     * @param in            字节输入流
     * @param writer        字符输出流
     * @param inputEncoding 字符编码，如果为空则使用平台默认编码
     * @return 拷贝成功则返回{@code true},否则返回{@code false}
     */
    public static boolean copy(InputStream in, Writer writer, final String inputEncoding) {
        return copy(in, writer, StringUtil.isEmpty(inputEncoding) ? Charset.defaultCharset() : Charset.forName(inputEncoding));
    }

    /**
     * 将输入流的字节数组转换为<code>Writer</code>字符内容，使用系统默认编码。
     *
     * @param in            字节输入流
     * @param writer        字符输出流
     * @param inputEncoding 输入流的字符编码，如果为空则使用平台默认编码
     * @return 拷贝成功则返回{@code true},否则返回{@code false}
     */
    public static boolean copy(final InputStream in, final Writer writer, final Charset inputEncoding) {
        if (in == null) {
            return false;
        }
        InputStreamReader reader = new InputStreamReader(in, inputEncoding == null ? Charset.defaultCharset() : inputEncoding);
        return copy(reader, writer) > 0;
    }

    //endregion

    //region ================ Copy Reader to Writer ================

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝失败或流超过2GB则返回-1，否则返回流的大小
     */
    public static long copy(final Reader reader, final Writer writer) {
        return copy(reader, writer, false);
    }

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝失败或流超过2GB则返回-1，否则返回流的大小
     */
    public static long copy(final Reader reader, final Writer writer, final boolean autoClose) {
        return copy(reader, writer, DEFAULT_LARGE_BUFFER_SIZE, autoClose);
    }

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝失败或流超过2GB则返回-1，否则返回流的大小
     */
    public static long copy(final Reader reader, final Writer writer, final int bufferSize) {
        return copy(reader, writer, bufferSize, false);
    }

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝失败或流超过2GB则返回-1，否则返回流的大小
     */
    public static long copy(final Reader reader, final Writer writer, final int bufferSize, final boolean autoClose) {
        return copy(reader, writer, new char[bufferSize], autoClose);
    }

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝失败或流超过2GB则返回-1，否则返回流的大小
     */
    public static long copy(final Reader reader, final Writer writer, final char[] buffer) {
        return copy(reader, writer, buffer, false);
    }

    /**
     * 字符流的拷贝，支持大字符流(超过2GB)拷贝
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝成功则返回流的大小，否则返回-1
     */
    public static long copy(final Reader reader, final Writer writer, final char[] buffer, final boolean autoClose) {
        long size = 0;
        int count;
        try {
            while (EOF != (count = reader.read(buffer))) {
                writer.write(buffer, 0, count);
                size += count;
            }
            return size;
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            if (autoClose) {
                closeQuietly(writer);
                closeQuietly(reader);
            }
        }
    }

    //endregion

    //region ================ Copy Reader to OutputStream ================

    /**
     * 将字符输入流转为字节输出流，使用平台默认编码
     *
     * @param reader 字符输入流
     * @param out    字节输出流
     * @return 拷贝成功则返回{@code true},否则返回{@code false}
     */
    public static boolean copy(final Reader reader, final OutputStream out) {
        return copy(reader, out, Charset.defaultCharset());
    }

    /**
     * 将字符输入流转为字节输出流，使用指定编码
     *
     * @param reader         字符输入流
     * @param out            字节输出流
     * @param outputEncoding 输出流的编码
     * @return 拷贝成功则返回{@code true},否则返回{@code false}
     */
    public static boolean copy(final Reader reader, final OutputStream out, final String outputEncoding) {
        return copy(reader, out, StringUtil.isEmpty(outputEncoding) ? Charset.defaultCharset() : Charset.forName(outputEncoding));
    }

    /**
     * 将字符输入流转为字节输出流，使用指定编码
     *
     * @param reader         字符输入流
     * @param out            字节输出流
     * @param outputEncoding 输出流的编码
     * @return 拷贝成功则返回{@code true},否则返回{@code false}
     */
    public static boolean copy(final Reader reader, final OutputStream out, final Charset outputEncoding) {
        if (reader == null) {
            return false;
        }
        if (out == null) {
            return false;
        }
        Charset encoding = outputEncoding == null ? Charset.defaultCharset() : outputEncoding;
        OutputStreamWriter writer = new OutputStreamWriter(out, encoding);
        if (copy(reader, writer) == -1) {
            return false;
        }
        try {
            // we have to flush here.
            writer.flush();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
        return true;
    }

    //endregion

    //region ================ Read all lines ================

    /**
     * 从输入流中读取，采用平台默认编码
     *
     * @param in 待读取的流
     * @return 读取的内容
     */
    public static List<String> readLines(final InputStream in) {
        return readLines(in, false);
    }

    /**
     * 从输入流中读取，采用平台默认编码
     *
     * @param in 待读取的流
     * @return 读取的内容
     */
    public static List<String> readLines(final InputStream in, final boolean autoClose) {
        return readLines(in, null, autoClose);
    }

    /**
     * 从输入流中读取
     *
     * @param in      待读取的流
     * @param charset 字符编码
     * @return 读取的内容
     */
    public static List<String> readLines(InputStream in, final Charset charset) {
        return readLines(in, charset, false);
    }

    /**
     * 从输入流中读取
     *
     * @param in      待读取的流
     * @param charset 字符编码
     * @return 读取的内容
     */
    public static List<String> readLines(InputStream in, final Charset charset, final boolean autoClose) {
        if (in == null) {
            return null;
        }
        InputStreamReader reader = new InputStreamReader(in, CharsetUtil.getCharset(charset));
        return readLines(reader, autoClose);
    }

    /**
     * 从流中读取内容，读取完毕后会关闭流
     *
     * @param reader 待读取的流
     * @return 读取的内容
     */
    public static List<String> readLines(Reader reader) {
        return readLines(reader, false);
    }

    /**
     * 从流中读取内容，读取完毕后会关闭流
     *
     * @param reader 待读取的流
     * @return 读取的内容
     */
    public static List<String> readLines(final Reader reader, final boolean autoClose) {
        BufferedReader bufferedReader = getBufferedReader(reader);
        List<String> lines = new ArrayList<>();
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
            return lines;
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            if (autoClose) {
                closeQuietly(bufferedReader);
                closeQuietly(reader);
            }
        }
    }

    /**
     * 采用系统默认编码读取流的内容，读取完毕后会关闭流
     *
     * @param in 输入流
     * @return 流的内容
     * @since 2.0
     */
    public static String readToString(InputStream in) {
        return readToString(in, null);
    }

    /**
     * 采用系统默认编码读取流的内容，读取完毕后会关闭流
     *
     * @param in 输入流
     * @return 流的内容
     * @since 2.0
     */
    public static String readUtf8ToString(InputStream in) {
        return readToString(in, CharsetUtil.UTF_8);
    }

    /**
     * 采用系统默认编码读取流的内容，读取完毕后会关闭流
     *
     * @param in 输入流
     * @return 流的内容
     * @since 2.0
     */
    public static String readUtf8ToString(final InputStream in, final boolean autoClose) {
        return readToString(in, CharsetUtil.UTF_8, autoClose);
    }

    /**
     * 采用系统默认编码读取流的内容，读取完毕后会关闭流
     *
     * @param in      输入流
     * @param charset 编码
     * @return 流的内容
     * @since 2.0
     */
    public static String readToString(final InputStream in, final Charset charset) {
        return readToString(in, charset, false);
    }

    /**
     * 采用系统默认编码读取流的内容，读取完毕后会关闭流
     *
     * @param in      输入流
     * @param charset 编码
     * @return 流的内容
     * @since 2.0
     */
    public static String readToString(final InputStream in, final Charset charset, final boolean autoClose) {
        List<String> lines = readLines(in, charset, autoClose);
        return CollectionUtil.join(lines, StringUtil.EMPTY_STRING);
    }

    //endregion

    /**
     * 返回 BufferReader
     * 如果是{@link BufferedReader}强转返回，否则新建。如果提供的Reader为null返回null
     *
     * @param reader 普通Reader，如果为null返回null
     * @return {@linkplain BufferedReader} or {@code null}
     */
    public static BufferedReader getBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 返回 BufferReader
     *
     * @param in      输入流
     * @param charset 字符集
     * @return {@linkplain BufferedReader} or {@code null}
     */
    public static BufferedReader getBufferedReader(@Nullable InputStream in, @Nullable Charset charset) {
        if (in == null) {
            return null;
        }
        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }
        return getBufferedReader(reader);
    }

    /**
     * 获得二进制文件的真实文件后缀，不会关闭流
     *
     * @param in 输入流
     * @return 文件后缀
     */
    public static String getRealBinExt(@NonNull InputStream in) {
        Preconditions.requireNonNull(in, "in == null");
        //https://filesignatures.net/index.php?page=all&order=EXT&alpha=M
        //https://my.oschina.net/ososchina/blog/1610685?nocache=1541639315254

        byte[] topByte = readBytes(in, 8);
        if (in.markSupported()) {
            in.mark(0);
        }
        if (topByte != null && topByte.length == 10) {
            if ((topByte[0] & 0xFF) == 0x23 && (topByte[1] & 0xFF) == 0x21 && (topByte[2] & 0xFF) == 0x41 &&
                    (topByte[3] & 0xFF) == 0x4D && (topByte[4] & 0xFF) == 0x52) {
                return "amr";
            } else if ((topByte[0] & 0xFF) == 0x49 && (topByte[1] & 0xFF) == 0x44 && (topByte[2] & 0xFF) == 0x33) {
                return "mp3";
            } else if ((topByte[0] & 0xFF) == 0x47 && (topByte[1] & 0xFF) == 0x49 && (topByte[2] & 0xFF) == 0x46 &&
                    (topByte[3] & 0xFF) == 0x38) {
                return "gif";
            } else if ((topByte[0] & 0xFF) == 0x89 && (topByte[1] & 0xFF) == 0x50 && (topByte[2] & 0xFF) == 0x4E &&
                    (topByte[3] & 0xFF) == 0x47 && (topByte[4] & 0xFF) == 0x0D && (topByte[5] & 0xFF) == 0x0A &&
                    (topByte[6] & 0xFF) == 0x1A && (topByte[7] & 0xFF) == 0x0A) {
                return "png";
            } else if ((topByte[0] & 0xFF) == 0xFF && (topByte[1] & 0xFF) == 0xD8 && (topByte[2] & 0xFF) == 0xFF &&
                    ((topByte[3] & 0xFF) == 0xE0 || (topByte[3] & 0xFF) == 0xE1 || (topByte[3] & 0xFF) == 0xE8)) {
                return "jpg";
            } else if ((topByte[0] & 0xFF) == 0xFF && (topByte[1] & 0xFF) == 0xD8 && (topByte[2] & 0xFF) == 0xFF &&
                    ((topByte[3] & 0xFF) == 0xE2 || (topByte[3] & 0xFF) == 0xE3)) {
                return "jpeg";
            } else if ((topByte[0] & 0xFF) == 0x42 && (topByte[1] & 0xFF) == 0x4D) {
                return "bmp";
            } else if ((topByte[0] & 0xFF) == 0x25 && (topByte[1] & 0xFF) == 0x50 && (topByte[2] & 0xFF) == 0x44 &&
                    (topByte[3] & 0xFF) == 0x46) {
                return "pdf";
            }
//            luxiaoxiao 13429116607
//            陆晓
        }
        return null;
    }
}
