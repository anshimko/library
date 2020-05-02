package com.senlainc.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Transactional
	public void create(Book book) {
		bookDAO.create(book);
	}

	@Override
	@Transactional
	public List<Book> readAll() {
		return bookDAO.readAll();
	}

	@Override
	@Transactional
	public Book read(int id) {
		return bookDAO.read(id);
	}

	@Override
	@Transactional
	public boolean update(Book book, int id) {
		return bookDAO.update(book, id);
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		return bookDAO.delete(id);
	}

}
