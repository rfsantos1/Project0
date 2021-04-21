package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.mariadb.jdbc.Driver;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException {
		Driver mariaDBDriver = new Driver();
		DriverManager.registerDriver(mariaDBDriver);
		
		String username = System.getenv("db_username");
		String password = System.getenv("db_password");
		
		String url = System.getenv("db_url");
		
		return DriverManager.getConnection(url, username, password);
	}

}
