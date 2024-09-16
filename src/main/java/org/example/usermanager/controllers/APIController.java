package org.example.usermanager.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.example.usermanager.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/")
public class APIController {

	private DBController dbController;

	public APIController() {
		this.dbController = new DBController();
	}

	@GetMapping("/users")
	public ArrayList<User> getAllUsers() {
		try {
			return dbController.getAllUsers();
		} catch (SQLException ex) {
			ArrayList<User> arr = new ArrayList<>();
			arr.add(new User(ex.toString(), "none"));
			return arr;
		}
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") int id) {
		try {
			User user = dbController.getUser(id);
			return user;
		} catch (SQLException ex) {
			return new User(ex.toString(), "none");
		}
	}

	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public User setUser(@RequestBody User user) {
		try {
			return dbController.insertUser(user);
		} catch (SQLException ex) {
			return new User(ex.toString(), "none");
		}
	}

	@PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@PathVariable("id") int id, @RequestBody User newUser) {
		try {
			return dbController.updateUser(newUser, id);
		} catch (SQLException ex) {
			return new User(ex.toString(), "none");
		}
	}

	@DeleteMapping("/users/{id}")
	public User delUser(@PathVariable("id") int id) {
		try {
			return dbController.deleteUser(id);
		} catch (SQLException ex) {
			return new User(ex.toString(), "none");
		}
	}
}
