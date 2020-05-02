package com.senlainc.library.dao;

import java.util.List;

import com.senlainc.library.entity.Book;

public interface BookDAO {

	void create(Book book);

	List<Book> readAll();

	Book read(int id);

	boolean update(Book book, int id);

	boolean delete(int id);

}
