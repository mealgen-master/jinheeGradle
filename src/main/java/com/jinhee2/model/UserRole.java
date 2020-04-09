package com.jinhee2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name="user_role")
public class UserRole {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public enum Role {
		USER, ADMIN, PARENT, TEACHER;
	}

	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private Role rolename;

	public UserRole() {
	}
	
	public UserRole(Role rolename) {
		this.rolename = rolename;
	}

	public String enumToString(Role role) {
		return "ROLE_" + role;
	}
}
