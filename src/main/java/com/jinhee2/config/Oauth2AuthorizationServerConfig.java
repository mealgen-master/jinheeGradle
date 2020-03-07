package com.jinhee2.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.jinhee2.service.UserDetailsServiceImpl;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;

//	authenticationManager에 의존하여 manager를 가져온다.
	@Autowired
	public AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Value("${security.oauth2.jwt.signkey}")
	private String signkey;
	
//	 signKey 공유방식
	@Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signkey);
		return converter;
    }
	
	
//	@Value("classpath:jinhee.jks")
//	public Resource resourceFile;
//
//    @Bean
//	public JwtAccessTokenConverter jwtAccessTokenConverter() {
//    	KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resourceFile, "jinheepass".toCharArray());
//    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//    	converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jinhee"));
//    	return converter;
//    }
	
	
	// 클라이언트 clientDetail 관련된 설정
	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception {
		client.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
				.tokenStore(new JdbcTokenStore(dataSource))
//        		.accessTokenConverter(jwtAccessTokenConverter())
	        	.authenticationManager(authenticationManager)
    			.userDetailsService(userDetailsServiceImpl);
    }

	// 보호받는 리소스에 대한 접근제어와 접근규칙 설정
	// 각각 /oauth/token_key, /oauth/check_token에 대한 엔드포인트에 대한 설정
//	@Override
//    public void configure(AuthorizationServerSecurityConfigurer security) {
//		security
//				.tokenKeyAccess("perAccess()")
//	            .checkTokenAccess("isAuthenticated()");
//    }
}
