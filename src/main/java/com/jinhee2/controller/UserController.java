package com.jinhee2.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jinhee2.model.User;
import com.jinhee2.repository.UserJpaRepository;
import com.jinhee2.service.UserService;

@RestController
public class UserController {

	@Resource(name="userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/selectUser")
	public User selectUser(@RequestParam(name="id") int id, @RequestParam(name="name") String name) {
		User user = new User();
		
		try {
			user = userService.selectUser(name);
			return user;
		} catch (NumberFormatException e) {
			throw new NumberFormatException("에러에");
		}
		
	}
}