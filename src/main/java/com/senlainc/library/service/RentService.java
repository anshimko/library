package com.senlainc.library.service;

import java.util.List;

import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.BookReturnDTO;
import com.senlainc.library.entity.RentHistory;

public interface RentService {

	List<RentHistory> readByBook(int id);

	List<Book> readAvailable();

	List<BookReturnDTO> readBorrow();

	List<BookReturnDTO> readBorrowOverdue();

	void create(RentHistory rentHistory);

	boolean returned(int id);

}