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
import com.company.airline.entity.User;
import com.company.airline.exception.DaoException;

public class CommandUserInfo implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/Airline";
		}
		String role = user.getRole().getRoleName();
		if (role.equals("pilot") || role.equals("navigator") || role.equals("radioman") || role.equals("stewardess")) {
			FlightDao flightDao = DaoFactoryInstance.getFactory().getFlightDao();
			List<Flight> flights = flightDao.findFlightsByUserIdFromDate(user.getId(), new Date());
			request.setAttribute("flights", flights);
		}
		return "/WEB-INF/pages/user_info.jsp";
	}
}
