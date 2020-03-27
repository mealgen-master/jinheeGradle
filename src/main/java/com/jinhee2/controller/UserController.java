package com.jinhee2.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.jinhee2.dto.UsersDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jinhee2.model.UserRole;
import com.jinhee2.model.UserRole.Role;
import com.jinhee2.model.Users;
import com.jinhee2.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserResourceAssembler userResourceAssembler;

	@GetMapping("/selectUserDTO/{id}")
	// status 자리에
	// EntityModel을 통해 DTO + link 객체를 대체
	ResponseEntity<EntityModel<UsersDTO.Response>> selectUserDTO(@ApiParam(required = true , example = "1") @PathVariable final  Integer id) {
		UsersDTO.Response userDtoResponse = userService.findUserDto(id);

		// hateoas
		EntityModel<UsersDTO.Response> resource = userResourceAssembler.toModel(userDtoResponse);

		return ResponseEntity.ok(resource);
	}

	@PostMapping("/insertUserDTO")
	ResponseEntity<UsersDTO.Response> insertUserDTO(@RequestBody @Valid UsersDTO.Create dto) throws Exception {
		UsersDTO.Response userDtoResponse = userService.insertUserDto(dto);
		return ResponseEntity.ok(userDtoResponse);
	}

	@PutMapping("/updateUserDTO/{id}")
	ResponseEntity<UsersDTO.Response> updateUserDTO(
			@ApiParam(required = true, example = "1") @PathVariable final Integer id,
			@RequestBody UsersDTO.Update dto
	) throws Exception {
		UsersDTO.Response userDtoResponse = userService.updateUserDto(id, dto);
		return ResponseEntity.ok(userDtoResponse);
	}

	@DeleteMapping("/deleteUserDTO/{id}")
	ResponseEntity<?> deleteUserDTO(
			@ApiParam(required = true, example = "1") @PathVariable final Integer id
	) {
		userService.deleteUserDto(id);
		return ResponseEntity.noContent().build();
	}

//	@Secured("ADMIN")
	@GetMapping("/selectUser")
	private List<Users> selectUsers() {
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
	
	@GetMapping("/deleteUser")
	private String deleteUser(
		@RequestParam(name="id") Integer id
	) {
		userService.deleteUser(id);
		return id + " 번째 삭제완료";
	}
}