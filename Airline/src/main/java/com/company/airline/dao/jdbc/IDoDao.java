package com.company.airline.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDoDao {
	public void process(Connection connection) throws SQLException;
}
