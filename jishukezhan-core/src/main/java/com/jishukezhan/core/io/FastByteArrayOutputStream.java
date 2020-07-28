/*
 * Copyright (c) 2017, biezhi 王爵 nice (biezhi.me@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jishukezhan.core.io;

import com.jishukezhan.core.exceptions.IoRuntimeException;
import com.jishukezhan.core.lang.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 基于快速缓冲{@linkplain FastByteBuffer}的OutputStream，随着数据的增长自动扩充缓冲区
 * <p>可以通过{@link #toByteArray()}和 {@link #toString()}来获取数据<p>
 * <p>{@link #close()}方法无任何效果，当流被关闭后不会抛出IOException</p>
 * <p>这种设计避免重新分配内存块而是分配新增的缓冲区，缓冲区不会被GC，数据也不会被拷贝到其他缓冲区。</p>
 *
 * @author biezhi
 */
public class FastByteArrayOutputStream extends OutputStream {

    private final FastByteBuffer fastByteBuffer;

    public FastByteArrayOutputStream() {
        this(1024);
    }

    public FastByteArrayOutputStream(int bufferSize) {
        this.fastByteBuffer = new FastByteBuffer(bufferSize);
    }

    /**
     * Writes <code>b.length</code> bytes from the specified byte array
     * to this output stream. The general contract for <code>write(b)</code>
     * is that it should have exactly the same effect as the call
     * <code>write(b, 0, b.length)</code>.
     *
     * @param b the data.
     * @see OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b) {
        fastByteBuffer.append(b);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this output stream.
     * The general contract for <code>write(b, off, len)</code> is that
     * some of the bytes in the array <code>b</code> are written to the
     * output stream in order; element <code>b[off]</code> is the first
     * byte written and <code>b[off+len-1]</code> is the last byte written
     * by this operation.
     * <p>
     * The <code>write</code> method of <code>OutputStream</code> calls
     * the write method of one argument on each of the bytes to be
     * written out. Subclasses are encouraged to override this method and
     * provide a more efficient implementation.
     * <p>
     * If <code>b</code> is <code>null</code>, a
     * <code>NullPointerException</code> is thrown.
     * <p>
     * If <code>off</code> is negative, or <code>len</code> is negative, or
     * <code>off+len</code> is greater than the length of the array
     * <code>b</code>, then an <tt>IndexOutOfBoundsException</tt> is thrown.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     */
    @Override
    public void write(byte[] b, int off, int len) {
        fastByteBuffer.append(b, off, len);
    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for <code>write</code> is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument <code>b</code>. The 24
     * high-order bits of <code>b</code> are ignored.
     * <p>
     * Subclasses of <code>OutputStream</code> must provide an
     * implementation for this method.
     *
     * @param b the <code>byte</code>.
     */
    @Override
    public void write(int b) {
        fastByteBuffer.append((byte) b);
    }

    /**
     * 返回数据大小
     *
     * @return 数据大小
     */
    public int size() {
        return fastByteBuffer.size();
    }

    /**
     * 此方法无任何效果，当流被关闭后不会抛出IOException
     */
    @Override
    public void close() {
        //ignore
    }

    /**
     * 重置缓冲区内容
     */
    public void reset() {
        fastByteBuffer.reset();
    }

    public void writeTo(OutputStream out) {
        final int index = fastByteBuffer.index();
        byte[] buffer;
        try {
            for (int i = 0; i < index; i++) {
                buffer = fastByteBuffer.array(i);
                out.write(buffer);
            }
            out.write(fastByteBuffer.array(index), 0, fastByteBuffer.offset());
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 转为Byte数组
     *
     * @return Byte数组
     */
    public byte[] toByteArray() {
        return fastByteBuffer.toArray();
    }

    @Override
    public String toString() {
        return new String(toByteArray());
    }

    /**
     * 转为字符串
     *
     * @param charsetName 编码
     * @return 字符串
     */
    public String toString(String charsetName) {
        return toString(CharsetUtil.forName(charsetName));
    }

    /**
     * 转为字符串
     *
     * @param charset 编码
     * @return 字符串
     */
    public String toString(Charset charset) {
        return new String(toByteArray(), charset);
    }

}
