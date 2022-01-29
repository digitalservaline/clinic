package com.digitalservaline.clinic.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.digitalservaline.clinic.exception.BusinessException;

public class SHAHashingUtil {

	public static StringBuffer encryptPassword(String password) throws BusinessException {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString;
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException("Exception Occured.", e);
		}
	}

	public static String generatePassword() {
		return generateSessionKey(8);
	}

	private static String generateSessionKey(int length) {
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); // 9
		int n = alphabet.length(); // 10

		String result = new String();
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++)
			// 12
			result = result + alphabet.charAt(r.nextInt(n)); // 13

		return result;
	}

	public static StringBuffer encryptPasswordWithMD5(String password) throws BusinessException {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(password.getBytes("UTF-8"));

			byte byteData[] = md.digest();

			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(password.getBytes("UTF-8"));
//			BigInteger hash = new BigInteger(1, md.digest());
//			String result = hash.toString(16);
//			if ((result.length() % 2) != 0) {
//			  result = "0" + result;
//			}
			
			return hexString;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new BusinessException("Exception Occured.", e);
		}
	}
	public static String getMd5(String input) throws BusinessException 
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            //byte[] messageDigest = md.digest(input.getBytes()); 
            
            md.update(input.getBytes());
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, md.digest()); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new BusinessException(e.getMessage()); 
        } 
    } 
}
