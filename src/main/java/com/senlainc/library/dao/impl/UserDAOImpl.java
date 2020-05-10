package com.senlainc.library.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.UserDAO;
import com.senlainc.library.entity.User;
import com.senlainc.library.entity.UserInfo;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(User user) {
		Session session = sessionFactory.getCurrentSession();

		user.getUserInfo().setUser(user);
		session.saveOrUpdate(user);
	}

	@Override
	public List<User> readAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from User", User.class).list();
	}

	@Override
	public User read(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		if(user == null) {
			throw new RecordNotFoundException("User id '" + id + "' does no exist");
		}
				
		return user;
	}

	@Override
	public boolean update(User user, int id) {
		Session session = sessionFactory.getCurrentSession();
		
		UserInfo userInfo = user.getUserInfo();
		User userOld = session.get(User.class, id);
		
		if(userOld == null) {
			throw new RecordNotFoundException("User id '" + id + "' does no exist");
		}			
		
		session.evict(userOld);
		
		user.setId(id);
		user.setRole(userOld.getRole());
		user.setUserInfo(null);
		session.saveOrUpdate(user);
		
		userInfo.setUser(user);
		userInfo.setId(userOld.getUserInfo().getId());
		session.saveOrUpdate(userInfo);
		return true;
	}

	@Override
	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.byId(User.class).load(id);
		
		if(user == null) {
			throw new RecordNotFoundException("User id '" + id + "' does no exist");
		}	
		session.delete(user);
		return true;
		
	}

	@Override
	public User findByLogin(String login) {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root).where(cb.like(root.get("login"), login));

		return session.createQuery(criteria).getResultList().stream().findAny().orElse(null);
	}

}