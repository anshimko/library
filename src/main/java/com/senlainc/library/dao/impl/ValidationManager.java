package com.senlainc.library.dao.impl;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.entity.User;

@Repository
public class ValidationManager {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly = true)
	public boolean validateUnique(Class<?> clazz, String name, Object value) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.eq(name, value));
		return criteria.uniqueResult() == null ? true : false;
		
//		CriteriaBuilder cb = session.getCriteriaBuilder();
//		CriteriaQuery<User> criteria = cb.createQuery(User.class);
//		Root<User> root = criteria.from(User.class);
//		criteria.select(root).where(cb.like(root.get("login"), login));
		
	}

}
