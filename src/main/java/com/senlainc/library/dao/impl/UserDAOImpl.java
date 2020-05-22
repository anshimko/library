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
import com.senlainc.library.entity.UserRole;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final String FIELD_LOGIN = "login";
	
	private static final String QUERY_FROM_USER = "from User";

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User create(User user) {
		Session session = sessionFactory.getCurrentSession();
		user.getUserInfo().setUser(user);
		
		session.saveOrUpdate(user);

		return session.get(User.class, user.getId());
	}

	@Override
	public List<User> readAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(QUERY_FROM_USER, User.class).list();
	}

	@Override
	public User read(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		if(user == null) {
			throw new RecordNotFoundException("User id '" + id + "' does no exist");
		}
		return user;
	}

	@Override
	public User update(User user) {

		Session session = sessionFactory.getCurrentSession();
		
		Integer id = user.getId();
		UserInfo userInfo = user.getUserInfo();
		User userOld = session.get(User.class, id);
		
		if(userOld == null) {
			throw new RecordNotFoundException("User id '" + id + "' does no exist");
		}
		
		session.evict(userOld);
		
		user.setId(id);
		
		userInfo.setUser(user);
		userInfo.setId(userOld.getUserInfo().getId());
		session.saveOrUpdate(userInfo);
		
		return (User) session.merge(user);
	}

	@Override
	public boolean delete(Integer id) {
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
		criteria.select(root).where(cb.like(root.get(FIELD_LOGIN), login));

		return session.createQuery(criteria).getResultList().stream().findAny().orElse(null);
	}

}
