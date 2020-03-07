package com.jinhee2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OauthAccessToken {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String authentication_id;
	
	@Column
	private String token_id;
	
	@Column
	@Lob
	private byte[] token;
	
	@Column(unique=true)
	private String user_name;

	@Column
	private String client_id;
	
	@Column
	@Lob
	private byte[] authentication;
	
	@Column
	private String refresh_token;

}
