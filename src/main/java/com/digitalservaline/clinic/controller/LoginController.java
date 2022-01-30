package com.digitalservaline.clinic.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalservaline.clinic.exception.BusinessException;

@Controller
public class LoginController {
	
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping(value = {"/login", "/"})
    public String viewLogin(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "timeout", required = false) String timeout,
			@RequestParam(value = "resetPassword", required = false) String resetPassword,
			@RequestParam(value = "register", required = false) String register,
			@RequestParam(value = "alreadyVerified", required = false) String alreadyVerified,
			@RequestParam(value = "verificationSuccess", required = false) String verificationSuccess,
			@RequestParam(value = "verificationError", required = false) String verificationError,
			HttpServletRequest request, Model model) throws BusinessException {
		
         if (error != null)
        	model.addAttribute("error",getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

        if (logout != null)
            model.addAttribute("message", "You've been logged out successfully.");
        
        if (timeout != null) 
        	model.addAttribute("error",	"Session expired. Please login again.");
		
        if (resetPassword != null) 
        	model.addAttribute("message","New password has been sent to your registered Email Address!");
        
        if (register != null) 
			model.addAttribute("message","Your account has been created. Please verify your email by following instructions sent to you in an email.");
		
		if (alreadyVerified != null) 
			model.addAttribute("error", "Email Already Verified.");
		
		if (verificationSuccess != null) 
			model.addAttribute("message","Email Verification Successful. Once Admin approves your account, You will get email notification.");
		
		if (verificationError != null) 
			model.addAttribute("error", "Invalid URL.");
		
		
        return "login";
    }
	
	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession()
				.getAttribute(key);

		String error = "";

		if (exception instanceof AuthenticationServiceException) {
			error = exception.getMessage();
		} else if (exception instanceof BadCredentialsException) {
			error = "Invalid user name or password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = exception.getMessage();
		}

		return error;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView viewErrorPage(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("error/403");
		return modelAndView;

	}
	
}
