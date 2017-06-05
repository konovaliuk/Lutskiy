package com.company.airline.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.Flight;
import com.company.airline.entity.User;
import com.company.airline.exception.DaoException;

public class CommandEditCrew implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandEditCrew.class);
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		Long id = Long.parseLong(request.getParameter("id"));
		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
		Flight flight = flightDao.getFlightById(id);
		request.setAttribute("flight", flight);

		UserDao userDao = DaoFactoryInstance.getFactory().getUserDao();
		List<User> crew = userDao.findUsersByFlightId(flight.getId());
		request.setAttribute("crew", crew);

		List<User> userList = userDao.findUsersForCrewExceptDate(flight.getDate());
		request.setAttribute("users", userList);
		LOGGER.info("flightId - " + id);
		return "/WEB-INF/pages/crew.jsp";
	}
}
