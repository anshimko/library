package com.senlainc.library.service;

import java.util.List;

import com.senlainc.library.entity.Book;

public interface BookService {

	void create(Book book);

	List<Book> readAll();

	Book read(Integer id);

	boolean update(Book book, Integer id);

	boolean delete(Integer id);

}
