package com.senlainc.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.UserDAO;
import com.senlainc.library.entity.User;
import com.senlainc.library.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.create(user);	
	}

	@Override
	@Secured("ROLE_ADMIN")
	public List<User> readAll() {
		return userDAO.readAll();
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	public User read(Integer id) {
		return userDAO.read(id);
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	public boolean update(User user, Integer id) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.update(user, id);
		return true;
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	public boolean delete(Integer id) {
		userDAO.delete(id);
		return true;
	}

}
