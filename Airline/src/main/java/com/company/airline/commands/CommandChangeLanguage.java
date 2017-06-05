package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.exception.DaoException;

public class CommandChangeLanguage implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandChangeLanguage.class);

	/**
	 * Changes language and returns link of the same page with same parameters
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		String link = request.getParameter("link");
		String parameters = request.getParameter("parameters");
		if (link.equals("")) {
			link = "/Airline";
		}
		if (link.equals("/Airline/login")) {
			link = "/Airline/login_page";
		}
		if (link.equals("/Airline/registration")) {
			link = "/Airline/registration_page";
		}
		link += parameters.equals("") ? "" : ("?" + parameters);	//creating link with parameters for redirecting
		String language = request.getParameter("lang");
		request.getSession().setAttribute("language", language);
		LOGGER.info("link - " + link + "  language - " + language);
		return link;
	}
}
