package com.company.airline.dao.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBHelper {

	private static DataSource dataSourse = getDataSourse();

	private DBHelper() {
	}

	private static DataSource getDataSourse() {
		DataSource dataSourse = null;
		try {
			dataSourse = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/airline");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSourse;
	}

	public static Connection getConnection() throws SQLException {
		return dataSourse.getConnection();
	}
}
