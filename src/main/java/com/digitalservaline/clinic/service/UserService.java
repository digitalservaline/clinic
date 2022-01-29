package com.digitalservaline.clinic.service;

import com.digitalservaline.clinic.domain.Users;

public interface UserService {

	Users findByUserName(String userName);
}
