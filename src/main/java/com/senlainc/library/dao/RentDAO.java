package com.senlainc.library.dao;

import java.util.List;

import com.senlainc.library.entity.RentHistory;

public interface RentDAO {

	List<RentHistory> read(int id);

	List<RentHistory> readAvailable();

	List<RentHistory> readBorrow();

	List<RentHistory> readBorrowOverdue();

	void create(RentHistory rentHistory);

	boolean returned(int id);

}
