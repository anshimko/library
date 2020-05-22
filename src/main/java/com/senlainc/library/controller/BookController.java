package com.senlainc.library.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.Book;
import com.senlainc.library.service.BookService;

@RestController
@RequestMapping(value = "/books")
@Validated
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid Book book) {
		
		Book newBook = bookService.create(book);
		return newBook != null ? read(newBook.getId()) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@GetMapping
	public ResponseEntity<List<Book>> read() {
		final List<Book> books = bookService.readAll();

		return books != null && !books.isEmpty() ? new ResponseEntity<>(books, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Book> read(@PathVariable 
									 @Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		
		final Book book = bookService.read(id);
		

		return book != null ? new ResponseEntity<>(book, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer id, @RequestBody @Valid Book book) {
		final Book bookUpdated = bookService.update(book);

		return bookUpdated != null ? read(bookUpdated.getId()) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		final boolean deleted = bookService.delete(id);

		return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}
	

}
