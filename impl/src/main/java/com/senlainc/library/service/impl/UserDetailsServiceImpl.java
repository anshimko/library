package com.senlainc.library.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.UserDAO;
import com.senlainc.library.entity.User;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByLogin(username);
		
		return (user == null) ? null : new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
				getAuthorities(user));
	}

}
