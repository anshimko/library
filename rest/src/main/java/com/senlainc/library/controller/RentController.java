package com.senlainc.library.controller;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.dto.BookReportDTO;
import com.senlainc.library.dto.RentDTO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.search.SearchCriteria;
import com.senlainc.library.service.RentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/rents")
public class RentController {
	
	@Autowired
	private RentService rentService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@ApiOperation(value = "Get book request history")
	@GetMapping(value = "/book/{id}") // this is id book
	public ResponseEntity<List<RentHistory>> read(@PathVariable 
									 @Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		
		final List<RentHistory> rentHistory = rentService.readByBook(id);
	
		return ResponseEntity.ok(rentHistory);
	}
	
	@ApiOperation(value = "Get a list of available books")
	@GetMapping(value = "/books/available")
	public ResponseEntity<List<Book>> readAvailable(@RequestParam("page") int page, 
													@RequestParam("size") int size, 
													@RequestParam(value = "search", required = false) String search) {
		
		List<SearchCriteria> params = AbstractController.fillParamsOfSearch(search);
		final List<Book> books = rentService.readAvailable(page, size, params);

		return ResponseEntity.ok(books);
	}
	
	@ApiOperation(value = "Get a list of borrow books")
	@GetMapping(value = "/borrow")
	public ResponseEntity<List<BookReportDTO>> readBorrow(@RequestParam("page") int page, 
														  @RequestParam("size") int size, 
														  @RequestParam(value = "search", required = false) String search) {
		
		List<SearchCriteria> params = AbstractController.fillParamsOfSearch(search);
		List<Book> books = rentService.readBorrow(page, size, params);
		
		List<BookReportDTO> bookReports = books.stream().map(book -> convertToDto(book)).collect(Collectors.toList());

		return ResponseEntity.ok(bookReports);
	}
	
	@ApiOperation(value = "Get a list of books with overdue returns")
	@GetMapping(value = "/borrow/overdue")
	public ResponseEntity<List<BookReportDTO>> readBorrowOverdue(@RequestParam("page") int page, 
																 @RequestParam("size") int size, 
																 @RequestParam(value = "search", required = false) String search) {
		
		List<SearchCriteria> params = AbstractController.fillParamsOfSearch(search);
		List<Book> books = rentService.readBorrowOverdue(page, size, params);
		
		List<BookReportDTO> bookReports = books.stream().map(book -> convertToDto(book)).collect(Collectors.toList());

		return ResponseEntity.ok(bookReports);
	}
	
	@ApiOperation(value = "Give a book to the reader")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody RentDTO rentDTO) throws ParseException {
		
		RentHistory rentHistory = convertToEntity(rentDTO);
		rentService.create(rentHistory);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Take a book from reader")
	@PutMapping(value = "/book/{id}")
	public Boolean returned(@PathVariable(name = "id") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
	
		return rentService.returned(id);
	}
	
	private RentHistory convertToEntity(RentDTO rentDto) throws ParseException {
		RentHistory rentHistory = modelMapper.map(rentDto, RentHistory.class);
	  
	    return rentHistory;
	}
	
	private BookReportDTO convertToDto(Book book) {
		BookReportDTO bookReportDTO = modelMapper.map(book, BookReportDTO.class);
	    return bookReportDTO;
	}

}
