package com.company.airline.commands;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.Flight;
import com.company.airline.exception.DaoException;

public class CommandFlightsInterval implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandFlightsInterval.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null;
		Date end = null;
		try {
			start = format.parse(request.getParameter("date_start"));
			end = format.parse(request.getParameter("date_end"));
		} catch (NullPointerException e) {
			// it is ok
			return "/Airline/flights";
		} catch (ParseException e) {
			LOGGER.warn("wrogn date format: start - " + request.getParameter("date_start") + " end - "
					+ request.getParameter("date_end"), e);
			throw new ServletException(e);
		}

		FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
		List<Flight> list = flightDao.findFlightsInDateInterval(start, end);
		request.setAttribute("flights", list);
		java.sql.Date sqlStart = new java.sql.Date(start.getTime());
		java.sql.Date sqlEnd = new java.sql.Date(end.getTime());
		request.setAttribute("start", sqlStart);
		request.setAttribute("end", sqlEnd);
		LOGGER.info("date interval " + sqlStart + " - " + sqlEnd);
		return "/WEB-INF/pages/flights.jsp";
	}
}
