package com.digitalservaline.clinic.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.digitalservaline.clinic.filter.CaptchaAuthenticationFilter;
import com.digitalservaline.clinic.handler.ClinicAuthenticationSuccessHandler;
import com.digitalservaline.clinic.servlet.CaptchaGenServlet;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ClinicAuthenticationSuccessHandler authenticationSuccessHandler;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	RequestMatcher csrfRequestMatcher = new RequestMatcher() {

		  private AntPathRequestMatcher[] requestMatchers = {
		      new AntPathRequestMatcher("/**/login/*"),
		      new AntPathRequestMatcher("/**/resetpassword/*"),
		      new AntPathRequestMatcher("/**/registerNewAccount/*")
		  };
		  
		  @Override
		  public boolean matches(HttpServletRequest request) {
		    for (AntPathRequestMatcher rm : requestMatchers) {
		      if (rm.matches(request)) { return true; }
		    }
		    return false;
		  } 
		};
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		// Enable csrf only on some request matches
	      .csrf()
	        .requireCsrfProtectionMatcher(csrfRequestMatcher)
	        .and()
		.authorizeRequests()
			.antMatchers("/", "/captcha", "/forgotpassword").permitAll()
			.antMatchers("/khadi/**").access("hasRole('MPRDC')")
			.antMatchers("/department/**").access("hasRole('DEPARTMENT')")
			.antMatchers("/indentor/**").access("hasRole('INDENTOR')")
			.antMatchers("/supplier/**").access("hasRole('SUPPLIER')")
			.antMatchers("/consignee/**").access("hasRole('CONSIGNEE')")
			.antMatchers("/pc/**").access("hasRole('PC')")
			.and()
			.formLogin().loginPage("/login")
			.successHandler(authenticationSuccessHandler)
			.and().logout().permitAll()
			.and()
			.exceptionHandling()
			.accessDeniedPage("/403")
			.and()
			.sessionManagement()
			.maximumSessions(2)
			.expiredUrl("/login?timeout");
		
		// Protection against 'ClickJacking' attacks. solved 'X-Frame-Options Header Not Set'
		http.headers().frameOptions().sameOrigin();
		// Protection against Type 1 Reflected XSS attacks. solved 'Web Browser XSS Protection Not Enabled'
		http.headers().xssProtection();
		// Disabling browsers to perform risky mime sniffing. solved 'X-Content-Type-Options Header Missing'
		http.headers().contentTypeOptions();
		//Protection against Session Fixation
		http.sessionManagement().sessionFixation().migrateSession();
		
		http.headers().cacheControl();
		
		http.headers().referrerPolicy(ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE);
		
		//CSP fix
		http.headers().contentSecurityPolicy("script-src 'self' 'unsafe-inline' 'unsafe-eval'");
		
		//http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin","domain"));
		
		/*http.addFilterBefore(
				authenticationFilter(), UsernamePasswordAuthenticationFilter.class);*/
		http.addFilterBefore(new CaptchaAuthenticationFilter("/login", "/login?error"), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/css/**", "/js/**", "/img/**", "/angular/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	public ServletRegistrationBean captchaServlet() {
		ServletRegistrationBean captcha = new ServletRegistrationBean(
				new CaptchaGenServlet(), "/captcha");
		return captcha;
	}
	
	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}
	
	
}
