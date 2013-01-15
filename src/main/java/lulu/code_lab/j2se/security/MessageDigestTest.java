package lulu.code_lab.j2se.security;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

public class MessageDigestTest {

	@Test
	public void md5Test() {
		try {
			String str = "1";
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(str.getBytes("UTF-8"));
			System.out.println(result.length);
			System.out.println(toHexString(result) + " :legth " + toHexString(result).length());
			System.out.println(DatatypeConverter.printHexBinary(result));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void SHATest() {
		try {
			String str = "1";
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] result = digest.digest(str.getBytes("UTF-8"));
			System.out.println(result.length);
			System.out.println(toHexStr(result) + " :legth " + toHexStr(result).length());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void SHASaltTest() {
		try {
			String str = "1";
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] salt = generateSalt(8);

			if (salt != null) {
				digest.update(salt);
			}
			byte[] result = digest.digest(str.getBytes("UTF-8"));
			int iterations = 1024;
			for (int i = 1; i <= iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			System.out.println(toHexString(result) + " :legth " + toHexString(result).length());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 生成随机字节
	 * @param numBytes
	 * @return
	 */
	public static byte[] generateSalt(int numBytes) {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * 计算法
	 */
	public static String toHexStr(byte[] input) {
		StringBuffer hex = new StringBuffer();
		for (byte b : input) {
			int a = b & 0xff;
			if (a < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(a));
		}
		return hex.toString();
	}

	private static final String HEXES = "0123456789abcdef";

	/**
	 * 查表法
	 */
	public String toHexString(byte[] bytes) {
		final StringBuilder hex = new StringBuilder(2 * bytes.length);
		for (final byte b : bytes) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
}
