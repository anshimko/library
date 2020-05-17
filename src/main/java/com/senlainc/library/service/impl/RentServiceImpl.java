package com.senlainc.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
	@Secured("ROLE_ADMIN")
	@Transactional
	public List<RentHistory> readByBook(int id) {
		return rentDAO.readByBook(id);
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	@Transactional
	public List<Book> readAvailable() {
		return rentDAO.readAvailable();
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public List<BookReturnDTO> readBorrow() {
		return rentDAO.readBorrow();
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public List<BookReturnDTO> readBorrowOverdue() {
		return rentDAO.readBorrowOverdue();
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public void create(RentHistory rentHistory) {
		rentDAO.create(rentHistory);
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public boolean returned(int id) {
		return rentDAO.returned(id);
	}

}
