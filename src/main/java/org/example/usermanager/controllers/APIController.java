package org.example.usermanager.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.example.usermanager.model.ErrorResponse;
import org.example.usermanager.model.Response;
import org.example.usermanager.model.User;
import org.example.usermanager.services.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/")
public class APIController {

	private UserService userService;

	@Autowired
	public APIController(UserService userService) {
		this.userService = userService;
	}

	//TODO: Implementar token de admin para ver contraseñas

	@GetMapping("/users/all")
	public ResponseEntity<ArrayList<User>> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/users")
	public ResponseEntity<Response> getUser(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "mail", required = false) String mail) {
		return userService.getUser(name, mail);
	}


	@GetMapping("/users/{id}")
	public ResponseEntity<Response> getUser(@PathVariable("id") int id) {
		return userService.getUserById(id);
	}

	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> setUser(@RequestBody User user) {
		return userService.setUser(user);
	}

	@PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Response> delUser(@PathVariable("id") int id) {
		return userService.delUser(id);
	}
}
