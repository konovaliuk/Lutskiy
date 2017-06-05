package com.company.airline.commands;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.User;
import com.company.airline.entity.UserBuilder;
import com.company.airline.exception.DaoException;
import com.company.airline.manager.Message;

public class CommandRegistration implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandRegistration.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("password_confirm");

		Locale locale = null;
		String language = (String) request.getSession().getAttribute("language");
		if (language != null) {
			locale = new Locale(language);
		} else {
			locale = request.getLocale();
		}

		if (!password.equals(passwordConfirm)) {
			setUserAttributes(request, firstName, lastName, email);
			request.setAttribute("error_message", Message.getResource(locale).getObject("message.different_pass"));
			LOGGER.info("different passwords 1 - " + password + " 2 - " + passwordConfirm);
			return "/WEB-INF/pages/registration.jsp";
		}

		UserDao userDao = DaoFactoryInstance.getFactory().getUserDao();
		User user = userDao.findUserByEmail(email);
		if (user != null) {
			setUserAttributes(request, firstName, lastName, email);
			request.setAttribute("error_message", Message.getResource(locale).getObject("message.email_in_use"));
			LOGGER.info("entered email is already exist - " + email);
			return "/WEB-INF/pages/registration.jsp";
		}
		user = new UserBuilder().setFirstName(firstName).setLastName(lastName).setEmail(email).setPassword(password)
				.setRole("user").create();
		request.getSession().setAttribute("user", user);
		LOGGER.info("new user " + email + " created");
		return "/Airline";
	}

	private static void setUserAttributes(HttpServletRequest request, String firstName, String lastName, String email) {
		request.setAttribute("first_name", firstName);
		request.setAttribute("last_name", lastName);
		request.setAttribute("email", email);
	}
}
