package com.digitalservaline.clinic.util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.digitalservaline.clinic.constants.ClinicConstants;

public class ClinicUtil {

	
	public static final Logger logger = LoggerFactory.getLogger(ClinicUtil.class);

	public static String[] acceptedContentTypes = { "application/pdf","image/jpg", "image/jpeg", "image/png", "image/gif" };

	public static String[] pdfOnly = { "application/pdf" };

	public static String getImageString(byte[] profileImage) {
		return "data:image/jpg;base64," + Base64.encodeBase64String(profileImage);
	}


	public static String generateCaptchaText(int captchaLength) {

		//String saltChars = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
		String saltChars = "1234567890";
		StringBuffer captchaStrBuffer = new StringBuffer();
		java.util.Random rnd = new java.util.Random();

		// build a random captchaLength chars salt
		while (captchaStrBuffer.length() < captchaLength) {
			int index = (int) (rnd.nextFloat() * saltChars.length());
			captchaStrBuffer.append(saltChars.substring(index, index + 1));
		}

		return captchaStrBuffer.toString();
		//return "1";
	}

	public static String getMessage(String propertyFile, String key,
			Object[] params) {

		Locale locale = LocaleContextHolder.getLocale();

		ResourceBundle bundle = ResourceBundle.getBundle(propertyFile, locale);

		return MessageFormat.format(bundle.getString(key), params);
	}

	public static User getUserDetail() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = null;
		if (null != securityContext) {
			Authentication authentication = securityContext.getAuthentication();
			if (null != authentication) {
				if (authentication.getPrincipal() instanceof String) {
					user = null;
				} else {
					user = (User) authentication.getPrincipal();
				}
			}
		}
		return user;
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

	public static Date convertStringToDate(String inputDate){

		SimpleDateFormat dateFormat = new SimpleDateFormat(ClinicConstants.DATE_FORMAT);

		try {
			return dateFormat.parse(inputDate.trim());
		} catch (ParseException e) {
			logger.info("failed to convert String to date, Input Date - {}", inputDate);
			logger.error("An exception occurred.", e);
		}
		return null;
	}

	public static String convertDateToString(Date inputDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				ClinicConstants.DATE_FORMAT);
		
		try {
			if(inputDate!=null)
				return dateFormat.format(inputDate);
		} catch (Exception e) {
			logger.info("failed to convert date to String, Input Date - {}", inputDate);
			logger.error("An exception occurred.", e);
		}	
		return null;
	}
	
	
	public static String convertDateToStringWithFormat(Date inputDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				format);
		try {
			if(inputDate!=null)
				return dateFormat.format(inputDate);
		} catch (Exception e) {
			logger.info("failed to convert date to String, Input Date - {}, format - {}", inputDate, format);
			logger.error("An exception occurred.", e);
		}	
		return null;
	}

	public static boolean isThisDateBeforeTheGivenDate(String dateToValidateStr, String givenDateStr){

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date dateToValidate = sdf.parse(dateToValidateStr);
			Date givenDate = sdf.parse(givenDateStr);

			if(dateToValidate.compareTo(givenDate) < 1){
				return true;
			}else
				return false;

		} catch (ParseException e) {
			logger.info("isThisDateBeforeTheGivenDate(), Input Dates - {}, {}", dateToValidateStr, givenDateStr);
			logger.error("An exception occurred.", e);
			return false;
		}
	}

	public static String minusOneMonth(String dateToMinus) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToMinus);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			cal.add(Calendar.MONTH, -1);

			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);

			month++;// calender's month indexing starts from 0
			if(String.valueOf(month).length()<2){
				return "0"+month+"/"+year;
			}
			return month+"/"+year;
		} catch (ParseException e) {
			logger.info("minusOneMonth(), Input Date - {}", dateToMinus);
			logger.error("An exception occurred.", e);
			return "";
		}
	}

	public static String getFinancialYear(){

		int year = Calendar.getInstance().get(Calendar.YEAR);

		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (month < 4) {
			return (year - 1)%100 + "-" + year%100;
		} else {
			return year%100 + "-" + (year + 1)%100;
		}
	}

	public static String decryptParam(String param){
		
		String decryptedString =  new String(java.util.Base64.getDecoder().decode(param));
        AesUtil aesUtil = new AesUtil(128, 1000);
        if (decryptedString != null && decryptedString.split("::").length == 3) {
        	return aesUtil.decrypt(decryptedString.split("::")[1], decryptedString.split("::")[0], 
        			"1234567891234567", decryptedString.split("::")[2]);
        }
        else{
        	return "0";
        }
	}
	
}
