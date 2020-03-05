package com.jinhee2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jinhee2.model.UserDetailsImpl;
import com.jinhee2.model.Users;
import com.jinhee2.repository.UserJpaRepository;

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
		
		// 찾은 레코드를 기준으로 User 설정
		UserDetailsImpl userDetailImpl = new UserDetailsImpl(users);
		return userDetailImpl;
	}
}
