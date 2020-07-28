package com.jishukezhan.core.lang;

import com.jishukezhan.annotation.Nullable;
import com.jishukezhan.core.text.MessageFormatter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * String Utilities
 *
 * @author miles.tang
 */
public class StringUtil {

    /**
     * Don't let anyone instantiate this class
     */
    private StringUtil() {
        throw new AssertionError("No com.jishukezhan.core.lang.StringUtil instances for you!");
    }


    // region 定义的公共的字符串相关的常量

    /**
     * null字符串
     */
    public static final String NULL = "null";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 英文逗号字符串
     */
    public static final String COMMA = EMPTY_STRING + CharUtil.COMMA;

    /**
     * 下划线
     */
    public static final String UNDERLINE = EMPTY_STRING + CharUtil.UNDERLINE;

    /**
     * 中划线/破折号
     */
    public static final String DASH = EMPTY_STRING + CharUtil.DASH;

    /**
     * 点
     */
    public static final String DOT = EMPTY_STRING + CharUtil.DOT;

    /**
     * 左边大括号
     */
    public static final String BIG_BRACKETS_START = EMPTY_STRING + CharUtil.BIG_BRACKETS_START;

    /**
     * 右边大括号
     */
    public static final String BIG_BRACKETS_END = EMPTY_STRING + CharUtil.BIG_BRACKETS_END;

    /**
     * 空的JSON字符串
     */
    public static final String EMPTY_JSON = "{}";

    /**
     * 正斜杠
     */
    public static final String SLASH = EMPTY_STRING + CharUtil.SLASH;

    /**
     * 反斜杠
     */
    public static final String BACKSLASH = EMPTY_STRING + CharUtil.BACKSLASH;

    /**
     * &符号
     */
    public static final String AMP = "&";

    /**
     * :符号
     */
    public static final String COLON = ":";

    /**
     * 等号
     */
    public static final String EQUALS = EMPTY_STRING + CharUtil.EQUALS;

    /**
     * 空格
     */
    public static final String SPACE = EMPTY_STRING + CharUtil.SPACE;

    // endregion


    // region 判断字符串是否为空或不为空

    /**
     * 判断字符串是否为空，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     *
     * @param cse 被检测的字符序列
     * @return 字符串是否为空
     */
    public static boolean isEmpty(CharSequence cse) {
        return cse == null || cse.length() == 0;
    }

    /**
     * 判断字符串是否为空，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     *
     * @param str 被检测的字符串
     * @return 字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return isEmpty((CharSequence) str);
    }

    /**
     * 判断字符串列表中有任何一个字符串是否为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中任一元素<code>null</code></li>
     * <li>数组中任一元素为""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     StringUtils.isAnyEmpty();               = true
     *     StringUtils.isAnyEmpty("");             = true
     *     StringUtils.isAnyEmpty("","1");         = true
     *     StringUtils.isAnyEmpty("1",null,"");    = true
     *     StringUtils.isAnyEmpty("a","b");        = false
     * </pre>
     *
     * @param array 被检测的字符串列表
     * @return 字符串列表中是否有任一字符串为空
     * @see StringUtil#isEmpty(String)
     * @since 2.0
     */
    public static boolean isAnyEmpty(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return true;
        }
        for (CharSequence ele : array) {
            if (isEmpty(ele)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串列表中有任何一个字符串是否为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中任一元素<code>null</code></li>
     * <li>数组中任一元素为""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     StringUtils.isAnyEmpty();               = true
     *     StringUtils.isAnyEmpty("");             = true
     *     StringUtils.isAnyEmpty("","1");         = true
     *     StringUtils.isAnyEmpty("1",null,"");    = true
     *     StringUtils.isAnyEmpty("a","b");        = false
     * </pre>
     *
     * @param strArray 被检测的字符串列表
     * @return 字符串列表中是否有任一字符串为空
     * @see StringUtil#isEmpty(String)
     * @since 2.0
     */
    public static boolean isAnyEmpty(String... strArray) {
        if (ArrayUtil.isEmpty(strArray)) {
            return true;
        }
        for (String str : strArray) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串列表是否都为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中所有元素<code>null</code>或则""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     StringUtils.isAllEmpty();               = true
     *     StringUtils.isAllEmpty("");             = true
     *     StringUtils.isAllEmpty("","1");         = false
     *     StringUtils.isAllEmpty("",null,"");     = true
     *     StringUtils.isAllEmpty("a","b");        = false
     * </pre>
     *
     * @param strArray 字符串列表
     * @return 字符串列表所有元素是否都为空
     * @see #isEmpty(String)
     * @since 2.0
     */
    public static boolean isAllEmpty(String... strArray) {
        if (ArrayUtil.isEmpty(strArray)) {
            return true;
        }
        for (String str : strArray) {
            if (hasLength(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串列表是否都为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中所有元素<code>null</code>或则""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     StringUtils.isAllEmpty();               = true
     *     StringUtils.isAllEmpty("");             = true
     *     StringUtils.isAllEmpty("","1");         = false
     *     StringUtils.isAllEmpty("",null,"");     = true
     *     StringUtils.isAllEmpty("a","b");        = false
     * </pre>
     *
     * @param cses 字符串列表
     * @return 字符串列表所有元素是否都为空
     * @see #isEmpty(String)
     * @since 2.0
     */
    public static boolean isAllEmpty(CharSequence... cses) {
        if (ArrayUtil.isEmpty(cses)) {
            return true;
        }
        for (CharSequence cse : cses) {
            if (hasLength(cse)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不是空字符串，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     * <pre>
     *     StringUtils.hasLength(null);              = false
     *     StringUtils.hasLength("");                = false
     *     StringUtils.hasLength(" ");               = true
     *     StringUtils.hasLength("Hi");              = true
     * </pre>
     *
     * @param str 被检测的字符串
     * @return 非空字符串
     */
    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    /**
     * 判断字符串不是空字符串，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     * <pre>
     *     StringUtils.hasLength(null);              = false
     *     StringUtils.hasLength("");                = false
     *     StringUtils.hasLength(" ");               = true
     *     StringUtils.hasLength("Hi");              = true
     * </pre>
     *
     * @param str 被检测的字符串
     * @return 非空字符串
     * @see #hasLength(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>数组本身不是{@code null}</li>
     * <li>数组中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strArr 字符串列表
     * @return 非空字符串列表
     * @see #hasLength(String)
     * @since 2.0.0
     */
    public static boolean haveAnyLength(String... strArr) {
        if (ArrayUtil.isEmpty(strArr)) {
            return false;
        }
        for (String str : strArr) {
            if (hasLength(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断给定的参数均不为空
     *
     * @param strArr 被检测的字符串列表
     * @return 均不为空返回{@code true},否则返回{@code false}
     * @since 2.0.0
     */
    public static boolean haveAllLength(String... strArr) {
        if (ArrayUtil.isEmpty(strArr)) {
            return false;
        }
        for (String str : strArr) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see #hasLength(String)
     * @since 2.0
     */
    public static boolean haveAnyLength(Collection<String> strColl) {
        if (CollectionUtil.isEmpty(strColl)) {
            return false;
        }
        for (String str : strColl) {
            if (hasLength(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see #hasLength(String)
     * @since 2.0
     */
    public static boolean haveAllLength(Collection<String> strColl) {
        if (CollectionUtil.isEmpty(strColl)) {
            return false;
        }
        for (String str : strColl) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否存在文本字符，文本字符串的定义：
     * <ul>
     * <li>不是{@code null}</li>
     * <li>不是""</li>
     * <li>字符串中的任意一个字符不是''</li>
     * </ul>
     * <pre>
     *     StringUtils.hasText(null);              = false
     *     StringUtils.hasText("");                = false
     *     StringUtils.hasText(" ");               = false
     *     StringUtils.hasText("  ");              = false
     *     StringUtils.hasText(" a");              = true
     *     StringUtils.hasText("a");               = true
     * </pre>
     *
     * @param str 被检测的字符串
     * @return 非空白字符串
     * @see Character#isWhitespace(char)
     */
    public static boolean hasText(CharSequence str) {
        if (isEmpty(str)) {
            return false;
        }
        for (int len = str.length(), i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否存在文本字符
     *
     * @param str 被检测的字符串
     * @return 非空白字符串
     * @see #hasText(CharSequence)
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    /**
     * 判断字符串中是否存在任意一个空白子符
     * <pre>
     *     StringUtils.containsWhitespace(null);            = false
     *     StringUtils.containsWhitespace("");              = false
     *     StringUtils.containsWhitespace(" ");             = true
     *     StringUtils.containsWhitespace("ab d");          = true
     *     StringUtils.containsWhitespace("abcd");          = false
     * </pre>
     *
     * @param str 被检测的字符串
     * @return 当字符串存在一个空白符时就返回{@code true},否则返回{@code false}
     * @see Character#isWhitespace(char)
     */
    public static boolean containsWhitespace(CharSequence str) {
        if (isEmpty(str)) {
            return false;
        }
        for (int len = str.length(), i = 0; i < len; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串中是否存在任意一个空白子符
     *
     * @param str 被检测的字符串
     * @return 当字符串存在一个空白符时就返回{@code true},否则返回{@code false}
     * @see #containsWhitespace(CharSequence)
     */
    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence) str);
    }

    // endregion


    // region 去除字符串头尾部的空白部分

    /**
     * 去除字符串左右两侧的空白符
     * <pre>
     *     StringUtils.trim(null);               = null
     *     StringUtils.trim("");              = ""
     *     StringUtils.trim("    ")           = ""
     *     StringUtils.trim("  a b  ");       = "a b"
     * </pre>
     *
     * @param str 字符串
     * @return 返回已经去除左右两边的空白的字符串
     * @see Character#isWhitespace(char)
     */
    public static String trim(String str) {
        return trim(str, 0);
    }

    /**
     * 去除字符串左侧的空白字符
     * <pre>
     *     StringUtils.ltrim(null);            = null
     *     StringUtils.ltrim("ab");            = "ab"
     *     StringUtils.ltrim("  ab c");        = "ab c"
     *     StringUtils.ltrim("  ab c ");       = "ab c "
     * </pre>
     *
     * @param str 字符串
     * @return 返回去除后的字符串
     */
    public static String trimLeft(String str) {
        return trim(str, -1);
    }

    /**
     * 去除字符串右侧的空白字符
     * <pre>
     *     StringUtils.rtrim(null);            = null
     *     StringUtils.rtrim("ab");            = "ab"
     *     StringUtils.rtrim(" ab c");         = " ab c"
     *     StringUtils.rtrim(" ab c ");        = " ab c"
     * </pre>
     *
     * @param str 字符串
     * @return 返回去除后的字符串
     */
    public static String trimRight(String str) {
        return trim(str, 1);
    }

    /**
     * 去除字符串头尾部空白部分
     * <ul>
     * <li>当{@code mode}=-1去除字符串左侧部分空白</li>
     * <li>当{@code mode}=0去除字符串左右两侧部分空白</li>
     * <li>当{@code mode}=1去除字符串右侧部分空白</li>
     * </ul>
     *
     * @param str  字符串
     * @param mode 去除模式
     * @return 去除空白后的字符串
     */
    public static String trim(String str, int mode) {
        if (isEmpty(str)) {
            return str;
        }

        int length = str.length(), start = 0, end = length;

        if (mode <= 0) {
            //trim by left
            while (start < end && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        }

        if (mode >= 0) {
            //trim by right
            while (start < end && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        }

        if (start > 0 || end < length) {
            return str.substring(start, end);
        }
        return str;
    }

    /**
     * 去除字符串数组中的每个元素左右两侧空白部分
     *
     * @param strArray 原数组
     * @return 新数组
     */
    public static String[] trim(String... strArray) {
        if (strArray == null) {
            return null;
        }

        return trimToList(strArray).toArray(ArrayUtil.EMPTY_STRING_ARRAY);
    }

    /**
     * 去除字符串数组中的每个元素左右两侧空白部分
     *
     * @param strArray 原数组
     * @return 新集合
     */
    public static List<String> trimToList(String... strArray) {
        if (strArray == null) {
            return null;
        }
        final int length = strArray.length;
        if (length == 0) {
            return Collections.emptyList();
        }

        List<String> strList = new ArrayList<>(length);
        for (String str : strArray) {
            strList.add(trim(str));
        }

        return strList;
    }


    /**
     * 去除字符串集合中的每个元素左右两侧空白部分
     *
     * @param strArray 原数组
     */
    public static void trim(List<String> strArray) {
        if (CollectionUtil.isEmpty(strArray)) {
            return;
        }

        String str;
        int length = strArray.size(), i;
        for (i = 0; i < length; i++) {
            str = strArray.get(i);
            strArray.set(i, trim(str));
        }
    }


    /**
     * 去除字符串中所有的空白字符
     * <pre>
     *     StringUtils.trimAllWhiteSpace(null);            = null
     *     StringUtils.trimAllWhiteSpace(" a ");           = "a"
     *     StringUtils.trimAllWhiteSpace(" a b c ");       = "abc"
     * </pre>
     *
     * @param str 字符串
     * @return 返回去除后的字符串
     * @see Character#isWhitespace(char)
     * @since 2.0
     */
    public static String trimAllWhiteSpace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    // endregion


    // region 字符串替换或删除操作

    /**
     * 替换指定字符串的指定区间内字符为"*"
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @return 替换后的字符串
     */
    public static String hide(String str, int startInclude, int endExclude) {
        return replace(str, startInclude, endExclude, '*');
    }

    /**
     * 替换指定字符串的指定区别的字符为'*'
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param length       要被替换的长度
     * @return 替换后的字符串
     */
    public static String hideLength(String str, int startInclude, int length) {
        if (isEmpty(str)) {
            return str;
        }
        return hide(str, startInclude, startInclude + length);
    }

    /**
     * 替换指定字符串的指定区间内字符为固定字符
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @param replacedChar 被替换的字符
     * @return 替换后的字符串
     */
    public static String replace(String str, int startInclude, int endExclude, char replacedChar) {
        if (isEmpty(str)) {
            return str;
        }
        final int strLength = str.length();
        if (startInclude > strLength) {
            return str;
        }
        if (endExclude > strLength) {
            endExclude = strLength;
        }
        if (startInclude > endExclude) {
            // 如果起始位置大于结束位置不替换
            return str;
        }

        final char[] chars = new char[strLength];
        for (int i = 0; i < strLength; i++) {
            if (i >= startInclude && i < endExclude) {
                chars[i] = replacedChar;
            } else {
                chars[i] = str.charAt(i);
            }
        }
        return new String(chars);
    }

    /**
     * 将字符串中所有出现{@code oldStr}替换为{@code newStr}
     * 替换方法不支持正则，因此效率高于{@linkplain String#replaceAll(String, String)}
     * <pre>
     *     StringUtils.replace("","l","x");                                  = ""
     *     StringUtils.replace("com.mzlion.core.lang","","x");               = "com.mzlion.core.lang"
     *     StringUtils.replace("com.mzlion.core.lang",".","/");              = "com/mzlion/core/lang"
     *     StringUtils.replace("m z l i o n"," ","");                        = "mzlion"
     * </pre>
     *
     * @param str    字符串
     * @param oldStr 需要替换的字符串
     * @param newStr 新的字符串
     * @return 返回替换后的字符串
     */
    public static String replace(String str, String oldStr, String newStr) {
        if (isAnyEmpty(str, oldStr) || newStr == null) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str.length());
        int pos = 0;
        int index = str.indexOf(oldStr, pos);
        int patternLen = oldStr.length();

        while (index >= 0) {
            sb.append(str, pos, index);
            sb.append(newStr);
            pos = index + patternLen;
            index = str.indexOf(oldStr, pos);
        }
        sb.append(str.substring(pos));
        return sb.toString();
    }

    /**
     * 将字符串{@code str}中的出现的子串{@code deleteStr}全部删除，删除的字符串不支持正则表达式.
     * <pre>
     *     StringUtils.delete("hello world","l");                    = "heo word"
     * </pre>
     *
     * @param str       字符串
     * @param deleteStr 要删除的字符串
     * @return 返回删除后的字符串
     * @see #replace(String, String, String)
     */
    public static String delete(String str, String deleteStr) {
        return replace(str, deleteStr, EMPTY_STRING);
    }

    /**
     * 删除字符串的前缀，忽略前缀大小写，如果前缀不匹配则返回原始字符串
     *
     * @param str    待处理的字符串
     * @param prefix 前缀字符串
     * @return 处理后的字符串
     */
    public static String deletePrefixIgnoreCase(String str, String prefix) {
        if (startsWithIgnoreCase(str, prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * 删除字符串的前缀，忽略前缀大小写，如果前缀不匹配则返回原始字符串
     *
     * @param str    待处理的字符串
     * @param prefix 前缀字符串
     * @return 处理后的字符串
     */
    public static String deletePrefix(String str, String prefix) {
        if (isAnyEmpty(str, prefix)) {
            return str;
        }

        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * 将字符串的第一个字符(必须在{@linkplain Character#toUpperCase(char)}中，否则就不会改变)转换为大写
     * <pre>
     *     StringUtils.capitalize("she is just a kid");              = "She is just a kid"
     *     StringUtils.capitalize("She is just a kid");              = "She is just a kid"
     *     StringUtils.capitalize("我只是一个孩纸");                   = "我只是一个孩纸"
     * </pre>
     *
     * @param str 字符串
     * @return 返回字符串中的第一个字符转换为大写
     * @see #changeCharacterCase(String, int, boolean)
     */
    public static String capitalize(String str) {
        return changeCharacterCase(str, 0, true);
    }

    /**
     * 将字符串的第一个字符(必须在{@linkplain Character#toLowerCase(char)}中，否则就不会改变)转换为小写
     * <pre>
     *     StringUtils.unCapitalize("she is just a kid");              = "she is just a kid"
     *     StringUtils.unCapitalize("She is just a kid");              = "she is just a kid"
     *     StringUtils.unCapitalize("Happy New Year");                 = "happy New Year"
     *     StringUtils.unCapitalize("我只是一个孩纸");                   = "我只是一个孩纸"
     * </pre>
     *
     * @param str 字符串
     * @return 返回字符串中的第一个字符转换为小写
     * @see #changeCharacterCase(String, int, boolean)
     */
    public static String unCapitalize(String str) {
        return changeCharacterCase(str, 0, false);
    }

    /**
     * 将字符串中指定位置({@code index})的字符(必须在{@linkplain Character#toLowerCase(char)})转为大(小)写,否则就不会改变
     * <pre>
     *     StringUtils.changeCharacterCase("Java",1,true);           = "JAva"
     * </pre>
     *
     * @param str     字符串
     * @param index   大小写更改位置
     * @param capital 大写还是小写，当值为{@code true}时则大写，值为{@code false}时则小写
     * @return 返回修改后的字符串
     */
    public static String changeCharacterCase(String str, int index, boolean capital) {
        if (isEmpty(str)) {
            return str;
        }
        int pos;
        if (index < 0) {
            pos = str.length() + index;
        } else {
            pos = index;
        }
        StringBuilder sb = new StringBuilder(str.length());
        sb.append(str.subSequence(0, pos));
        if (capital) {
            sb.append(Character.toUpperCase(str.charAt(pos)));
        } else {
            sb.append(Character.toLowerCase(str.charAt(pos)));
        }
        sb.append(str.substring(pos + 1));
        return sb.toString();
    }

    /**
     * 将驼峰式命名的字符串转换为下划线字符串
     * <pre>
     *     StringUtils.camelCaseToUnderline("helloWorld");          = hello_word
     * </pre>
     *
     * @param name 驼峰式的字符串
     * @return 返回下划线的字符串
     */
    public static String camelCaseToUnderline(String name) {
        if (name == null) {
            return null;
        }
        final int length = name.length();
        if (length == 0) {
            return name;
        }

        final StringBuilder builder = new StringBuilder(length + 10);
        char ch;
        int nextPos;
        for (int i = 0; i < length; i++) {
            ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                nextPos = i + 1;
                if (nextPos == length) {
                    builder.append(Character.toLowerCase(ch));
                    continue;
                }
                if (Character.isLowerCase(name.charAt(nextPos))) {
                    if (i != 0) {
                        builder.append(UNDERLINE);
                    }
                }
                builder.append(Character.toLowerCase(name.charAt(i)));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * 将下划线命名的字符串转换为驼峰式
     * <pre>
     *     StringUtils.underlineToCamel("hello_world");           = "helloWorld"
     * </pre>
     *
     * @param name 下划线式字符串
     * @return 返回驼峰式字符串
     */
    public static String underlineToCamel(String name) {
        if (isEmpty(name)) {
            return name;
        }

        final int length = name.length();
        final char[] charArray = name.toCharArray();
        final StringBuilder builder = new StringBuilder(length);

        int pos = 0;
        while (pos < length && charArray[pos] == CharUtil.UNDERLINE) {
            builder.append(charArray[pos]);
            pos++;
            if (pos < length && charArray[pos] != CharUtil.UNDERLINE) {
                break;
            }
        }

        char ch;
        while (pos < length) {
            ch = charArray[pos];
            if (ch != CharUtil.UNDERLINE) {
                builder.append(ch);
            } else {
                pos++;
                if (pos == length) {
                    builder.append(ch);
                    continue;
                }
                if (charArray[pos] != CharUtil.UNDERLINE) {
                    builder.append(Character.toUpperCase(charArray[pos]));
                } else {
                    builder.append(charArray[pos - 1]).append(charArray[pos]);
                }
            }
            pos++;
        }
        return builder.toString();
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str   字符串
     * @param chars 字符列表
     * @return 去除后的字符
     */
    public static String removeAll(String str, char... chars) {
        if (isEmpty(str) || ArrayUtil.isEmpty(chars)) {
            return str;
        }
        final int length = str.length();
        final StringBuilder sb = new StringBuilder(length);
        char ch;
        for (int i = 0; i < length; i++) {
            ch = str.charAt(i);
            if (!ArrayUtil.containsElement(chars, ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    //endregion


    //region 判断两个字符串是否相等

    /**
     * 判断字符串中是否以给定的字符串开头，不考虑大小写问题
     * <pre>
     *     StringUtils.startsWithIgnoreCase("abcd",null);            = false
     *     StringUtils.startsWithIgnoreCase("abcd","ab");            = true
     *     StringUtils.startsWithIgnoreCase("abcd","AB");            = true
     *     StringUtils.startsWithIgnoreCase("abcd","bc");            = false
     *     StringUtils.startsWithIgnoreCase(null,"ab");              = false
     * </pre>
     *
     * @param str    字符串
     * @param prefix 给定的字符串，以该字符串开头
     * @return 如果字符串{code str}是以{@code prefix}开头(忽略大小写)则返回{@code true},否则返回{@code false}
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (isAnyEmpty(str, prefix)) {
            return false;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        String lowerCaseStr = str.substring(0, prefix.length()).toLowerCase();
        String lowerCasePrefix = prefix.toLowerCase();
        return lowerCasePrefix.equals(lowerCaseStr);
    }

    /**
     * 判断字符串中是否以给定的字符串结尾,不考虑大小写问题
     * <pre>
     *     StringUtils.endsWithIgnoreCase("ddcbab",null);            = false
     *     StringUtils.endsWithIgnoreCase("ddcbab","ab");            = false
     *     StringUtils.endsWithIgnoreCase("ddcbab","AB");            = true
     *     StringUtils.endsWithIgnoreCase("ddcbab","bc");            = false
     *     StringUtils.endsWithIgnoreCase(null,"ab");                = false
     * </pre>
     *
     * @param str    字符串
     * @param suffix 给定的字符串，以该字符串开头
     * @return 如果字符串{code str}是以{@code prefix}开头(忽略大小写)则返回{@code true},否则返回{@code false}
     * @see String#endsWith(String)
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (isAnyEmpty(str, suffix)) {
            return false;
        }
        if (str.length() < suffix.length()) {
            return false;
        }
        if (str.endsWith(suffix)) {
            return true;
        }
        String lowerStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lowerSuffix = suffix.toLowerCase();
        return lowerSuffix.equals(lowerStr);
    }

    /**
     * 判断连个字符串是否相等
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 相等为{@code true},否则为{@code false}
     */
    public static boolean equals(String str1, String str2) {
        return equals(str1, str2, false);
    }

    /**
     * 判断连个字符串是否相等,忽略大小写
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 相等为{@code true},否则为{@code false}
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return equals(str1, str2, true);
    }

    /**
     * 判断连个字符串是否相等
     *
     * @param str1       字符串1
     * @param str2       字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 相等为{@code true},否则为{@code false}
     */
    public static boolean equals(String str1, String str2, boolean ignoreCase) {
        if (str1 == null) {
            //只有两个都为null才判断相等
            return str2 == null;
        }
        if (str2 == null) {
            return false;
        }
        return ignoreCase ? str1.equalsIgnoreCase(str2) : str1.equals(str2);
    }

    /**
     * 判断字符串是否在给定的数组中
     *
     * @param str   字符串
     * @param array 字符串数组
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean contains(String str, String... array) {
        return contains(str, false, array);
    }

    /**
     * 判断字符串是否在给定的数组中,忽略大小写
     *
     * @param str   字符串
     * @param array 字符串数组
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean containsIgnoreCase(String str, String... array) {
        return contains(str, true, array);
    }

    /**
     * 判断字符串是否在给定的数组中
     *
     * @param str        字符串
     * @param array      字符串数组
     * @param ignoreCase 是否忽略大小写
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean contains(String str, boolean ignoreCase, String... array) {
        if (ArrayUtil.isEmpty(array)) {
            return false;
        }
        for (String ele : array) {
            if (equals(str, ele, ignoreCase)) {
                return true;
            }
        }
        return false;
    }

    //endregion


    // region 分割字符串

    /**
     * 用英文逗号分割字符串
     *
     * @param str 被分割的字符串
     * @return 分割后的字符串数组
     */
    public static String[] split(String str) {
        return split(str, CharUtil.COMMA);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串数组
     */
    public static String[] split(String str, char separator) {
        return split(str, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串数组
     */
    public static String[] split(String str, char separator, boolean ignoreEmpty) {
        return split(str, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *     StringUtils.split(null)                                                                  =   null
     *     StringUtils.split("")                                                                    =   []
     *     StringUtils.split("\t")                                                                  =   [\t]
     *     StringUtils.split("hello,world")                                                         =   [hello,world]
     *     StringUtils.split("hello,")                                                              =   [hello,]
     *     StringUtils.split(",hello,,")                                                            =   [,hello,,]
     *     StringUtils.split(",hello,,",CharUtil.COMMA,true)                                       =   [hello]
     *     StringUtils.split(", hello a ,\teverything , will ,be\n,ok",CharUtil.COMMA,true,true)   =   [hello a,everything,will,be,ok]
     *
     * </pre>
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串数组
     */
    @SuppressWarnings("Duplicates")
    public static String[] split(String str, char separator, boolean ignoreEmpty, boolean isTrim) {
        if (str == null) {
            return null;
        }

        final List<String> list = splitToList(str, separator, ignoreEmpty, isTrim);

        return list.toArray(ArrayUtil.EMPTY_STRING_ARRAY);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串数组
     */
    public static String[] split(String str, String separator) {
        return split(str, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串数组
     */
    public static String[] split(String str, String separator, boolean ignoreEmpty) {
        return split(str, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *    StringUtils.split("Best wish for you\nThanks.",null)      =   [Best,wish,for,you,Thanks.]
     *    StringUtils.split("Mac##iPad#iPad Pro##iPhone","##")      =   [Mac,iPad#iPad Pro,iPhone]
     *    StringUtils.split("\t筷子 || 笔记 || 随|便||",true,true)    =   [筷子,笔记,随|便]
     * </pre>
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串数组
     */
    @SuppressWarnings("Duplicates")
    public static String[] split(String str, String separator, boolean ignoreEmpty, boolean isTrim) {
        if (str == null) {
            return null;
        }

        final List<String> list = splitToList(str, separator, ignoreEmpty, isTrim);

        return list.toArray(ArrayUtil.EMPTY_STRING_ARRAY);
    }

    /**
     * 基于收个匹配的字符分隔，匹配成功得到的是长度为2的数组
     * 如果分隔符不匹配则返回{@code null}
     *
     * @param str       待分隔的字符串
     * @param separator 分隔符
     * @return 分隔后的字符串
     */
    public static String[] splitAtFirst(String str, char separator) {
        if (str == null) {
            return null;
        }
        int index = str.indexOf(separator);
        if (index == -1) {
            return null;
        }
        if (index == 0) {
            return new String[]{StringUtil.EMPTY_STRING, str};
        }
        return new String[]{str.substring(0, index), str.substring(index + 1)};
    }

    /**
     * 用英文逗号分割字符串
     *
     * @param str 被分割的字符串
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(String str) {
        return splitToList(str, CharUtil.COMMA);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(String str, char separator) {
        return splitToList(str, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(String str, char separator, boolean ignoreEmpty) {
        return splitToList(str, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *     StringUtils.split(null)                                                                  =   null
     *     StringUtils.split("")                                                                    =   []
     *     StringUtils.split("\t")                                                                  =   [\t]
     *     StringUtils.split("hello,world")                                                         =   [hello,world]
     *     StringUtils.split("hello,")                                                              =   [hello,]
     *     StringUtils.split(",hello,,")                                                            =   [,hello,,]
     *     StringUtils.split(",hello,,",CharUtil.COMMA,true)                                       =   [hello]
     *     StringUtils.split(", hello a ,\teverything , will ,be\n,ok",CharUtil.COMMA,true,true)   =   [hello a,everything,will,be,ok]
     *
     * </pre>
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<String> splitToList(String str, char separator, boolean ignoreEmpty, boolean isTrim) {
        if (str == null) {
            return null;
        }
        final int length = str.length();
        if (length == 0) {
            return Collections.emptyList();
        }

        final List<String> list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == separator) {
                addToList(list, str.substring(start, i), ignoreEmpty, isTrim);
                start = i + 1;
            }
        }

        addToList(list, str.substring(start, length), ignoreEmpty, isTrim);

        return list;
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(String str, String separator) {
        return splitToList(str, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(String str, String separator, boolean ignoreEmpty) {
        return splitToList(str, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *    StringUtils.split("Best wish for you\nThanks.",null)      =   [Best,wish,for,you,Thanks.]
     *    StringUtils.split("Mac##iPad#iPad Pro##iPhone","##")      =   [Mac,iPad#iPad Pro,iPhone]
     *    StringUtils.split("\t筷子 || 笔记 || 随|便||",true,true)    =   [筷子,笔记,随|便]
     * </pre>
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<String> splitToList(String str, String separator, boolean ignoreEmpty, boolean isTrim) {
        if (str == null) {
            return null;
        }
        final int length = str.length();
        if (length == 0) {
            return Collections.emptyList();
        }

        final List<String> list = new ArrayList<>();
        int start = 0, index;
        if (isEmpty(separator)) {
            //按照空白分割
            for (index = 0; index < length; index++) {
                if (Character.isWhitespace(str.charAt(index))) {
                    addToList(list, str.substring(start, index), ignoreEmpty, isTrim);
                    start = index + 1;
                }
            }
            addToList(list, str.substring(start, length), ignoreEmpty, isTrim);
        } else if (separator.length() == 1) {
            return splitToList(str, separator.charAt(0), ignoreEmpty, isTrim);
        } else {
            index = 0;
            int sepLen = separator.length();
            while (index < length) {
                index = str.indexOf(separator, start);
                if (index > -1) {
                    addToList(list, str.substring(start, index), ignoreEmpty, isTrim);
                    start = index + sepLen;
                } else {
                    break;
                }
            }
            addToList(list, str.substring(start, length), ignoreEmpty, isTrim);
        }

        return list;
    }


    /**
     * 用英文逗号分割字符串
     *
     * @param str 被分割的字符串
     * @return 分割后的字符串集合列表
     */
    public static List<Integer> splitToIntList(String str) {
        return splitToIntList(str, CharUtil.COMMA);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<Integer> splitToIntList(String str, char separator) {
        if (str == null) {
            return null;
        }

        final List<String> strList = splitToList(str, separator, true, true);
        if (CollectionUtil.isEmpty(strList)) {
            return Collections.emptyList();
        }

        return str2IntList(strList);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<Integer> splitToIntList(String str, String separator) {
        if (str == null) {
            return null;
        }

        final List<String> strList = splitToList(str, separator, true, true);
        if (CollectionUtil.isEmpty(strList)) {
            return Collections.emptyList();
        }

        return str2IntList(strList);
    }


    private static List<Integer> str2IntList(List<String> strList) {
        return strList
                .stream()
                .filter(StringUtil::hasLength)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static void addToList(List<String> list, String str, boolean ignoreEmpty, boolean isTrim) {
        if (isTrim) {
            str = trim(str);
        }
        if (!ignoreEmpty || !str.isEmpty()) {
            list.add(str);
        }
    }

    // endregion


    // region 其它方法

    /**
     * <p>将url中的请求参数转为Map对象</p>
     *
     * @param urlParamStr url请求参数
     * @return 返回Map对象
     */
    public static Map<String, List<String>> urlParamsToMap(String urlParamStr) {
        if (urlParamStr == null) {
            return null;
        }
        if (urlParamStr.length() == 0) {
            return Collections.emptyMap();
        }
        List<String> urlParamList = splitToList(urlParamStr, AMP);
        if (CollectionUtil.isEmpty(urlParamList)) {
            return Collections.emptyMap();
        }

        HashMap<String, List<String>> params = new HashMap<>(urlParamList.size());
        String[] array;
        List<String> values;
        for (String urlParam : urlParamList) {
            if (hasText(urlParam)) {
                array = split(urlParam, EQUALS);
                if (hasLength(array[0])) {
                    values = params.computeIfAbsent(array[0], a -> new LinkedList<>());
                    if (array.length == 2) {
                        values.add(array[1]);
                    }
                }
            }
        }
        return params;
    }

    /**
     * 将url中的其你去参数转为单值的Map
     *
     * @param urlParamStr url请求参数
     * @return 返回Map对象
     */
    public static Map<String, String> urlParamsToSingleMap(String urlParamStr) {
        Map<String, List<String>> map = urlParamsToMap(urlParamStr);
        if (CollectionUtil.isNotEmpty(map)) {
            Map<String, String> result = new HashMap<>(map.size() << 1);
            List<String> value;
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                value = entry.getValue();
                if (CollectionUtil.isEmpty(value)) {
                    result.put(entry.getKey(), null);
                } else {
                    result.put(entry.getKey(), value.get(value.size() - 1));
                }
            }
            return result;
        }
        return Collections.emptyMap();
    }

    /**
     * 如果给定字符串不是以{@code suffix}结尾的，在尾部补充{@code suffix}
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 补充后的字符串
     */
    public static String addSuffixIfNot(String str, String suffix) {
        if (haveAnyLength(str, suffix)) {
            return str;
        }
        if (str.endsWith(suffix)) {
            return str.concat(suffix);
        }
        return str;
    }

    /**
     * Tokenize the given {@code String} into a {@code String} array via a
     * {@link StringTokenizer}.
     * <p>The given {@code delimiters} string can consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using {@link #delimitedListToStringArray}.
     *
     * @param str               the {@code String} to tokenize (potentially {@code null} or empty)
     * @param delimiters        the delimiter characters, assembled as a {@code String}
     *                          (each of the characters is individually considered as a delimiter)
     * @param trimTokens        trim the tokens via {@link String#trim()}
     * @param ignoreEmptyTokens omit empty tokens from the result array
     *                          (only applies to tokens that are empty after trimming; StringTokenizer
     *                          will not consider subsequent delimiters as token in the first place).
     * @return an array of the tokens
     * @see java.util.StringTokenizer
     * @see String#trim()
     * @see #delimitedListToStringArray
     */
    public static String[] tokenizeToStringArray(
            @Nullable String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return ArrayUtil.EMPTY_STRING_ARRAY;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    /**
     * 将字符串结合对象转为字符串数组
     *
     * @param collection {@linkplain Collection} or {@code null}
     * @return 非{@code null}的字符串数组
     */
    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return (CollectionUtil.isEmpty(collection) ? ArrayUtil.EMPTY_STRING_ARRAY : collection.toArray(ArrayUtil.EMPTY_STRING_ARRAY));
    }

    // endregion


    // region 格式化字符串

    /**
     * 格式化文本,使用{@code {}}作为占位符
     *
     * @param messagePattern 文本模式
     * @param arg            单个参数
     * @return 格式化后的文本
     */
    public static String format(String messagePattern, Object arg) {
        return format(messagePattern, new Object[]{arg});
    }

    /**
     * 格式化文本,使用{@code {}}作为占位符，按照顺序替换为对应的参数
     *
     * @param messagePattern 文本模式
     * @param args           参数列表
     * @return 格式化后的文本
     */
    public static String format(String messagePattern, Object... args) {
        MessageFormatter formatter = new MessageFormatter(messagePattern);
        return formatter.format(args);
    }

    // endregion

}
