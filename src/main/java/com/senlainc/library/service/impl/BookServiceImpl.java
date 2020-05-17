package com.senlainc.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.BookDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookDAO bookDAO;

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public void create(Book book) {
		bookDAO.create(book);
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	@Transactional
	public List<Book> readAll() {
		return bookDAO.readAll();
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	@Transactional
	public Book read(int id) {
		return bookDAO.read(id);
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public boolean update(Book book, int id) {
		return bookDAO.update(book, id);
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public boolean delete(int id) {
		return bookDAO.delete(id);
	}

}
