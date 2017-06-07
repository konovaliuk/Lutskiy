package com.company.airline.commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.dao.jdbc.IDoDao;
import com.company.airline.dao.jdbc.TransactionManager;
import com.company.airline.exception.DaoException;

public class CommandDeleteFlight implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandDeleteFlight.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		Long id = Long.parseLong(request.getParameter("id"));
		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
		
		TransactionManager.doInTransaction(new IDoDao() {
			public void process(Connection connection) throws SQLException {
				try {
					flightDao.deleteCrewById(connection, id);
					flightDao.deleteFlightById(connection, id);
					
				} catch (DaoException e) {
					LOGGER.warn("exception", e);
				}
			}
		});
		LOGGER.info("deleted crew and flight with id - " + id);
		return "/Airline/flights";
	}
}
