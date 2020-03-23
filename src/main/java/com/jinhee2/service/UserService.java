package com.jinhee2.service;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.jinhee2.dto.UsersDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinhee2.model.OauthClientDetails;
import com.jinhee2.model.UserRole;
import com.jinhee2.model.UserRole.Role;
import com.jinhee2.model.Users;
import com.jinhee2.repository.ClientJpaRepository;
import com.jinhee2.repository.UserJpaRepository;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	private ClientJpaRepository clientJpaRepository;

	// modelMapper 객체를 사용
	@Autowired
	private ModelMapper modelMapper;

	private UsersDTO.Response toResponse(Users users) {
		return modelMapper.map(users, UsersDTO.Response.class);
	}

	public List<Users> findAll() {
		return userJpaRepository.findAll();
	}

	public UsersDTO.Response findUser(final Integer id){
		return toResponse(userJpaRepository.findById(id).get());
	}


	public void insertClientDetails(OauthClientDetails oauthClientDetails) {
		clientJpaRepository.save(oauthClientDetails);
	}
	
	public void insertUser(Users user) {
		userJpaRepository.save(user);
	}
	
	public void updateUser(Integer id, String name, String address, String phonenumber, String password, Role role) {
		Users user = userJpaRepository.findById(id).get();
		UserRole userRole = new UserRole(role);
		
		user.setName(name);
		user.setAddress(address);
		user.setPhonenumber(phonenumber);
		user.setPassword(password);
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