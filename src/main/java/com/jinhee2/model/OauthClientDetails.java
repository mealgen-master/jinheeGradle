package com.jinhee2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OauthClientDetails {
	@Id
	@Column(unique = true)
	private String client_id;
	
	@Column
	private String client_secret; 
	
	@Column
	private String resource_ids; 
	
	// 클라이언트가 제한되는 범위
	@Column
	private String scope; 
	
	// 클라이언트가 허가받은 유형
	@Column
	private String authorized_grant_types; 
	
	@Column
	private String web_server_redirect_uri;
	
	// 클라이언트에서 허가받은 인가방법 -> springSecurity애서 UserDetail Service Principal 및 Authorities을 생성
	@Column
	private String authorities;
	
	@Column
	private Integer access_token_validity;
	
	@Column
	private Integer refresh_token_validity;
	
	@Column
	private String additional_information;
	
	@Column
	private String autoapprove;
}
