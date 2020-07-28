package com.jishukezhan.core.lang;

import com.jishukezhan.core.io.IOUtil;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Base64 Utilities
 *
 * @author miles.tang
 */
public class Base64Util {

    private Base64Util() {
        throw new AssertionError("No com.jishukezhan.core.digest.Base64Util instances for you!");
    }

    /**
     * 标准的BASE64编码
     *
     * @param data 待BASE64编码的字符串
     * @return BASE64之后的字符串
     */
    public static String encode(final String data) {
        return encode(data, false);
    }

    /**
     * 标准的BASE64编码
     *
     * @param data      待BASE64编码的字符串
     * @param isUrlSafe 为{@code true}则URL安全字符，否则为标准BASE64字符
     * @return BASE64之后的字符串
     */
    public static String encode(final String data, final boolean isUrlSafe) {
        return encode(data, null, isUrlSafe);
    }

    /**
     * 标准的BASE64编码
     *
     * @param data          待BASE64编码的字符串
     * @param inputEncoding 原始数据的编码
     * @return BASE64之后的字符串
     */
    public static String encode(final String data, final Charset inputEncoding) {
        return encode(data, inputEncoding, false);
    }

    /**
     * 标准的BASE64编码
     *
     * @param data          待BASE64编码的字符串
     * @param inputEncoding 原始数据的编码
     * @param isUrlSafe     为{@code true}则URL安全字符，否则为标准BASE64字符
     * @return BASE64之后的字符串
     */
    public static String encode(final String data, final Charset inputEncoding, final boolean isUrlSafe) {
        if (StringUtil.isEmpty(data)) {
            return data;
        }
        return encode(data.getBytes(CharsetUtil.getCharset(inputEncoding)), isUrlSafe);
    }

    /**
     * 标准的BASE64编码
     *
     * @param data 待BASE64编码的字节数组
     * @return 待BASE64编码的字符串
     * @see #encode(byte[], boolean)
     */
    public static String encode(final byte[] data) {
        return encode(data, false);
    }

    /**
     * BASE64编码
     *
     * @param data      待BASE64编码的字节数组
     * @param isUrlSafe 为{@code true}则URL安全字符，否则为标准BASE64字符
     * @return 待BASE64编码的字符串
     * @see Base64#getEncoder()
     * @see Base64#getUrlEncoder()
     */
    public static String encode(final byte[] data, final boolean isUrlSafe) {
        if (ArrayUtil.isEmpty(data)) {
            return null;
        }
        return isUrlSafe ? Base64.getUrlEncoder().encodeToString(data) : Base64.getEncoder().encodeToString(data);
    }

    /**
     * base64编码
     *
     * @param in 被编码base64的流（一般为图片流或者文件流）
     * @return 经过BASE64编码后的内容
     */
    public static String encode(final InputStream in) {
        return encode(in, false);
    }

    /**
     * base64编码
     *
     * @param in        被编码base64的流（一般为图片流或者文件流）
     * @param isUrlSafe 为{@code true}则URL安全字符，否则为标准BASE64字符
     * @return 经过BASE64编码后的内容
     */
    public static String encode(final InputStream in, final boolean isUrlSafe) {
        byte[] src = IOUtil.readBytes(Preconditions.requireNonNull(in));
        return isUrlSafe ? Base64.getUrlEncoder().encodeToString(src) : Base64.getEncoder().encodeToString(src);
    }

    /**
     * 标准的BASE64解码
     *
     * @param base64Data 已经编码过的字符串
     * @return 返回原始数据的字节数组形式
     */
    public static byte[] decode(final String base64Data) {
        return decode(base64Data, false);
    }

    /**
     * 标准的BASE64解码
     *
     * @param base64Data 已经编码过的字符串
     * @param isUrlSafe  为{@code true}则URL安全字符，否则为标准BASE64字符
     * @return 返回原始数据的字节数组形式
     */
    public static byte[] decode(final String base64Data, final boolean isUrlSafe) {
        if (StringUtil.isEmpty(base64Data)) {
            return null;
        }
        return isUrlSafe ? Base64.getUrlDecoder().decode(base64Data) : Base64.getDecoder().decode(base64Data);
    }

    /**
     * 标准的BASE64解码，根据默认字符集转为字符串
     *
     * @param base64Data 已经编码过的字符串
     * @return 原始数据
     * @see #decodeToStr(String, Charset, boolean)
     */
    public static String decodeToStr(final String base64Data) {
        return decodeToStr(base64Data, (Charset) null);
    }

    /**
     * 标准的BASE64解码
     *
     * @param base64Data     已经编码过的字符串
     * @param outputEncoding 字符编码，如果为空则采用默认编码
     * @return 原始数据
     * @see #decodeToStr(String, Charset, boolean)
     */
    public static String decodeToStr(final String base64Data, final String outputEncoding) {
        return decodeToStr(base64Data, CharsetUtil.forName(outputEncoding));
    }

    /**
     * 标准的BASE64解码
     *
     * @param base64Data     已经编码过的字符串
     * @param outputEncoding 字符编码，如果为空则采用默认编码
     * @return 原始数据
     * @see #decodeToStr(String, Charset, boolean)
     */
    public static String decodeToStr(final String base64Data, final Charset outputEncoding) {
        return decodeToStr(base64Data, outputEncoding, false);
    }

    /**
     * 标准的BASE64解码
     *
     * @param base64Data 已经编码过的字符串
     * @param isUrlSafe  是否{@code URL_SAFE}模式
     * @return 原始数据
     */
    public static String decodeToStr(final String base64Data, final boolean isUrlSafe) {
        return decodeToStr(base64Data, (Charset) null, isUrlSafe);
    }

    /**
     * BASE64解码，如果{@code isUrlSafe}为{@code true}则采用URL_SAFE模式解码，否则采用标准模式解码
     *
     * @param base64Data     已经编码过的字符串
     * @param outputEncoding 原始数据的编码
     * @param isUrlSafe      是否{@code URL_SAFE}模式
     * @return 原始数据
     * @see #decodeToStr(String, Charset, boolean)
     */
    public static String decodeToStr(final String base64Data, final String outputEncoding, final boolean isUrlSafe) {
        return decodeToStr(base64Data, CharsetUtil.forName(outputEncoding), isUrlSafe);
    }

    /**
     * BASE64解码，如果{@code isUrlSafe}为{@code true}则采用URL_SAFE模式解码，否则采用标准模式解码
     *
     * @param base64Data     已经编码过的字符串
     * @param outputEncoding 原始数据的编码
     * @param isUrlSafe      是否{@code URL_SAFE}模式
     * @return 原始数据
     * @see Base64#getDecoder()
     * @see Base64#getUrlDecoder()
     */
    public static String decodeToStr(final String base64Data, final Charset outputEncoding, final boolean isUrlSafe) {
        byte[] decodeData = decode(base64Data, isUrlSafe);
        if (ArrayUtil.isEmpty(decodeData)) {
            return null;
        }
        return new String(decodeData, CharsetUtil.getCharset(outputEncoding));
    }

}
