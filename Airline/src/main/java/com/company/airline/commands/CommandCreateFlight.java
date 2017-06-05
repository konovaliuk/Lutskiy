package com.company.airline.commands;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.Flight;
import com.company.airline.exception.DaoException;

public class CommandCreateFlight implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandCreateFlight.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		Flight flight = new Flight();
		flight.setDeparture(request.getParameter("departure"));
		flight.setDestination(request.getParameter("destination"));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			flight.setDate(format.parse(request.getParameter("date")));
		} catch (ParseException e) {
			LOGGER.warn("parsing date - " + request.getParameter("date"), e);
			throw new ServletException(e);
		}
		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
		flightDao.createFlight(flight);
		LOGGER.info("flight created: " + flight.toString());
		return "/Airline/flights";
	}
}
