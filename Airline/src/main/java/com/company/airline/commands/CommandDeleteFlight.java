package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.exception.DaoException;

public class CommandDeleteFlight implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandDeleteFlight.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		Long id = Long.parseLong(request.getParameter("id"));
		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
		flightDao.deleteCrewById(id);
		flightDao.deleteFlightById(id);
		LOGGER.info("deleted crew and flight with id - " + id);
		return "/Airline/flights";
	}
}
