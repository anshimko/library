package com.senlainc.library.service;

import java.util.List;

import com.senlainc.library.entity.Book;

public interface BookService {

	Book create(Book book);

	List<Book> readAll();

	Book read(Integer id);

	Book update(Book book);

	boolean delete(Integer id);

}
