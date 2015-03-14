package com.baseproject.util.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

public class DatabaseUtils {
	
	private static final String DB_CONNECTION_URL = "jdbc:postgresql://127.0.0.1:5432/baseproject_test";
	private static final String DB_USERNAME = "baseproject";
	private static final String DB_PASSWORD = "baseproject";

	public static void cleanAllTables() {
		Connection connection = null;
		try {
			connection = createConnection();
			String tables = allTablesNames(connection);
			
			if (tables != null && !tables.isEmpty()) {
				PreparedStatement statement = connection.prepareStatement("TRUNCATE " + tables + " CASCADE;");
				try {
					statement.execute();
				} finally {
					statement.close();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void dropAllTables() {
		Connection connection = null;
		try {
			connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(dropAllTablesScript(connection));
			try {
				statement.execute();
			} finally {
				statement.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private static Connection createConnection() throws SQLException {
		return DriverManager.getConnection(DB_CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
	}
	
	private static String dropAllTablesScript(Connection connection) throws SQLException {
		return allLinesOfFirstColumn(connection, "select 'drop table if exists \"' || tablename || '\" cascade;' from pg_tables where schemaname = 'public';", " ");
	}
	
	private static String allTablesNames(Connection connection) throws SQLException {
		return allLinesOfFirstColumn(connection, "select tablename from pg_tables where schemaname = 'public';", ", ");
	}
	
	private static String allLinesOfFirstColumn(Connection connection, String query, String separator) throws SQLException {
		StringJoiner joiner = new StringJoiner(separator);
		PreparedStatement statement = connection.prepareStatement(query);
		
		try {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				joiner.add(resultSet.getString(1));
			}
		} finally {		
			statement.close();
		}
		
		return joiner.toString();
	}
}
