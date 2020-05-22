package com.senlainc.library.dao;

import java.util.List;

import com.senlainc.library.entity.User;

public interface UserDAO {

	public void create(User user);

	public List<User> readAll();

	public User read(Integer id);

	public boolean update(User user, Integer id);
	
	public boolean delete(Integer id);

	public User findByLogin(String login);

}
