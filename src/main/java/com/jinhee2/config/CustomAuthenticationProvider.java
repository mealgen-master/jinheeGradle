package com.jinhee2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jinhee2.model.Users;
import com.jinhee2.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor // 지정된 속성에 한해서만 생성자를 만듬
// authentication과 해당 user를 DB에서 조회하여 권한이 일치하는지 조회한다.
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private UserJpaRepository userJpaRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
        String password = authentication.getCredentials().toString();
 
        Users user = userJpaRepository.findByName(name);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("password is not valid");
        }

        return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
	}

	// 유효한 사용자인지 검증
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
