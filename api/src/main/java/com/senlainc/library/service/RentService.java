package com.senlainc.library.service;

import java.util.List;

import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.search.SearchCriteria;

public interface RentService {

	List<RentHistory> readByBook(Integer id);

	List<Book> readAvailable(int page, int size, List<SearchCriteria> params);

	List<Book> readBorrow(int page, int size, List<SearchCriteria> params);

	List<Book> readBorrowOverdue(int page, int size, List<SearchCriteria> params);

	void create(RentHistory rentHistory);

	boolean returned(Integer id);

}
