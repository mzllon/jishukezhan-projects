package com.jishukezhan.core.lang;

import com.jishukezhan.annotation.NonNull;
import com.jishukezhan.annotation.Nullable;

/**
 * 条件测试工具
 *
 * @author miles.tang
 */
public class Preconditions {

    private Preconditions() {
        throw new AssertionError("No com.jishukezhan.core.lang.Preconditions instances for you!");
    }

    /**
     * 检查表达式是否为{@code true},如果不为{@code true},则抛出参数异常
     *
     * @param expression 待检测待表达式
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 检查表达式是否为{@code true},如果不为{@code true},则抛出参数异常
     *
     * @param expression   待检测待表达式
     * @param errorMessage 参数错误消息
     */
    public static void checkArgument(boolean expression, @NonNull String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * 确认参数非空w
     *
     * @param reference 对象
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T requireNonNull(final T reference) {
        return requireNonNull(reference, null);
    }

    /**
     * 确认参数非空
     *
     * @param reference 对象
     * @param message   异常消息,会通过{@linkplain String#valueOf(Object)}包装,即消息可{@code null}
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T requireNonNull(final T reference, @Nullable String message) {
        if (reference == null) {
            if (message == null) {
                throw new NullPointerException();
            }
            throw new NullPointerException(message);
        }
        return reference;
    }

    /**
     * 确认参数非空
     *
     * @param reference            对象
     * @param errorMessageTemplate 异常消息模板
     * @param errorMessageArgs     异常消息参数
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     * @see StringUtil#format(String, Object...)
     */
    public static <T> T requireNonNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens anyway
            throw new NullPointerException(StringUtil.format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    /**
     * 确认字符串非空
     *
     * @param reference 被检测的对象
     * @return 非空后返回原始字符串
     */
    public static <T> T requireNotEmpty(final T reference) {
        return requireNotEmpty(reference, null);
    }

    /**
     * 确认字符串非空
     *
     * @param reference 被检测的字符串
     * @param message   如果为空抛出异常信息
     * @return 非空后返回原始字符串
     */
    public static <T> T requireNotEmpty(final T reference, @Nullable final String message) {
        if (ObjectUtil.isEmpty(reference)) {
            if (reference == null) {
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException(message);
        }
        return reference;
    }

}
