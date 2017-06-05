package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CommandMissing implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandMissing.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("missing command - " + request.getServletPath());
		return "/Airline";
	}
}
