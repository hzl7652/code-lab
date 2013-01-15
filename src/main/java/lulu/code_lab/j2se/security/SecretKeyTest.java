package lulu.code_lab.j2se.security;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.Assert;
import org.junit.Test;

public class SecretKeyTest {

	@Test
	public void test1() throws Exception {

		String content = "aa";
		Cipher cipher = Cipher.getInstance("AES");

		SecretKey key = KeyGenerator.getInstance("AES").generateKey();

		//加密
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] b = cipher.doFinal(content.getBytes());
		System.out.println(new String(b));

		//解密
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decodeB = cipher.doFinal(b);
		System.out.println(new String(decodeB));
	}
	
	
	/**
	 * 演示用公钥加密，私钥解密.
	 * @throws Exception
	 */
	@Test
	public void publicEncrypt() throws Exception{
		byte[] sourceData = "abc".getBytes();
		//生成公钥和私钥
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		KeyPair keyPair = keyGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		//加密
		byte[] encryptData = cipher.doFinal(sourceData);
		
		//解密
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptData = cipher.doFinal(encryptData);
		System.out.println(new String(decryptData));
		Assert.assertArrayEquals(sourceData, decryptData);
		
	}

}
