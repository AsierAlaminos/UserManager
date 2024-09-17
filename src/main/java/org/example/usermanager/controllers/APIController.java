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

	@GetMapping("/users")
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
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Response> getUser(@PathVariable("id") int id) {
		try {
			User user = dbController.getUser(id);
			if (user.getId() == 0) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, "User not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> setUser(@RequestBody User user) {
		try {
			if (!checkCompleteUser(user)) {
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
			if (!checkEmptyUser(user)) {
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
			User delUser = dbController.getUser(id);
			if (!checkEmptyUser(delUser)) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(dbController.deleteUser(id), HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean checkCompleteUser(User user) {
		if (user.getName() == null || user.getMail() == null || user.getPasswd() == null)
			return false;
		return true;
	}

	private boolean checkEmptyUser(User user) {
		if (user.getName() == null && user.getMail() == null && user.getPasswd() == null)
			return false;
		return true;
	}
}
