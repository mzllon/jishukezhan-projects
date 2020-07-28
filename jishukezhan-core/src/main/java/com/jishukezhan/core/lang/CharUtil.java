package com.jishukezhan.core.lang;

/**
 * Character Utilities
 *
 * @author miles.tang
 */
public class CharUtil {

    //region 定义的公共的字符相关的常量

    /**
     * 点
     */
    public static final char DOT = '.';

    /**
     * 逗号
     */
    public static final char COMMA = ',';

    /**
     * 下划线
     */
    public static final char UNDERLINE = '_';

    /**
     * 中划线/破折号
     */
    public static final char DASH = '-';

    /**
     * 斜杆
     */
    public static final char SLASH = '/';

    /**
     * 反斜杠
     */
    public static final char BACKSLASH = '\\';

    /**
     * 左边大括号
     */
    public static final char BIG_BRACKETS_START = '{';

    /**
     * 右边大括号
     */
    public static final char BIG_BRACKETS_END = '}';

    /**
     * 等号
     */
    public static final char EQUALS = '=';

    /**
     * 空格
     */
    public static final char SPACE = ' ';

    //endregion

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param ch 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(char ch) {
        return BACKSLASH == ch || SLASH == ch;
    }

}
