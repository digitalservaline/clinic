package com.digitalservaline.clinic.constants;

public interface ClinicConstants {
	
	public String LOCALE_EN = "en";

	public String LOCALE_HI = "hi";
	
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";

	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	
	public static final String NOTIFICATION_FILE = "notification";

	public static final String CAPTCHA_LOGIN = "CAPTCHA_LOGIN";
	
	public static final String CAPTCHA_RESET = "CAPTCHA_RESET";
	
	public static final String CAPTCHA_SIGNUP = "CAPTCHA_SIGNUP";
	
	public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static final String ERROR_FETCHING_DATA = "Some error occured while fetching the data";
	
	public static final String ERROR_SAVING_DATA = "Some error occured while saving the data";
	
	public static final String ERROR_DELETING_DATA = "Some error occured while deleting the data";
	
	public static final String DUPLICATE_ENTRY = "Duplicate entry - ";
	
	public static final String STATUS_ACTIVE = "Active";
	
	public static final String STATUS_COMPLETED = "Completed";
	
	public static final String STATUS_INACTIVE = "InActive";
	
	public static final String STATUS_DELETED = "Deleted";
	
	public static final String STATUS_PENDING = "Pending";
	
	public static final String STATUS_RECONCILED = "Reconciled";
	
	public static final String STATUS_REJECTED = "Rejected";
	
	public static final String STATUS_TRANSFERRED = "Transferred";
	
	public static final String STATUS_PENDING_VERIFICATION = "Pending Verification";
	
	public static final String STATUS_PENDING_ACTIVATION = "Pending Activation";

	public static final String STATUS_IN_PROCESS = "In Process";
	
	public static final String STATUS_DELIVERED = "Delivered";
	
	public static final String PRESENT = "Present";
	
	String DATE_FORMAT = "dd/MM/yyyy";
	
	String DATE_FORMAT_dd_MMM_yyyy = "dd-MMM-yyyy";
	
	String DATE_FORMAT_MMM_yyyy = "MMM-yyyy";
	String MYSQL_DATE_FORMAT = "YYYY-MM-DD HH:MI:SS";
	
	String PENDING = "Pending";
	
	String COMPLETED = "Completed";
	
	String MONTH[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	String PARAM_APPCODE= "AMS44";
	String PARAM_DEFAULT_USER = "user";
	String PARAM_LDAP= "ldap";
	
	
}
