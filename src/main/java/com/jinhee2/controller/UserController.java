package com.jinhee2.controller;

import java.util.Arrays;
import java.util.List;

import com.jinhee2.dto.UsersDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jinhee2.model.UserRole;
import com.jinhee2.model.UserRole.Role;
import com.jinhee2.model.Users;
import com.jinhee2.service.UserService;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/selectUser")
	private List<Users> selectUsers() {

		return userService.findAll();
	}

//	@GetMapping("/selectUserDTO/{id}")
//	private ResponseEntity<UsersDTO.Response> selectUser(@ApiParam(required = true, example = "1") @PathVariable final Integer id) {
//		UsersDTO.Response userDtoResponse = userService.findUser(id);
//		return ResponseEntity.ok(userDtoResponse);
//	}

	@GetMapping("/api/selectUserDTO/{id}")
	private ResponseEntity<UsersDTO.Response> selectUserDTO(@ApiParam(required = true , example = "1") @PathVariable final  Integer id) {
		UsersDTO.Response userDtoResponse = userService.findUser(id);
		return ResponseEntity.ok(userDtoResponse);
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