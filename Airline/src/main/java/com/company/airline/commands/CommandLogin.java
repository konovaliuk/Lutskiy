package com.company.airline.commands;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.RoleDao;
import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.User;
import com.company.airline.exception.DaoException;
import com.company.airline.manager.Message;

public class CommandLogin implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandLogin.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserDao userDao = DaoFactoryInstance.getFactory().getUserDao();
		User user = userDao.findUserByEmail(email);

		if (user == null || !user.getPassword().equals(password)) {
			Locale locale = null;
			String language = (String) request.getSession().getAttribute("language");
			if (language != null) {
				locale = new Locale(language);
			} else {
				locale = request.getLocale();
			}
			if (user == null) {
				request.setAttribute("email", email);
				request.setAttribute("error_message", Message.getResource(locale).getObject("message.no_user"));
				LOGGER.info("user entered a nonexistent email - " + email);
				return "/WEB-INF/pages/login.jsp";
			} else if (!user.getPassword().equals(password)) {
				request.setAttribute("email", email);
				request.setAttribute("error_message", Message.getResource(locale).getObject("message.wrong_pass"));
				LOGGER.info("user " + email + " entered wrong password - " + password);
				return "/WEB-INF/pages/login.jsp";
			}
		} else {
			RoleDao roleDao = DaoFactoryInstance.getFactory().getRoleDao();
			user.setRole(roleDao.getRoleByUserId(user.getId()));
			request.getSession().setAttribute("user", user);
			LOGGER.info("user " + email + " entered");
		}
		return "/Airline";
	}
}
