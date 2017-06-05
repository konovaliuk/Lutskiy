package com.company.airline.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.User;
import com.company.airline.exception.DaoException;

public class CommandUsers implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException {
		UserDao userDao = DaoFactoryInstance.getFactory().getUserDao();
		List<User> users = userDao.findAllUsersExceptAdmin();
		request.setAttribute("users", users);
		
		return "/WEB-INF/pages/users.jsp";
	}

}
