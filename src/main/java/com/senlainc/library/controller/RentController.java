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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.BookReturnDTO;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.service.RentService;

@RestController
@RequestMapping(value = "/rents")
public class RentController {
	
	@Autowired
	private RentService rentService;
	
	@GetMapping(value = "/book/{id}") // this is id book
	public ResponseEntity<List<RentHistory>> read(@PathVariable 
									 @Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		
		final List<RentHistory> rentHistory = rentService.readByBook(id);
		

		return rentHistory != null ? new ResponseEntity<>(rentHistory, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/books/available")
	public ResponseEntity<List<Book>> readAvailable() {
		final List<Book> books = rentService.readAvailable();

		return books != null && !books.isEmpty() ? new ResponseEntity<>(books, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/borrow")
	public ResponseEntity<List<BookReturnDTO>> readBorrow() {
		final List<BookReturnDTO> books = rentService.readBorrow();

		return books != null && !books.isEmpty() ? new ResponseEntity<>(books, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/borrow/overdue")
	public ResponseEntity<List<BookReturnDTO>> readBorrowOverdue() {
		final List<BookReturnDTO> books = rentService.readBorrowOverdue();

		return books != null && !books.isEmpty() ? new ResponseEntity<>(books, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody RentHistory rentHistory) {
		
		rentService.create(rentHistory);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/book/{id}")
	public ResponseEntity<?> returned(@PathVariable(name = "id") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		final boolean updated = rentService.returned(id);

		return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

}
