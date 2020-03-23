package com.jinhee2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
		// oauth2
		"/oauth/**",
		"/api/oauth2/**",
		"/api/user/insertUser**",

		// swagger2
		"/v2/api-docs",
		"/swagger-resources",
		"/swagger-resources/**",
		"/configuration/ui",
		"/configuration/security",
		"/swagger-ui.html",
		"/webjars/**"
	};

	@Autowired
	DataSource dataSource;
	
	@Value("${security.oauth2.jwt.signkey}")
	private String signkey;
	
//	@Bean
//	public TokenStore tokenStore() {
//		return new JdbcTokenStore(dataSource);
//	};
	
//	 signKey 공유방식
	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		
		converter.setSigningKey(signkey);
		converter.setVerifierKey(signkey);
		
		return converter;
    }
	
//	@Value("classpath:jinhee.jks")
//	Resource resourceFile;
//
//	@Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////    	KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new FileSystemResource("src/main/resources/oauth2jwt.jks"), "oauth2jwtpass".toCharArray());
//		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resourceFile, "jinheepass".toCharArray());
//    	converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jinhee"));
////		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth22jwt"));
//		return converter;
//	}

//	@Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		resources.tokenStore(tokenStore());
//	}
    
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 모든 인증요청 중에
		http.authorizeRequests()
			.antMatchers(AUTH_WHITELIST).permitAll()
			.antMatchers("/selectUser").access("#oauth2.hasScope('read')")
			.antMatchers("/updateUser**").access("#oauth2.hasScope('write')")
			.anyRequest().authenticated();
	}
}
