package com.company.airline.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.exception.DaoException;

public class CommandSaveCrew implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandSaveCrew.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {

		Long flightId = Long.parseLong(request.getParameter("flightId"));
		String[] userIdString = request.getParameterValues("userId");
		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();

		if (userIdString == null) {
			flightDao.deleteCrewById(flightId);
			LOGGER.info("delete crew with id - " + flightId);
		} else {
			List<Long> userId = new ArrayList<>();
			for (String idString : userIdString) {
				userId.add(Long.parseLong(idString));
			}
			flightDao.deleteCrewById(flightId);
			LOGGER.info("delete crew with id - " + flightId);
			flightDao.saveCrewById(flightId, userId);
			LOGGER.info("save crew with id - " + flightId + " users: " + userId);
		}
		return "/Airline/flights";
	}
}
