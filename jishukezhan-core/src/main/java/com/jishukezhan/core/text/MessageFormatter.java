package com.jishukezhan.core.text;

import com.jishukezhan.core.lang.ArrayUtil;
import com.jishukezhan.core.lang.CharUtil;
import com.jishukezhan.core.lang.StringUtil;

/**
 * Message Formatter
 *
 * @author miles.tang
 */
public class MessageFormatter {

    /**
     * 消息模板（消息表达式）
     */
    private String messagePattern;

    public MessageFormatter(String messagePattern) {
        this.messagePattern = messagePattern;
    }

    public String getMessagePattern() {
        return messagePattern;
    }

    /**
     * 格式化传入一个参数消息
     *
     * @param arg 参数
     * @return 格式化后的消息
     */
    public String format(Object arg) {
        return format(new Object[]{arg});
    }

    /**
     * 格式化消息
     *
     * @param args 参数列表
     * @return 格式化的消息
     */
    public String format(Object... args) {
        if (messagePattern == null || ArrayUtil.isEmpty(args)) {
            return messagePattern;
        }

        int argLength = args.length, pos = 0, findIndex;
        StringBuilder builder = new StringBuilder(messagePattern.length() + argLength * 50);

        for (int i = 0; i < argLength; i++) {
            findIndex = messagePattern.indexOf(StringUtil.EMPTY_JSON, pos);
            if (findIndex == -1) {
                // no more variables
                if (pos == 0) {
                    // this is a simple string
                    return messagePattern;
                }
                // add the tail string which contains no variables and return the result.
                builder.append(messagePattern, pos, messagePattern.length());
                return builder.toString();
            }

            // normal case
            if (isEscapedSeparator(messagePattern, findIndex)) {
                if (!isDoubleEscaped(messagePattern, findIndex)) {
                    // {被转义了
                    i--;
                    builder.append(messagePattern, pos, findIndex - 1);
                    builder.append(StringUtil.BIG_BRACKETS_START);
                    pos = findIndex + 1;
                } else {
                    // aa x:\\{}
                    builder.append(messagePattern, pos, findIndex - 1);
                    deeplyAppendParameter(builder, args[i]);
                    pos = findIndex + 2;
                }
            } else {
                builder.append(messagePattern, pos, findIndex);
                deeplyAppendParameter(builder, args[i]);
                pos = findIndex + 2;
            }
        }

        builder.append(messagePattern, pos, messagePattern.length());
        return builder.toString();
    }

    private void deeplyAppendParameter(StringBuilder builder, Object arg) {
        if (arg == null) {
            builder.append((String) null);
            return;
        }

        if (ArrayUtil.isArray(arg)) {
            builder.append(ArrayUtil.toString(arg));
        } else {
            builder.append(arg.toString());
        }
    }

    /**
     * 判断前一个字符是不是\，如果是则表示转义字符
     *
     * @param messagePattern      消息模板
     * @param delimiterStartIndex 当前分隔符{}所在的位置
     * @return 如果是转义字符则返回{@code true}，否则返回{@code false}
     */
    private boolean isEscapedSeparator(String messagePattern, int delimiterStartIndex) {
        if (delimiterStartIndex == 0) {
            return false;
        }
        char potentialEscape = messagePattern.charAt(delimiterStartIndex - 1);
        return potentialEscape == CharUtil.BACKSLASH;
    }

    /**
     * 判断是不是双\\，如果是则\不具有转义字符的含义了
     *
     * @param messagePattern      消息模板
     * @param delimiterStartIndex 当前分隔符{}所在的位置
     * @return 如果是返回{@code true}，否则返回{@code false}
     */
    private boolean isDoubleEscaped(String messagePattern, int delimiterStartIndex) {
        return delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == CharUtil.BACKSLASH;
    }

}
