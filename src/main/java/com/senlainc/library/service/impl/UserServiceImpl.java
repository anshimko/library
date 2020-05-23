package com.senlainc.library.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senlainc.library.dao.UserDAO;
import com.senlainc.library.entity.User;
import com.senlainc.library.service.UserService;

@Service
public class UserServiceImpl extends AbstractService<User, UserDAO> implements UserService {

	public UserServiceImpl(UserDAO repository) {
		super(repository);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	@Override
	public Optional<User> update(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.update(user);
	}

}
