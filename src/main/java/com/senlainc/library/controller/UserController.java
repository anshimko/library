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

import com.senlainc.library.entity.User;
import com.senlainc.library.service.UserService;

@RestController
@RequestMapping(value = "/users")
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid User user) {
		User newUser = userService.create(user);
		
		return newUser != null ? read(newUser.getId()) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@GetMapping
	public ResponseEntity<List<User>> read() {
		final List<User> users = userService.readAll();

		return users != null && !users.isEmpty() ? new ResponseEntity<>(users, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> read(@PathVariable 
									 @Min(value = 1, message = "id must be greater than or equal to 1") Integer id) {
		
		final User user = userService.read(id);
		

		return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody User user) {
		final User userUpdated = userService.update(user);
		
		return userUpdated != null ? read(userUpdated.getId()) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
		final boolean deleted = userService.delete(id);

		return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

}
