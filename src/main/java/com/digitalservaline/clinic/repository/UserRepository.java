package com.digitalservaline.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalservaline.clinic.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUsernameAndStatus(String username, String status);
}
