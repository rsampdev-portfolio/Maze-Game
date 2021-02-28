package com.rsampdev.game.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost";

	static final String USER = "root";
	static final String PASS = "root";
	
	

	public DatabaseConnection() {
		super();
	}

	public static void write(String username, String pass) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			String sql = "use GameDatabase;";

			stmt.executeUpdate(sql);

			sql = "insert into GameUsers (Username, Password) values ('" + username + "', '" + pass + "');";

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.getMessage();
			se.getErrorCode();
			se.getSQLState();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public static boolean verify(String username, String password) {
		Connection conn = null;
		Statement stmt = null;
		boolean legit = false;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "use GameDatabase;";

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			sql = "select * from GameUsers where Username = '" + username + "' and Password = '" + password + "';";

			ResultSet resultSet = stmt.executeQuery(sql);

			ResultSetMetaData metadata = resultSet.getMetaData();
			int numberOfColumns = metadata.getColumnCount();

			ArrayList<String> arrayList = new ArrayList<String>();
			
			while (resultSet.next()) {
				int i = 1;
				while (i <= numberOfColumns) {
					arrayList.add(resultSet.getString(i++));
				}
			}

			if (arrayList.contains(username) && arrayList.contains(password)) {
				legit = true;
			}

		} catch (SQLException se) {
			se.getMessage();
			se.getErrorCode();
			se.getSQLState();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return legit;
	}
	
	public static List<String> getData() {
		Connection conn = null;
		Statement stmt = null;
		List<String> data = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "use GameDatabase;";

			stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			sql = "select * from GameUsers;";

			ResultSet resultSet = stmt.executeQuery(sql);

			ResultSetMetaData metadata = resultSet.getMetaData();
			int numberOfColumns = metadata.getColumnCount();

			while (resultSet.next()) {
				int i = 1;
				while (i <= numberOfColumns) {
					data.add(resultSet.getString(i++));
				}
			}

		} catch (SQLException se) {
			se.getMessage();
			se.getErrorCode();
			se.getSQLState();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return data;
	}
}