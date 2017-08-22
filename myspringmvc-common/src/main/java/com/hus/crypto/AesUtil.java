/**   
 * @author Strong
 * @date 2013年10月11日 下午3:37:21
 * Copyright (c) 2012 T.b.j,Inc. All Rights Reserved. 
 * @version V1.2.2   
 */

package com.hus.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName: AesUtil
 * @Description: TODO
 * @author Strong
 * @date 2013年10月11日 下午3:37:21 Copyright (c) 2012 T.b.j,Inc. All Rights
 *       Reserved.
 */
public class AesUtil {

	private static final String keystore = "0102030405060708";

	public static String getKeystore() {
		return keystore;
	}

	private final static String encoding = "UTF-8";

	public static String encryptAES(String content, String password) {
		try {
			SecretKeySpec skeySpec = getKey(password);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(password.getBytes(encoding));
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(content.getBytes(encoding));

			return new Base64().encodeAsString(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] encryptAES(byte[] content, String password) {
		try {
			SecretKeySpec skeySpec = getKey(password);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(password.getBytes(encoding));
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String content, String password) {

		try {
			SecretKeySpec skeySpec = getKey(password);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(password.getBytes(encoding));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new Base64().decode(content);

			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, encoding);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static byte[] decrypt(byte[] content, String password) {
		try {
			SecretKeySpec skeySpec = getKey(password);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(password.getBytes(encoding));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static SecretKeySpec getKey(String strKey) throws Exception {
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

		return skeySpec;
	}

	public static void main(String args[]) throws Exception {
        System.out.println(decrypt("37zoQWZlnsxuqWGnMXGYyp2Hd8Vr1FfGQ245KtTKLzk=", getKeystore()));
        //System.out.println(encryptAES("", getKeystore()));
	}
}
