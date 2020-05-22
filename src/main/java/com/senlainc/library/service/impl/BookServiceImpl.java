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
@Transactional
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookDAO bookDAO;

	@Override
	@Secured("ROLE_ADMIN")
	public Book create(Book book) {
		return bookDAO.create(book);
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	public List<Book> readAll() {
		return bookDAO.readAll();
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	public Book read(Integer id) {
		return bookDAO.read(id);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public Book update(Book book) {
		return bookDAO.update(book);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public boolean delete(Integer id) {
		return bookDAO.delete(id);
	}

}
