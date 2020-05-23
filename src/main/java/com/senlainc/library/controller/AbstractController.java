package com.senlainc.library.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.senlainc.library.entity.Model;
import com.senlainc.library.exception.EntityException;
import com.senlainc.library.exception.ErrorType;
import com.senlainc.library.service.GenericService;

@Validated
public abstract class AbstractController<E extends Model, S extends GenericService<E>> {

	protected final S service;

	@Autowired
	protected AbstractController(S service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid E entity) {

		return service.save(entity).map(ResponseEntity::ok)
				.orElseThrow(() -> new EntityException(String.format(ErrorType.ENTITY_NOT_SAVED.getDescription(), entity.toString())));
	}

	@PutMapping
	public ResponseEntity<E> update(@RequestBody @Valid E entity) {
		return service.update(entity).map(ResponseEntity::ok)
				.orElseThrow(() -> new EntityException(String.format(ErrorType.ENTITY_NOT_UPDATED.getDescription(), entity.toString())));
	}

	@GetMapping (value = "/{id}")
	public ResponseEntity<E> get(@PathVariable @Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		return service.get(id).map(ResponseEntity::ok)
				.orElseThrow(() -> new EntityException(String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), id)));
	}

	@GetMapping
	public ResponseEntity<List<E>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@DeleteMapping (value = "/{id}")
	public Boolean delete(@PathVariable @Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		return service.deleteById(id);
	}

}
