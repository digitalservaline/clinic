package com.digitalservaline.clinic.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalservaline.clinic.constants.ClinicConstants;
import com.digitalservaline.clinic.domain.Users;
import com.digitalservaline.clinic.repository.UserRepository;
import com.digitalservaline.clinic.service.UserService;



@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;


	@Override
	public Users findByUserName(String userName) {
		return userRepository.findByUsernameAndStatus(userName, ClinicConstants.STATUS_ACTIVE);
	}
}
