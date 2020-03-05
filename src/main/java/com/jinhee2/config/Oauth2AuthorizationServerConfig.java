package com.jinhee2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
//	@Bean
//	public TokenStore tokenStore() {
//		return 
//	}

	// authenticationManager에 의존하여 manager를 가져온다.
//	@Autowired
//	private AuthenticationManager authenticationManager;
	
	// 클라이언트 패스워드 인증
	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception {
		client.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	
//	@Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints.authenticationManager(authenticationManager);
//    }
}
