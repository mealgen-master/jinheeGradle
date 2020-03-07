package com.jinhee2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;



public class UserDetailsImpl extends User {
	public UserDetailsImpl (Users users) {
		super(users.getName(), users.getPassword(), authorities(users.getUserRoles()));
	}
	
	// 인증요청에 대한 권한
	private static Collection<? extends GrantedAuthority> authorities(List<UserRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(); 
		
		roles.forEach(
		// 람다표현식
			role -> authorities.add(new SimpleGrantedAuthority(role.getRolename().toString()))
		);
		return authorities;
	}
}
