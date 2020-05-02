package com.senlainc.library.service;

import java.util.List;

import javax.validation.constraints.Min;

import com.senlainc.library.entity.RentHistory;

public interface RentService {

	List<RentHistory> read(int id);

	List<RentHistory> readAvailable();

	List<RentHistory> readBorrow();

	List<RentHistory> readBorrowOverdue();

	void create(RentHistory rentHistory);

	boolean returned(int id);

}
