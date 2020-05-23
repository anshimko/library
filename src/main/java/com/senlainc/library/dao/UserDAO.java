package com.senlainc.library.dao;

import com.senlainc.library.entity.User;

public interface UserDAO extends GenericDAO<User>{

	public User findByLogin(String login);

}
