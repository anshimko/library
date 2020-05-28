package com.senlainc.library.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthenticationProvider{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
		if(userDetails == null ) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
				
		String password = authentication.getCredentials().toString();
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Password is incorrect");
		}
		
		
//		if(!authentication.isAuthenticated()) {
//			throw new BadCredentialsException("Access is denied");
//		}
		
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
	
		return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
}