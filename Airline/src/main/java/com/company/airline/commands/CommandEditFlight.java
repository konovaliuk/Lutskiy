package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.Flight;
import com.company.airline.exception.DaoException;

public class CommandEditFlight implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandEditFlight.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		Long id = Long.parseLong(request.getParameter("id"));
		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
		Flight flight = flightDao.getFlightById(id);
		request.setAttribute("flight", flight);
		LOGGER.info("edit command with id - " + id);
		return "/WEB-INF/pages/flight_util.jsp";
	}
}
