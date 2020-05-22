package com.senlainc.library.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.Catalog;
import com.senlainc.library.service.CatalogService;

@RestController
@RequestMapping(value = "/catalogs")
@Validated
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid Catalog catalog) {
		
		Catalog newCatalog = catalogService.create(catalog);
		return newCatalog != null ? read(newCatalog.getId()) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Catalog> read(@PathVariable 
									 @Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		
		final Catalog catalog = catalogService.read(id);
		

		return catalog != null ? new ResponseEntity<>(catalog, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		final boolean deleted = catalogService.delete(id);

		return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}
	
	@PostMapping(value = "/{catalogId}/books/{bookId}")
	public ResponseEntity<?> addBookInCatalog(@PathVariable(name = "catalogId") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer catalogId,
									@PathVariable(name = "bookId") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer bookId) {
		
		catalogService.addBookInCatalog(catalogId, bookId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
