package com.jishukezhan.core.lang;

import com.jishukezhan.core.io.FileUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Charset Utilities
 *
 * @author miles.tang
 */
public class CharsetUtil {

    private CharsetUtil() {
        throw new AssertionError("No com.jishukezhan.core.lang.CharsetUtil instances for you!");
    }

    /**
     * ISO-8859-1编码名
     *
     * @since 2.0.0
     */
    public static final String ISO_8859_1_VALUE = "ISO-8859-1";

    /**
     * UTF-8编码名
     *
     * @since 2.0.0
     */
    public static final String UTF_8_VALUE = "UTF-8";

    /**
     * GBK编码名
     *
     * @since 2.0.0
     */
    public static final String GBK_VALUE = "GBK";

    /**
     * ISO-8859-1编码对象
     *
     * @since 2.0.0
     */
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;

    /**
     * UTF-8编码对象
     *
     * @since 2.0.0
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * GBK编码对象
     */
    public static final Charset GBK = Charset.forName(GBK_VALUE);

    /**
     * 平台默认编码
     */
    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static Charset defaultCharset() {
        return DEFAULT_CHARSET;
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     * @since 2.0.0
     */
    public static String defaultCharsetName() {
        return DEFAULT_CHARSET.name();
    }

    /**
     * 系统字符集编码，如果是Windows，则默认为GBK编码，否则取 {@link #defaultCharsetName()}
     *
     * @return 系统字符集编码
     * @see #defaultCharsetName()
     * @since 2.0.0
     */
    public static Charset systemCharset() {
        return FileUtil.isWindows() ? GBK : defaultCharset();
    }

    /**
     * 系统字符集编码，如果是Windows，则默认为GBK编码，否则取 {@link #defaultCharsetName()}
     *
     * @return 系统字符集编码
     * @see #defaultCharsetName()
     * @since 2.0.0
     */
    public static String systemCharsetName() {
        return systemCharset().name();
    }

    /**
     * 转换为{@linkplain Charset}对象
     *
     * @param charsetName 字符编码，如果为空则返回UTF-8编码
     * @return {@link Charset}
     */
    public static Charset forName(String charsetName) {
        return forName(charsetName, CharsetUtil.UTF_8);
    }

    /**
     * 转换为{@linkplain Charset}对象
     *
     * @param charsetName 字符编码，如果为空则返回默认编码
     * @return {@link Charset}
     */
    public static Charset forName(String charsetName, Charset defaultEncoding) {
        return StringUtil.isEmpty(charsetName) ? defaultEncoding : Charset.forName(charsetName);
    }

    /**
     * 返回字符编码，如果为{@code} null}则返回平台默认编码
     *
     * @param encoding 编码
     * @return 编码
     */
    public static Charset getCharset(Charset encoding) {
        return encoding == null ? defaultCharset() : encoding;
    }

    /**
     * 返回字符编码，如果为{@code} null}则返回指定默认编码
     *
     * @param encoding        编码
     * @param defaultEncoding 当{@code encoding}为空时
     * @return 编码
     */
    public static Charset getCharset(Charset encoding, Charset defaultEncoding) {
        return encoding == null ? defaultEncoding : encoding;
    }

    /**
     * 解析编码，如果解析失败返回默认编码
     *
     * @param encoding        编码字符串
     * @param defaultEncoding 默认编码
     * @return 解析后的编码
     */
    public static Charset parseSafety(final String encoding, Charset defaultEncoding) {
        if (encoding != null) {
            try {
                return Charset.forName(encoding);
            } catch (UnsupportedCharsetException e) {
                return defaultEncoding;
            }
        }
        return defaultEncoding;
    }

}
