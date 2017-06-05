package com.company.airline.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.airline.exception.DaoException;

public interface ICommand {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DaoException;
}
