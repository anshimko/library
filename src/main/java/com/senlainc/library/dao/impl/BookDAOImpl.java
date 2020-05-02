package com.senlainc.library.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.BookDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.entity.User;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class BookDAOImpl implements BookDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Book> readAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Book", Book.class).list();
	}

	@Override
	public Book read(int id) {
		Session session = sessionFactory.getCurrentSession();
		Book book = session.get(Book.class, id);
		if(book == null) {
			throw new RecordNotFoundException("Book id '" + id + "' does no exist");
		}
		
		RentHistory rentHistory = session.get(RentHistory.class, id);
		return book;
	}

	@Override
	public boolean update(Book book, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
