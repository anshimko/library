package com.senlainc.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.UserDAO;
import com.senlainc.library.entity.User;
import com.senlainc.library.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.create(user);	
	}

	@Override
	@Transactional
	public List<User> readAll() {
		return userDAO.readAll();
	}

	@Override
	@Transactional
	public User read(int id) {
		return userDAO.read(id);
	}

	@Override
	@Transactional
	public boolean update(User user, int id) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.update(user, id);
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		userDAO.delete(id);
		return true;
	}

}
