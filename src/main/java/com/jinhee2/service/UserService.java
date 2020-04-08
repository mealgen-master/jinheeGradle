package com.jinhee2.service;


import java.lang.annotation.Target;
import java.util.*;

import com.jinhee2.dto.UsersDTO;
import com.jinhee2.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jinhee2.model.OauthClientDetails;
import com.jinhee2.model.UserRole;
import com.jinhee2.model.UserRole.Role;
import com.jinhee2.model.Users;
import com.jinhee2.repository.ClientJpaRepository;
import com.jinhee2.repository.UserJpaRepository;

import javax.security.auth.login.AccountNotFoundException;

@Service
@AllArgsConstructor
public class UserService {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private ClientJpaRepository clientJpaRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;


	/*
	private Users validate(final String name) throws Exception {
		1.
		Users existUser = userJpaRepository.findByName(name)

		2.
		Users existUser = userJpaRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(id.toString()));

		if(!existUser.getName().equals(name)) {
			throw new Exception("Access is denied :: 이미 존재하는 이름입니다. ?");
		}
		return existUser;
	}
	*/

	private UsersDTO.Response toResponse(Users users) {
		return UserMapper.INSTANCE.toDto(users);
//		return modelMapper.map(users, UsersDTO.Response.class);
	}

	public UsersDTO.Response findUserDto(final Integer id){
		return toResponse(userJpaRepository.findById(id).get());
	}

	public UsersDTO.Response insertUserDto(UsersDTO.Create dto) throws Exception{
		/*
		// 중복체크 Exception 패키지에 추가 후 수정필요함
		Optional<Users> optionalUsers = userJpaRepository.findByName(dto.getName());

		if(optionalUsers.isPresent()) {
			throw new Exception("이미 존재하는 사용자입니다." + dto.getName());
		}
		*/

		// modelMapper
//		Users newUser = modelMapper.map(dto, Users.class);
//		return toResponse(userJpaRepository.save(newUser));

		// mapStruct
		Users user = new Users();
		user.setName(dto.getName());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		UserRole role = new UserRole();
		role.setRolename(dto.getRolename());

		user.setUserRoles(new ArrayList<>(Arrays.asList(role)));
		user.setName(dto.getName());

		return toResponse(userJpaRepository.save(user));
	}

	public UsersDTO.Response updateUserDto(
			final Integer id,
			UsersDTO.Update dto
	) throws Exception {
		UserRole userRole = new UserRole();
		userRole.setRolename(dto.getRolename());

		Users user = userJpaRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(id.toString()));
		user.setName(dto.getName());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setUserRoles(new ArrayList<>(Arrays.asList(userRole)));

		return toResponse(userJpaRepository.save(user));

		// name, password, role 수정
		// modelMapper.map(dto, user);
	}

	public void deleteUserDto(
			final Integer id
	) {
		userJpaRepository.deleteById(id);
	}


	//////////////////////////////////// prev: DTO && next: Java Object Model //////////////////////////////////////////


	public List<Users> findAll() {
		return userJpaRepository.findAll();
	}

	public void insertUser(Users user) {
		userJpaRepository.save(user);
	}

	public void insertClientDetails(OauthClientDetails oauthClientDetails) {
		clientJpaRepository.save(oauthClientDetails);
	}

	public void updateUser(Integer id, String name, String address, String phonenumber, String password, Role role) {
		Users user = userJpaRepository.findById(id).get();
		UserRole userRole = new UserRole(role);

		user.setName(name);
		user.setAddress(address);
		user.setPhonenumber(phonenumber);
		user.setPassword(passwordEncoder.encode(password));
		userRole.setRolename(role);
		user.setUserRoles(new LinkedList<UserRole>(Arrays.asList(userRole)));

		userJpaRepository.save(user);
	}

	public void deleteUser(Integer id) {
		userJpaRepository.deleteById(id);
	}

//	public List<User> findUser(Integer id, String name) {
//		return userJpaRepository.findByTwoColumn(id, name);
//	}
}