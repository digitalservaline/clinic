package com.digitalservaline.clinic.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.util.StringUtils;

import com.digitalservaline.clinic.constants.ClinicConstants;
import com.digitalservaline.clinic.domain.Role;
import com.digitalservaline.clinic.domain.Users;
import com.digitalservaline.clinic.service.UserService;
import com.digitalservaline.clinic.util.ClinicUtil;

@RestController
@RequestMapping("/admin/*")
public class AdminController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private User user;

	@Autowired
	private UserService userService;

	@GetMapping(value = "/home")
	public ModelAndView viewHome(HttpServletRequest request, Model model) {

		Locale locale = (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);

		String language = request.getParameter("lang");

		if (null == locale) {

			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en"));

		} else if (!StringUtils.isEmpty(language) && language.equals(ClinicConstants.LOCALE_HI)) {

			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("hi"));

		} else if (!StringUtils.isEmpty(language) && language.equals(ClinicConstants.LOCALE_EN)) {

			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en"));

		}

		Locale updatedLocale = (Locale) request.getSession()
				.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		model.addAttribute("language", updatedLocale.toString());
		model.addAttribute("key", "1234567891234567");

		user = ClinicUtil.getUserDetail();
		ModelAndView modelAndView = new ModelAndView("admin/adminHome");

		if (user != null) {
			logger.info("User - {}, Role - {} - Displaying home page", user.getUsername(), user.getAuthorities());
			Users userEntity = userService.findByUserName(user.getUsername());
			modelAndView.addObject("loggedInUserName", userEntity.getUsername());

			String roleName = "";
			Set<Role> roles = userEntity.getRoles();
			if (roles != null && !roles.isEmpty()) {
				List<Role> roleList = new ArrayList<Role>(roles);
				roleName = roleList.get(0).getRoleName();
			}
			modelAndView.addObject("roleName", roleName);
		}
		return modelAndView;
	}

	@GetMapping(value = "/dashboard")
	public ModelAndView viewDashboard(HttpServletRequest request) {
		user = ClinicUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying dashboard", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("admin/dashboard");
		return modelAndView;
	}
	
	@GetMapping(value = "/state")
	public ModelAndView state(HttpServletRequest request) {
		user = ClinicUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying dashboard", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("admin/state");
		return modelAndView;
	}
	
	@GetMapping(value = "/city")
	public ModelAndView addCity(HttpServletRequest request) {
		user = ClinicUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying dashboard", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("admin/city");
		return modelAndView;
	}
	
	@GetMapping(value = "/addSate")
	public void addState(HttpServletRequest request) {
		user = ClinicUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying dashboard", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("admin/state");
	}

}
