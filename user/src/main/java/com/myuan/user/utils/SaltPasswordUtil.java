package com.myuan.user.utils;

import java.security.MessageDigest;

public class SaltPasswordUtil {

	private static String salt = "sansr";
	/**
	 * 重新加密密码
	 * @param password
	 * @return
	 */
	public final static String getNewPass(String password) {

		try {
			String s = password + salt;
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			return toHex(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i=0; i<bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
}
