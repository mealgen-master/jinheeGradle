package com.jinhee2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;



public class UserDetailsImpl extends User {
	public UserDetailsImpl (Users users) {
		super(users.getName(), users.getPassword(), authorities(users.getUserRoles()));
	}
	
	// 인증요청에 대한 권한
	private static Collection<? extends GrantedAuthority> authorities(List<UserRole> roles) {
//		List<GrantedAuthority> authorities = new ArrayList<>();

//		roles.forEach(
//			role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()))
//					.collect(Collectors.toSet())z
//		);

//		return authorities;

		// 인스턴스를 생성할 때 인자로 들어오는 users에서 roles를 가져온다.
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.enumToString(role.getRolename()))).collect(Collectors.toSet());
	}
}
