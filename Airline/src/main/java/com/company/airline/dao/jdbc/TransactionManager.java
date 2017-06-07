package com.company.airline.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class TransactionManager {
	private static final Logger LOGGER = Logger.getLogger(TransactionManager.class);
	
	public static void doInTransaction(IDoDao instance) {
		try (Connection connection = DBHelper.getConnection()){
			connection.setAutoCommit(false);
			try {
				instance.process(connection);
				connection.commit();
			} catch (SQLException e){
				connection.rollback();
				LOGGER.warn("exception", e);
			}
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
		}
				
	}

}
