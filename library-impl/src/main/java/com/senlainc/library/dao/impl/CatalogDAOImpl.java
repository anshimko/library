package com.senlainc.library.dao.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.CatalogDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.Catalog;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class CatalogDAOImpl extends AbstractDAO<Catalog> implements CatalogDAO{
	
	public CatalogDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		setClazz(Catalog.class);
	}

	@Override
	public Optional<Catalog> get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		Catalog catalog = session.get(Catalog.class, id);
		if (catalog == null) {
			throw new RecordNotFoundException("Catalog id '" + id + "' does no exist");
		}
		
		Hibernate.initialize(catalog.getParentCatalog());
		Hibernate.initialize(catalog.getBooks());
		
		Set<Book> books = catalog.getBooks();
		
		session.evict(catalog);
		
		Set<Book> booksOnlyId = new HashSet<Book>();
		
		for(Book book : books) {
			booksOnlyId.add(new Book(book.getId()));
		}
		
		catalog.setBooks(booksOnlyId);
			
		return Optional.of(catalog);
	}

	@Override
	public void addBookInCatalog(Integer catalogId, Integer bookId) {
		Session session = sessionFactory.getCurrentSession();		
		
		Catalog catalog = session.get(Catalog.class, catalogId);
		Book book = session.get(Book.class, bookId);
		
		catalog.getBooks().add(book);
		book.getCatalogs().add(catalog);
		
	}

}
