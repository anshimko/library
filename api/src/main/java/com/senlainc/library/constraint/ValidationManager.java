package com.senlainc.library.constraint;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		
	}

}
