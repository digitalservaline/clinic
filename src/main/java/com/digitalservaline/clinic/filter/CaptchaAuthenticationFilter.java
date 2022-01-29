package com.digitalservaline.clinic.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.digitalservaline.clinic.constants.ClinicConstants;


/**
 * The filter to verify captcha.
 */
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String processUrl;

    public CaptchaAuthenticationFilter(String defaultFilterProcessesUrl, String failureUrl) {
        super(defaultFilterProcessesUrl);
        this.processUrl = defaultFilterProcessesUrl;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
        if(processUrl.equals(req.getServletPath()) && "POST".equalsIgnoreCase(req.getMethod())){
            String expect = req.getSession().getAttribute(ClinicConstants.CAPTCHA_LOGIN)!= null ? req.getSession().getAttribute(ClinicConstants.CAPTCHA_LOGIN).toString() : "";

            //remove from session
            req.getSession().removeAttribute(ClinicConstants.CAPTCHA_LOGIN);

            if (expect != null && !expect.equalsIgnoreCase(req.getParameter("captchaText"))){
                unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException("Wrong captcha text."));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
