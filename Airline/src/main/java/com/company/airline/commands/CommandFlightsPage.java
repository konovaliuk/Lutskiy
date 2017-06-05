package com.company.airline.commands;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.Flight;
import com.company.airline.exception.DaoException;

public class CommandFlightsPage implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
		List<Flight> list = flightDao.findFlightsFromDate(new Date());
		request.setAttribute("flights", list);
		return "/WEB-INF/pages/flights.jsp";
	}
}
