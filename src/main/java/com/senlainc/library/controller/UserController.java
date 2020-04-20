package com.senlainc.library.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.User;
import com.senlainc.library.service.UserService;

@RestController
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/users")
	public ResponseEntity<?> create(@RequestBody User User) {
		userService.create(User);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> read() {
		final List<User> Users = userService.readAll();

		return Users != null && !Users.isEmpty() ? new ResponseEntity<>(Users, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> read(@PathVariable 
									 @Min(value = 1, message = "id must be greater than or equal to 1") int id) {
		
		final User user = userService.read(id);
		

		return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/users/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User User) {
		final boolean updated = userService.update(User, id);

		return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
		final boolean deleted = userService.delete(id);

		return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

}
