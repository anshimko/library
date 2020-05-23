package com.senlainc.library.dao.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.BookDAO;
import com.senlainc.library.entity.Author;
import com.senlainc.library.entity.Book;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class BookDAOImpl extends AbstractDAO<Book> implements BookDAO {
	
	private static final String QUERY_FROM_BOOK = "from Book";
	
	public BookDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		setClazz(Book.class);
	}

	@Override
	public Optional<Book> save(Book book) {
		Session session = sessionFactory.getCurrentSession();
		
		book.getAuthors().forEach(author -> author.getBooks().add(book));
		session.persist(book);
		
		return Optional.ofNullable(session.get(Book.class, book.getId()));
	}

	
	@Override
	public Optional<Book> get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Book book = session.get(Book.class, id);
		
		if (book == null) {
			throw new RecordNotFoundException("Book id '" + id + "' does no exist");
		}

		Hibernate.initialize(book.getAuthors());
		Hibernate.initialize(book.getCatalogs());

		return Optional.of(book);
	}

	@Override
	public List<Book> getAll() {
		Session session = sessionFactory.getCurrentSession();

		List<Book> books = session.createQuery(QUERY_FROM_BOOK, Book.class).list();
		books.forEach(book -> {
			Hibernate.initialize(book.getAuthors());
			Hibernate.initialize(book.getCatalogs());
		});

		return books;
	}

	@Override
	public Boolean deleteById(Integer id) {
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
