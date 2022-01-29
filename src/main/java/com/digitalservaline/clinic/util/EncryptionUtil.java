package com.digitalservaline.clinic.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptionUtil {
	public static String encrypt(String keyPath, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(
					initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(returnbyte(keyPath), "AES");
			//SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
//			System.out.println("encrypted string: "
//					+ Base64.encodeBase64String(encrypted));

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String key, String initVector, String encrypted) {
		try {
			
//			String result = URLDecoder.decode(encrypted,
//					StandardCharsets.UTF_8.toString());
			//System.out.println("url decoded response string: " + result);
			IvParameterSpec iv = new IvParameterSpec(
					initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(returnbyte(key), "AES");
			//SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			System.out.println("decrypted response string: " + new String(original));
			
			String dataAfterDecryption=new String(original);
			String result = URLDecoder.decode(dataAfterDecryption,
					StandardCharsets.UTF_8.toString());
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private static byte[] returnbyte(String path) throws IOException {
		FileInputStream fileinputstream = new FileInputStream(path);
		byte[] abyte = new byte[fileinputstream.available()];
		fileinputstream.read(abyte);
		fileinputstream.close();
		return abyte;
	}

//	public static void main(String[] args) throws UnsupportedEncodingException {
//		String realPath = "C:/CoE_SVN/codebase/mpmsme/mpindustry/mpindustry-web/src/main/resources/MPSSO.key";
//		//String realPath = "1234567890123456";
//		String initVector = "1234561234567890";
//		String abc = "123456";
//		String enc = encrypt(realPath, initVector, abc);
//		System.out.println("After Encrypted: "+enc);
//		String urlenc = URLEncoder.encode(enc, StandardCharsets.UTF_8.toString());
//		System.out.println("After Encrypted and Url Encoded: "+urlenc);
//		decrypt(realPath, initVector, urlenc);
//		
//		
//		
//		//String encrypted = "rMc20KoyJI6OkOwtpa4j6tx5mhDtalEANoRv5mIw2SZgD8jJJrJNEYLsaAzD2IKueP962LyUF%2BDu%0D%0AvfcKKYKQllaq7HUdXq0HU5bJZFgbwN7LSo0VOCD5bDK9yDrDqosrY1b9vonkp6X2GLAZ4I%2FzM8kU%0D%0Adh6aD72hFfF0AE%2FvdyjYUN%2FlodaX6Iqrcn59Q%2B%2FaDOtNDrANA973hgoPki%2F4ygGon8QWnxnOQJfg%0D%0ALWPfqYBm0f0efp5nw99iOG47rWHmHkCGGHy1nzbQNzJqhjTJDhSddCUkhSoC9XmJA3N3ESWxAOuS%0D%0AyO7%2Fmzb%2F6BpV%2FLG%2FkY84Nx5k4ESOrcvtwUzZkA%3D%3D";
//		//String decodedUrl=URLDecoder.decode(urlenc, StandardCharsets.UTF_8.toString());
//		//String decrpt=decrypt(realPath, initVector, encrypted);
//		//String decryptEnc=URLDecoder.decode(decrpt, StandardCharsets.UTF_8.toString());
//		//System.out.println("Final after decrpt: "+decrpt);
//	}
}
