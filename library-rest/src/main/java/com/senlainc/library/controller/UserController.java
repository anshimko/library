package com.senlainc.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senlainc.library.entity.User;
import com.senlainc.library.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController extends AbstractController<User, UserService>{

	protected UserController(UserService service) {
		super(service);
	}

}
