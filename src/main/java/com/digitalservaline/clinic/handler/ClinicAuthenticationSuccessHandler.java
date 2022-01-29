package com.digitalservaline.clinic.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.digitalservaline.clinic.constants.ClinicConstants;

@Component
public class ClinicAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public static final Logger logger = LoggerFactory.getLogger(ClinicAuthenticationSuccessHandler.class);
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {

		String remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
		if (remoteAddr == null || "".equals(remoteAddr)) {
			remoteAddr = httpServletRequest.getRemoteAddr();
		}
		logger.info("User - " + authentication.getName() + ", Role - " + authentication.getAuthorities().toString()
				+ " - User Logged In Successfully from System IP: " + remoteAddr);

		String targetUrl = "";
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (GrantedAuthority authority : authorities) {

			String role = authority.getAuthority();
			
			if (ClinicConstants.ROLE_SUPERADMIN.equals(role)) {
				targetUrl = "/superadmin/home#/dashboard";
				break;
			} else if (ClinicConstants.ROLE_ADMIN.equals(role)) {
				targetUrl = "/admin/home#/dashboard";
				break;
			} else {
				targetUrl = "/403";
			}
		}
		redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
}
