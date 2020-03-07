package com.jinhee2.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jinhee2.model.UserRole;
import com.jinhee2.model.UserRole.Role;
import com.jinhee2.model.Users;
import com.jinhee2.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/selectUser")
	private List<Users> selectUser() {
		return userService.findAll();
	}
	
	@GetMapping("/insertUser")
	private String insertUser(
			@RequestParam(name="name") String name,
			@RequestParam(name="phonenumber") String phonenumber,
			@RequestParam(name="address") String address,
			@RequestParam(name="password") String password,
			@RequestParam(name="role") Role role
	) {
		Users user = new Users();
		UserRole userRoles = new UserRole();
		
		user.setName(name);
		user.setPhonenumber(phonenumber);
		user.setAddress(address);
		user.setPassword(passwordEncoder.encode(password));
		userRoles.setRolename(role);
		user.setUserRoles(Arrays.asList(userRoles));
		
		userService.insertUser(user);
		return "사용자 " + name + " 저장완료";
	}
	
	@GetMapping("/updateUser")
	private String updateUser(
			@RequestParam(name="id") Integer id,
			@RequestParam(name="name") String name,
			@RequestParam(name="phonenumber") String phonenumber,
			@RequestParam(name="address") String address,
			@RequestParam(name="password") String password,
			@RequestParam(name="role") Role role
	) {
		userService.updateUser(id, name, address, phonenumber, passwordEncoder.encode(password), role);
		return name + " 수정완료";
	}
	
	@GetMapping("deleteUser")
	private String deleteUser(
		@RequestParam(name="id") Integer id
	) {
		userService.deleteUser(id);
		return id + " 번째 삭제완료";
	}
}