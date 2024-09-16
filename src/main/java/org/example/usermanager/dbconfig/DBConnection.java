package org.example.usermanager.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

	private Connection connection;

	private void createConnection() {
		String url = String.format("jdbc:mysql://localhost:3306/%s", SchemaDB.DB_NAME);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, "root", "");
			System.out.println("[*] Conecction success");
		} catch (SQLException | ClassNotFoundException throwables) {
			System.out.println("[!] Connection failed");
		}
	}

	public Connection getConnection() {
		if (connection == null)
			createConnection();
		return connection;
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}
	
}
