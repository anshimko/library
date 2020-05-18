package com.senlainc.library.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.CatalogDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.Catalog;
import com.senlainc.library.exception.RecordNotFoundException;

@Repository
public class CatalogDAOImpl implements CatalogDAO{
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void create(Catalog catalog) {
		Session session = sessionFactory.getCurrentSession();
		
		session.persist(catalog);
	}

	@Override
	public Catalog read(int id) {
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
			
		return catalog;
	}

	@Override
	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();

		Catalog catalog = new Catalog();
		catalog.setId(id);
		session.delete(catalog);
		return true;
	}

	@Override
	public void addBookInCatalog(int catalogId, int bookId) {
		Session session = sessionFactory.getCurrentSession();
		
//		Catalog catalog = new Catalog();
//		catalog.setId(catalogId);
//		
//		Book book = new Book();
//		book.setId(bookId);
//		book.getCatalogs().add(catalog);
//		
//		catalog.getBooks().add(book);
//		
//		session.saveOrUpdate(catalog);
		
		
		Catalog catalog = session.get(Catalog.class, catalogId);
		Book book = session.get(Book.class, bookId);
		
		
		catalog.getBooks().add(book);
		book.getCatalogs().add(catalog);
		
		
	}


}
