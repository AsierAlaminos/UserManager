package org.example.usermanager.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.usermanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.usermanager.model.ErrorResponse;
import org.example.usermanager.model.Response;
import org.example.usermanager.model.User;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ResponseEntity<ArrayList<User>> getAllUsers() {
		try {
			ArrayList<User> users = userRepository.getAllUsers();
			if (users.size() == 0) {
				return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ArrayList<User>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Response> getUser(String name, String mail) {
		try {
			if (name == null && mail == null) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			User user = userRepository.getUser(name, mail);
			if (user.getId() == 0) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Response> getUserById(int id) {
		try {
			if (id <= 0) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			User user = userRepository.getUserById(id);
			if (user.getId() == 0) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Response> setUser(User user) {
		try {
			if (!user.isComplete()) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(userRepository.insertUser(user), HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Response> updateUser(int id, User user) {
		try {
			if (!user.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(userRepository.updateUser(user, id), HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Response> delUser(int id) {
		try {
			User delUser = userRepository.getUserById(id);
			if (!delUser.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(userRepository.deleteUser(id), HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
