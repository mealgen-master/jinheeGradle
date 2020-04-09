package com.jinhee2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user")
public class Users implements UserDetails {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	public String name;

	@Column
	public String address;

	@Column
	public String password;

	@Column
	public String phonenumber;

//	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	@ElementCollection(fetch = FetchType.EAGER)
	public List<UserRole> userRoles = new ArrayList<>();

	// UserRole에서 role을 가져와서 List화 한다.
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userRoles.stream().map(role -> new SimpleGrantedAuthority(role.enumToString(role.getRolename()))).collect(Collectors.toSet());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true; // 만료
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true; // 막지않는다.
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true; // 인증만료기간
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true; // 사용한다.
	}
}