
package org.example.usermanager.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.example.usermanager.dbconfig.DBConnection;
import org.example.usermanager.dbconfig.SchemaDB;
import org.example.usermanager.model.User;

public class DBController {

	private DBConnection dbConnection;
	private Connection connection;

	public DBController() {
		this.dbConnection = new DBConnection();
		this.connection = dbConnection.getConnection();
	}

	public ArrayList<User> getAllUsers() throws SQLException {
		try {
			ArrayList<User> users = new ArrayList<>();
			String query = "SELECT * FROM users";
			Statement statement = this.connection.createStatement();

			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				users.add(new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("mail")));
			}
			return users;
		} catch (SQLException throwables) {
			throw throwables;
		}

	}

	public User getUser(int id) throws SQLException {
		
		try {
			String query = String.format("SELECT * FROM users WHERE id=%d", id);
			Statement statement = this.connection.createStatement();

			ResultSet resultSet = statement.executeQuery(query);
			User user = new User();
			if (resultSet.next()){
				user = new User(id, resultSet.getString("name"), resultSet.getString("mail"));
			}
			return user;
		} catch (SQLException throwables) {
			throw throwables;
		}
	}
	
	public User insertUser(User user) throws SQLException {
		try {
			String query = String.format("INSERT INTO %s (%s, %s, %s) VALUES ('%s', '%s', '%s');",
					SchemaDB.USER_TABLE, SchemaDB.COL_NAME, SchemaDB.COL_MAIL, SchemaDB.COL_PASSWD,
					user.getName(), user.getMail(), user.getPasswd());

			Statement statement = this.connection.createStatement();
			statement.executeUpdate(query);
			return user;
		} catch (SQLException throwables) {
			throw throwables;
		}
	}

	public User updateUser(User user, int idUser) throws SQLException {
		try {
			User userOutdated = getUser(idUser);
			if (user.getName() != userOutdated.getName()) {
				update(SchemaDB.COL_NAME, user.getName(), idUser);
			}
			if (user.getMail() != userOutdated.getMail()) {
				update(SchemaDB.COL_MAIL, user.getMail(), idUser);
			}
			if (user.getPasswd() != userOutdated.getPasswd()) {
				update(SchemaDB.COL_PASSWD, user.getPasswd(), idUser);
			}
			return user;
		} catch (SQLException throwables) {
			throw throwables;
		}
	}

	private int update(String column, String value, int idUser) throws SQLException {
		try {
			String query = String.format("UPDATE %s set %s = '%s' WHERE %s = '%s';", SchemaDB.USER_TABLE, column, value, SchemaDB.COL_ID, idUser);
			Statement statement = this.connection.createStatement();
			return statement.executeUpdate(query);
		} catch (SQLException throwables) {
			throw throwables;
		}
	}

	public User deleteUser(int idUser) throws SQLException {
		try {
			User userDeleted = getUser(idUser);
			String query = String.format("DELETE FROM %s WHERE %s = %d;", SchemaDB.USER_TABLE, SchemaDB.COL_ID, idUser);
			Statement statement = this.connection.createStatement();
			statement.execute(query);
			return userDeleted;
		} catch (SQLException throwables) {
			throw throwables;
		}

	}
}
