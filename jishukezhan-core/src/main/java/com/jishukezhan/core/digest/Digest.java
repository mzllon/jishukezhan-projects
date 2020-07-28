package com.jishukezhan.core.digest;

import com.jishukezhan.core.io.IOUtil;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create MessageDigest instance
 *
 * @author miles.tang
 */
public class Digest {

    /**
     * 根据算法名称返回{@code MessageDigest}实例
     *
     * @param algorithm 摘要算法名称
     * @return 返回摘要实例
     * @see MessageDigest#getInstance(String)
     */
    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            // safe to swallow
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 返回MD5实例
     *
     * @return MD5实例
     */
    static MessageDigest md5() {
        return getDigest(MessageDigestAlgorithm.MD5.getValue());
    }

    /**
     * 返回 SHA-1 实例
     *
     * @return SHA-1 实例
     */
    static MessageDigest sha1() {
        return getDigest(MessageDigestAlgorithm.SHA1.getValue());
    }

    /**
     * 返回 SHA-224 实例
     *
     * @return SHA-256 实例
     */
    static MessageDigest sha224() {
        return getDigest(MessageDigestAlgorithm.SHA224.getValue());
    }

    /**
     * 返回 SHA-256 实例
     *
     * @return SHA-256 实例
     */
    static MessageDigest sha256() {
        return getDigest(MessageDigestAlgorithm.SHA256.getValue());
    }

    /**
     * 返回 SHA-384 实例
     *
     * @return SHA-384 实例
     */
    static MessageDigest sha384() {
        return getDigest(MessageDigestAlgorithm.SHA384.getValue());
    }

    /**
     * 返回 SHA-512 实例
     *
     * @return SHA-512 实例
     */
    static MessageDigest sha512() {
        return getDigest(MessageDigestAlgorithm.SHA512.getValue());
    }

    /**
     * 对输入流做摘要计算,会自动关闭输入流
     *
     * @param digest 某种摘要算法
     * @param data   待计算的数据
     * @return 返回计算后的结果
     */
    static byte[] doDigest(final MessageDigest digest, final InputStream data) {
        try {
            byte[] input = IOUtil.readBytes(data);
            digest.update(input);
            return digest.digest();
        } finally {
            IOUtil.closeQuietly(data);
        }
    }

    static byte[] doDigest(final MessageDigest digest, final byte[] input) {
        digest.update(input);
        return digest.digest();
    }

}
