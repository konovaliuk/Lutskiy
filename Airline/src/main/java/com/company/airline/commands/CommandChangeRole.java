package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.User;
import com.company.airline.exception.DaoException;

public class CommandChangeRole implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandChangeRole.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		Long userId = Long.parseLong(request.getParameter("id"));
		UserDao userDao = DaoFactoryInstance.getFactory().getUserDao();
		User user = userDao.getUserById(userId);
		request.setAttribute("user", user);
		LOGGER.info("useId = " + userId + "parse string - " + request.getParameter("id"));
		return "/WEB-INF/pages/change_role.jsp";
	}

}
