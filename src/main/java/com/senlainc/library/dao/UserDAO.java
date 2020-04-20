package com.senlainc.library.dao;

import java.util.List;

import com.senlainc.library.entity.User;

public interface UserDAO {

	public void create(User user);

	public List<User> readAll();

	public User read(int id);

	public boolean update(User user, int id);
	
	public boolean delete(int id);

	public User findByLogin(String login);

}
