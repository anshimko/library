package com.senlainc.library.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.BookDAO;
import com.senlainc.library.entity.Author;
import com.senlainc.library.entity.Book;
import com.senlainc.library.exception.RecordNotFoundException;
import com.senlainc.library.search.EntitySearchQueryCriteriaConsumer;
import com.senlainc.library.search.SearchCriteria;

@Repository
public class BookDAOImpl extends AbstractDAO<Book> implements BookDAO {
		
	public BookDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		setClazz(Book.class);
	}

	@Override
	public Optional<Book> save(Book book) {
		Session session = getCurrentSession();
		
		book.getAuthors().forEach(author -> author.getBooks().add(book));
		session.persist(book);
		
		return Optional.ofNullable(session.get(Book.class, book.getId()));
	}
	
	@Override
	public Optional<Book> update(Book book) {
		
		Session session = getCurrentSession();
		
		book.getAuthors().forEach(author -> author.getBooks().add(book));
		session.merge(book);
		
		return Optional.ofNullable(session.get(Book.class, book.getId()));
	}

	
	@Override
	public Optional<Book> get(Integer id) {
		Session session = getCurrentSession();
		Book book = session.get(Book.class, id);
		
		if (book == null) {
			throw new RecordNotFoundException("Book id '" + id + "' does no exist");
		}

		Hibernate.initialize(book.getAuthors());
		Hibernate.initialize(book.getCatalogs());

		return Optional.of(book);
	}

	@Override
	public List<Book> getAll(int page, int size, List<SearchCriteria> params) {
		
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
		Root<Book> root = criteria.from(Book.class);
		
		// search by parameters
		Predicate predicate = cb.conjunction();
		EntitySearchQueryCriteriaConsumer searchConsumer =  new EntitySearchQueryCriteriaConsumer(predicate, cb, root);
		params.stream().forEach(searchConsumer);
		predicate = searchConsumer.getPredicate();
		criteria.where(predicate);
		
		// pagination
		CriteriaQuery<Book> select = criteria.select(root);
		TypedQuery<Book> typedQuery = getCurrentSession().createQuery(select);
		typedQuery.setFirstResult((page - 1) * size);
		typedQuery.setMaxResults(size);

		List<Book> books = typedQuery.getResultList();
		
		books.forEach(book -> {
			Hibernate.initialize(book.getAuthors());
			Hibernate.initialize(book.getCatalogs());
		});

		return books;
	}

	@Override
	public Boolean deleteById(Integer id) {
		Session session = getCurrentSession();
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
