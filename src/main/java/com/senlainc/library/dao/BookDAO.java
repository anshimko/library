package com.senlainc.library.dao;

import java.util.List;

import com.senlainc.library.entity.Book;

public interface BookDAO {

	Book create(Book book);

	List<Book> readAll();

	Book read(Integer id);
	
	Book update(Book book);

	boolean delete(Integer id);

}
