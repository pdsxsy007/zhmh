package io.cordova.zhqy.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化
 *
 * @ClassName: AESEncryptUtile
 * @Description: 加密解密工具
 * @author: lgh
 * @date: 2018年7月10日 下午3:06:27
 */
public class AesEncryptUtile {

	/** 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。 */
	public static String key = "1234567890123456";
	private static String ivParameter = "0392039203920300";
	private static AesEncryptUtile instance = null;

	/**
	 * 单例
	 *
	 * @return
	 * @author lgh
	 */
	public static AesEncryptUtile getInstance() {
		if (instance == null) {
			instance = new AesEncryptUtile();
		}
		return instance;
	}

	/**
	 * 加密 暂未使用
	 *
	 * @param encData 待加密字符串
	 * @param secretKey key
	 * @param vector 向量
	 * @return
	 * @throws Exception
	 * @author lgh
	 */
	@TargetApi(Build.VERSION_CODES.O)
	public static String encrypt(String encData, String secretKey, String vector) throws Exception {
		if (secretKey == null) {
			return null;
		}
		if (secretKey.length() != 16) {
			return null;
		}
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = secretKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes("utf-8"));
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
		java.util.Base64.Encoder encoder = getEncoder();
		byte[] encoder64 = encoder.encode(encrypted);
		// 此处使用BASE64做转码。
		return new String(encoder64);
	}

	/**
	 * 加密
	 *
	 * @param src 待加密字符串
	 * @return
	 * @throws Exception
	 * @author lgh
	 */
	// ----------------ToDo 加密
	@TargetApi(Build.VERSION_CODES.O)
	public static String encrypt(String src) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = key.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes("utf-8"));
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
		String str = Base64.encodeToString(encrypted, Base64.DEFAULT);
		return str;
	}

	/**
	 * 加密
	 * @param src 待加密字符串
	 * @param kye 密钥（16位）
	 * @return
	 * @throws Exception
	 * @author lgh
	 */
	@TargetApi(Build.VERSION_CODES.O)
	public static String encrypt(String src, String kye) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = kye.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes("utf-8"));
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
//		java.util.Base64.Encoder encoder = getEncoder();
//		byte[] encoder64 = encoder.encode(encrypted);
		String str = Base64.encodeToString(encrypted,Base64.NO_WRAP);
		// 此处使用BASE64做转码。
		return str;
	}

	/**
	 * 解密
	 * @param src	待解密字符串
	 * @return
	 * @throws Exception
	 * @author lgh
	 */
	@TargetApi(Build.VERSION_CODES.O)
	public static String decrypt(String src) throws Exception {
		try {
			byte[] raw = key.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes("utf-8"));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decode(src.getBytes(), Base64.DEFAULT);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 *    解密
	 * @param src	待解密字符串
	 * @param key	key
	 * @return
	 * @throws Exception
	 * @author lgh
	 */
	//------------------- ToDo 解密
	@TargetApi(Build.VERSION_CODES.O)
	public static String decrypt(String src, String key) throws Exception {
		try {
			byte[] raw = key.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes("utf-8"));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//			java.util.Base64.Decoder decoder = getDecoder();
//			// 先用base64解密
//			byte[] encrypted1 = decoder.decode(src);
			byte[] encrypted1 = Base64.decode(src, Base64.NO_WRAP);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 解密
	 * @param src	待解密字符串
	 * @param key	key
	 * @param ivs	向量
	 * @return
	 * @throws Exception
	 * @author lgh
	 */
	@TargetApi(Build.VERSION_CODES.O)
	public static String decrypt(String src, String key, String ivs) throws Exception {
		try {
			byte[] raw = key.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivs.getBytes("utf-8"));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			java.util.Base64.Decoder decoder = getDecoder();
			// 先用base64解密
			byte[] encrypted1 = decoder.decode(src);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}
//
//	public static void main(String[] s) {
//		try {
////			// 需要加密的字串
////			String cSrc = "love12234 受打击哦发生大幅弹道导弹";
////
////			// 加密
////			long lStart = System.currentTimeMillis();
////			String enString = AesEncryptUtile.getInstance().encrypt(cSrc);
////			System.out.println("1：" + enString);
////
////			long lUseTime = System.currentTimeMillis() - lStart;
////			System.out.println("2：" + lUseTime + "ms");
////
////			// 解密
////			lStart = System.currentTimeMillis();
////			String DeString = AesEncryptUtile.getInstance().decrypt(enString);
////			System.out.println("3：" + DeString);
////			lUseTime = System.currentTimeMillis() - lStart;
////			System.out.println("4：" + lUseTime + "ms");
//
//
//			// 需要加密的字串
//			String enString = "+5pWIvVy+CXmTF49y1m8NKoTPDayvAH86JPcWZU1Xrx2NmxFoY8GuFsUthJgSVWs/Sg6LpVAaDLqJJDI9LTRLsB0Fu/TliJFJ/2U2uWRirKWWr4esmxtvv0E5yBxfN5EsXFvdfxyBzQp9mZmUSyAylicvmJdgGXTZa2DSfw2YmvCx3qMJU3YfoTGnWPG3dpm4LQbvqYFgVvI/uc9lpLL6A\u003d\u003d";
//			AesEncryptUtile.getInstance();
//			// 解密
//			String deString = AesEncryptUtile.decrypt(enString,"1234567890123456");
//			System.out.println("解密：" + deString);
//
//		} catch (Exception e) {
//			e.getMessage();
//		}
//	}
//
}
