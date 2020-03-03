package com.jinhee2.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jinhee2.model.User;
import com.jinhee2.repository.UserJpaRepository;

public class UserService {

	@Autowired
	private UserJpaRepository userJpaRepository;
	
//	public Iterable<User> selectAll () {
//		return userJpaRepository.findAll();
//	}
	
	public User selectUser(String username) {
		return userJpaRepository.findByUsername(username);
	}
}
