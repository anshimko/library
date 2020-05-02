package com.senlainc.library.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.service.RentService;

@RestController
public class RentController {
	
	@Autowired
	private RentService rentService;
	
	@GetMapping(value = "/rents/{id}") // this id book
	public ResponseEntity<List<RentHistory>> read(@PathVariable 
									 @Min(value = 1, message = "id must be greater than or equal to 1") int id) {
		
		final List<RentHistory> rentHistory = rentService.read(id);
		

		return rentHistory != null ? new ResponseEntity<>(rentHistory, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/rents/available")
	public ResponseEntity<List<RentHistory>> readAvailable() {
		final List<RentHistory> rentHistorys = rentService.readAvailable();

		return rentHistorys != null && !rentHistorys.isEmpty() ? new ResponseEntity<>(rentHistorys, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/rents/borrow")
	public ResponseEntity<List<RentHistory>> readBorrow() {
		final List<RentHistory> rentHistorys = rentService.readBorrow();

		return rentHistorys != null && !rentHistorys.isEmpty() ? new ResponseEntity<>(rentHistorys, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/rents/borrow/overdue")
	public ResponseEntity<List<RentHistory>> readBorrowOverdue() {
		final List<RentHistory> rentHistorys = rentService.readBorrowOverdue();

		return rentHistorys != null && !rentHistorys.isEmpty() ? new ResponseEntity<>(rentHistorys, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/rents")
	public ResponseEntity<?> create(@RequestBody RentHistory rentHistory) {
		
		rentService.create(rentHistory);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/rents/returned/{id}")
	public ResponseEntity<?> returnRentHistory(@PathVariable(name = "id") 
									@Min(value = 1, message = "id must be greater than or equal to 1") int id) {
		final boolean updated = rentService.returned(id);

		return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

}
