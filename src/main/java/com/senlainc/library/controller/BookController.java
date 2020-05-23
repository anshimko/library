package com.senlainc.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.Book;
import com.senlainc.library.service.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookController extends AbstractController<Book, BookService> {

	protected BookController(BookService service) {
		super(service);
	}

}
