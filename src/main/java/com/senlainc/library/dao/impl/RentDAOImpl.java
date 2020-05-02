package com.senlainc.library.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.RentDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.entity.User;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class RentDAOImpl implements RentDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<RentHistory> read(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<RentHistory> criteriaQuery = builder.createQuery(RentHistory.class);
		Root<RentHistory> root = criteriaQuery.from(RentHistory.class);
		criteriaQuery.select(root).where(builder.equal(root.get("book").get("id"), id));

		List<RentHistory> read = session.createQuery(criteriaQuery).getResultList();

		return read;
	
	}

	@Override
	public List<RentHistory> readAvailable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RentHistory> readBorrow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RentHistory> readBorrowOverdue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(RentHistory rentHistory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean returned(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
