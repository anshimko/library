package com.senlainc.library.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.BookDAO;
import com.senlainc.library.entity.Author;
import com.senlainc.library.entity.Book;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class BookDAOImpl implements BookDAO {
	
	private static final String QUERY_FROM_BOOK = "from Book";

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(Book book) {
		Session session = sessionFactory.getCurrentSession();
		
		book.getAuthors().forEach(author -> author.getBooks().add(book));
		session.persist(book);
	}

	@Override
	public List<Book> readAll() {
		Session session = sessionFactory.getCurrentSession();

		List<Book> books = session.createQuery(QUERY_FROM_BOOK, Book.class).list();
		books.forEach(book -> {
			Hibernate.initialize(book.getAuthors());
			Hibernate.initialize(book.getCatalogs());
		});

		return books;
	}

	@Override
	public Book read(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Book book = session.get(Book.class, id);
		if (book == null) {
			throw new RecordNotFoundException("Book id '" + id + "' does no exist");
		}

		Hibernate.initialize(book.getAuthors());
		Hibernate.initialize(book.getCatalogs());

		return book;
	}

	@Override
	public boolean update(Book book, Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		Book oldBook = session.get(Book.class, id);
		oldBook.setTitle(book.getTitle());
		
		return true;
	}

	@Override
	public boolean delete(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Book book = session.byId(Book.class).load(id);
		
		if(book == null) {
			throw new RecordNotFoundException("Book id '" + id + "' does no exist");
		}
		
		for (Author author : book.getAuthors()) {
		    if (author.getBooks().size() == 1) {
		        session.delete(author);
		    } else {
		        author.getBooks().remove(book);
		    }
		}
		
		session.delete(book);
		return true;
	}

}
