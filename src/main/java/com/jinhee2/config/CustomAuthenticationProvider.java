package com.jinhee2.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jinhee2.model.UserDetailsImpl;
import com.jinhee2.model.Users;
import com.jinhee2.repository.UserJpaRepository;

// 인증절차구현 supports -> authenticate
//@RequiredArgsConstructor // 지정된 속성에 한해서만 생성자를 만듬
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	// AuthenticationProvider = 인증요청이나 인증된 Principal에 대한 토큰
	private PasswordEncoder passwordEncoder;
    private UserJpaRepository userJpaRepository;
    
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
        String password = authentication.getCredentials().toString();
 
        Users user = userJpaRepository.findByName(name);
 
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("password is not valid");
        }
 
        return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}
}
