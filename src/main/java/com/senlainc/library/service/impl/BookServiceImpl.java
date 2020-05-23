package com.senlainc.library.service.impl;

import org.springframework.stereotype.Service;

import com.senlainc.library.dao.BookDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.service.BookService;

@Service
public class BookServiceImpl extends AbstractService<Book, BookDAO> implements BookService{
	
	public BookServiceImpl(BookDAO repository) {
		super(repository);
	}

}
