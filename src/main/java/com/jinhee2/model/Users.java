package com.jinhee2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user")
public class Users {
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

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	@ElementCollection(fetch = FetchType.EAGER)
	public List<UserRole> userRoles = new ArrayList<>();
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userRoles.stream().map( role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
	}
}