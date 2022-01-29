package com.digitalservaline.clinic.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

import com.digitalservaline.clinic.constants.ClinicConstants;
import com.digitalservaline.clinic.service.impl.UserDetailsServiceImpl;

public class CustomUsernamePasswordAuthenticationFilter extends
		OncePerRequestFilter {
	
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String captchaText = request.getParameter("captchaText");
		HttpSession session = request.getSession();

		
		
		String captcha = (String) session.getAttribute(ClinicConstants.CAPTCHA_LOGIN);
		
		if (captchaText != null){

			captchaText = captchaText.toUpperCase();

		}

		if (captcha == null || (captcha != null && !captcha.equals(captchaText))) {
			userDetailsService.setCaptchaSuccess(false);
			//session.setAttribute("isCaptchaSuccess", false);
		} else {
			userDetailsService.setCaptchaSuccess(true);
			//session.setAttribute("isCaptchaSuccess", true);
		}

		/*String loginType = request.getParameter("loginType");

		if (StringUtils.isNotBlank(loginType)) {
			userDetailsService.setLoginType(loginType);

		}*/
		super.doFilter(request, response, filterChain);
	}

	public CustomUsernamePasswordAuthenticationFilter(UserDetailsServiceImpl userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}
}
