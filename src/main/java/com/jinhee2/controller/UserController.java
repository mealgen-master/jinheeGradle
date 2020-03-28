package com.jinhee2.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.jinhee2.dto.UsersDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jinhee2.model.UserRole;
import com.jinhee2.model.UserRole.Role;
import com.jinhee2.model.Users;
import com.jinhee2.service.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UserController.BASE_PATH, produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {
	static final String BASE_PATH = "/api/user";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserResourceAssembler userResourceAssembler;

	@GetMapping("/selectUserDTO/{id}")
	ResponseEntity<EntityModel<UsersDTO.Response>> selectUserDTO(@ApiParam(required = true , example = "1") @PathVariable final  Integer id) {
		UsersDTO.Response userDtoResponse = userService.findUserDto(id);

		EntityModel<UsersDTO.Response> resource = userResourceAssembler.toModel(userDtoResponse);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.buildAndExpand(userDtoResponse)
				.toUri();

		return ResponseEntity
				.created(uri)
				.body(resource);

//		return ResponseEntity.ok(resource);
	}

	@PostMapping("/insertUserDTO")
	ResponseEntity<EntityModel<UsersDTO.Response>> insertUserDTO(@RequestBody @Valid UsersDTO.Create dto) throws Exception {
		UsersDTO.Response userDtoResponse = userService.insertUserDto(dto);

		EntityModel<UsersDTO.Response> resource = userResourceAssembler.toModel(userDtoResponse);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.build()
				.toUri();

		return ResponseEntity
				.created(uri)
				.body(resource);
	}

	@PutMapping("/updateUserDTO/{id}")
	ResponseEntity<EntityModel<UsersDTO.Response>> updateUserDTO(
			@ApiParam(required = true, example = "1") @PathVariable final Integer id,
			@RequestBody UsersDTO.Update dto
	) throws Exception {
		UsersDTO.Response userDtoResponse = userService.updateUserDto(id, dto);

		EntityModel<UsersDTO.Response> resource = userResourceAssembler.toModel(userDtoResponse);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.build()
				.toUri();

//		String teststring = "this is header";

		return ResponseEntity
				.created(uri)
//				.header(teststring,teststring)
				.body(resource);
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
	public List<Users> selectUsers() {
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