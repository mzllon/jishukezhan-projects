package com.jishukezhan.core.digest;

/**
 * 摘要算法类型
 *
 * @author miles.tang
 */
public enum MessageDigestAlgorithm {
	MD2("MD2"),
	MD5("MD5"),
	SHA1("SHA-1"),
	SHA224("SHA-224"),
	SHA256("SHA-256"),
	SHA384("SHA-384"),
	SHA512("SHA-512");

	private String value;

	/**
	 * 构造
	 *
	 * @param value 算法字符串表示
	 */
	MessageDigestAlgorithm(String value) {
		this.value = value;
	}

	/**
	 * 获取算法字符串表示
	 * @return 算法字符串表示
	 */
	public String getValue() {
		return this.value;
	}
}
