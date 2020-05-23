package com.senlainc.library.controller;

import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.Catalog;
import com.senlainc.library.service.CatalogService;

@RestController
@RequestMapping(value = "/catalogs")
public class CatalogController extends AbstractController<Catalog, CatalogService>{

	protected CatalogController(CatalogService service) {
		super(service);
	}
	
	@PostMapping(value = "/{catalogId}/books/{bookId}")
	public ResponseEntity<?> addBookInCatalog(@PathVariable(name = "catalogId") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer catalogId,
									@PathVariable(name = "bookId") 
									@Min(value = 1, message = "id must be greater than or equal to 1") Integer bookId) {
		
		service.addBookInCatalog(catalogId, bookId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
