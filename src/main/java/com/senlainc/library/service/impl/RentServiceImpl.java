package com.senlainc.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.RentDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.BookReturnDTO;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.service.RentService;

@Service
public class RentServiceImpl implements RentService{
	
	@Autowired
	private RentDAO rentDAO;

	@Override
	@Transactional
	public List<RentHistory> readByBook(int id) {
		return rentDAO.readByBook(id);
	}

	@Override
	@Transactional
	public List<Book> readAvailable() {
		return rentDAO.readAvailable();
	}

	@Override
	@Transactional
	public List<BookReturnDTO> readBorrow() {
		return rentDAO.readBorrow();
	}

	@Override
	@Transactional
	public List<BookReturnDTO> readBorrowOverdue() {
		return rentDAO.readBorrowOverdue();
	}

	@Override
	@Transactional
	public void create(RentHistory rentHistory) {
		rentDAO.create(rentHistory);
	}

	@Override
	@Transactional
	public boolean returned(int id) {
		return rentDAO.returned(id);
	}

}
