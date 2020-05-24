package com.senlainc.library.dao;

import java.util.List;

import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.BookReturnDTO;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.search.SearchCriteria;

public interface RentDAO {

	List<RentHistory> readByBook(Integer id);

	List<Book> readAvailable(int page, int size, List<SearchCriteria> params);

	List<BookReturnDTO> readBorrow(int page, int size, List<SearchCriteria> params);

	List<BookReturnDTO> readBorrowOverdue(int page, int size, List<SearchCriteria> params);

	void create(RentHistory rentHistory);

	boolean returned(Integer id);

}
