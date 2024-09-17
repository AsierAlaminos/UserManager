package org.example.usermanager.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.example.usermanager.model.ErrorResponse;
import org.example.usermanager.model.Response;
import org.example.usermanager.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/")
public class APIController {

	private DBController dbController;

	public APIController() {
		this.dbController = new DBController();
	}

	@GetMapping("/users/all")
	public ResponseEntity<ArrayList<User>> getAllUsers() {
		try {
			ArrayList<User> users = dbController.getAllUsers();
			if (users.size() == 0) {
				return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (SQLException ex) {
			ArrayList<User> arr = new ArrayList<>();
			arr.add(new User(ex.toString(), "none"));
			return new ResponseEntity<>(arr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<Response> getUser(@RequestParam(name = "id", required = false) String userId, @RequestParam(name = "name", required = false) String name, @RequestParam(name = "mail", required = false) String mail) {
		try {
			if (userId == null && name == null && mail == null) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			User user = dbController.getUser(userId, name, mail);
			if (user.getId() == 0) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> setUser(@RequestBody User user) {
		try {
			if (!user.isComplete()) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(dbController.insertUser(user), HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		try {
			if (!user.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(dbController.updateUser(user, id), HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Response> delUser(@PathVariable("id") int id) {
		try {
			User delUser = dbController.getUserById(id);
			if (!delUser.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(dbController.deleteUser(id), HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
