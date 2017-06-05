package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.entity.User;

public class CommandLogout implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandLogout.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("user " + ((User)request.getSession().getAttribute("user")).getEmail() + " initiate logout");
		request.getSession().invalidate();
		return "/Airline";
	}
}
