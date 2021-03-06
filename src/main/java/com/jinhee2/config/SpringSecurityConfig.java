package com.jinhee2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jinhee2.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	// UserDetail을 상속받은 커스텀 Impl 구현체를 사용한다.
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	// DB와 화면에 입력된 사용자 정보에 대한 인증여부에 대한 검증 
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	// name을 기준으로 새로운 User를 찾고
	// 해당 유저에 대해 authoiritie를 포함한 값을 설정하여 인스턴스 생성 & 리턴
	@Override
	public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
	
	// 해당 메소드는 Authentication 빌더와 context를 포함한 delegator 객체를 리턴 -> Provider에 인증관련 위임
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
		auth
			.authenticationProvider(customAuthenticationProvider)
			.userDetailsService(userDetailsServiceImpl);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		security
//                .authorizeRequests()
//                .antMatchers("/oauth/**", "/oauth/token", "/oauth2/callback").permitAll()
//                .antMatchers("/selectUser").authenticated()
//		        .and()
//		        .httpBasic();
		
		// httpBasic으로 통신하며, 요청이 들어오는 요청에 대한 설정
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/oauth/**", "/oauth/token", "/oauth2/callback**").permitAll()
			.anyRequest().authenticated();

//			.antMatchers("/oauth/**", "/oauth/token", "/oauth2/callback**").permitAll()
//			.antMatchers("/selectUser").hasRole("{\"id\":1,\"rolename\":\"USER\"}")
//			.antMatchers("/selectUser").authenticated()
//			.anyRequest().permitAll()
//	        .and()
//	        .httpBasic();
	}
}
