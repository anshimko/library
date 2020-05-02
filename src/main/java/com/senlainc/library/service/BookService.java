package com.senlainc.library.service;

import java.util.List;

import com.senlainc.library.entity.Book;

public interface BookService {

	void create(Book book);

	List<Book> readAll();

	Book read(int id);

	boolean update(Book book, int id);

	boolean delete(int id);

}
