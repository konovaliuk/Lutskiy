package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.exception.DaoException;

public class CommandUpdateRole implements ICommand {
	private static final Logger LOGGER = Logger.getLogger(CommandUpdateRole.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		Long userId = Long.parseLong(request.getParameter("userId"));
		String roleName = request.getParameter("role");
		UserDao userDao = DaoFactoryInstance.getFactory().getUserDao();
		userDao.updateUserRoleById(userId, roleName);
		LOGGER.info("new role - " + roleName + " userId - " + userId);
		return "/Airline/admin/users";
	}
}
