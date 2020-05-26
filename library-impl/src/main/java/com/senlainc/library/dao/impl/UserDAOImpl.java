package com.senlainc.library.dao.impl;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.UserDAO;
import com.senlainc.library.entity.User;
import com.senlainc.library.entity.UserInfo;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO{

	private static final String FIELD_LOGIN = "login";

	public UserDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		setClazz(User.class);
	}	

	@Override
	public Optional<User> save(User user) {
		
		Session session = getCurrentSession();
		user.getUserInfo().setUser(user);
		session.saveOrUpdate(user);
		Optional<User> optional = Optional.ofNullable(session.get(User.class, user.getId()));

		return optional;
	}

	@Override
	public Optional<User> update(User user) {
		Session session = getCurrentSession();
		
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
		
		Optional<User> optional = Optional.ofNullable((User) session.merge(user));

		return optional;
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
