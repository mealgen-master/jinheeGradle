package com.jinhee2.service;

import com.jinhee2.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jinhee2.model.UserDetailsImpl;
import com.jinhee2.model.Users;
import com.jinhee2.repository.UserJpaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UserJpaRepository userJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userJpaRepository.findByName(username);
		
		if( users == null ) {
			throw new UsernameNotFoundException(username);
		}

		UserDetailsImpl userDetailImpl = new UserDetailsImpl(users);

		return userDetailImpl;
	}
}
