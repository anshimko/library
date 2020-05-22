package com.senlainc.library.service;

import java.util.List;

import com.senlainc.library.entity.User;

public interface UserService{

	public User create(User user);

	public List<User> readAll();

	public User read(Integer id);

	public User update(User user);
	
	public boolean delete(Integer id);

}
