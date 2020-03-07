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

// 인증절차구현 supports -> authenticate
@RequiredArgsConstructor // 지정된 속성에 한해서만 생성자를 만듬
// 커스텀 프로바이더를 사용하여 권한을 부여한다.
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private UserJpaRepository userJpaRepository;
    
	// 사용자 Princifal(인증여부)를 파라미터로 받아옴
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
