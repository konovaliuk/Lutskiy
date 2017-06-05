package com.company.airline.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.airline.entity.User;

@WebFilter("/dispatcher/*")
public class DispatcherFilter extends AbstractFilter {
	private static final Logger LOGGER = Logger.getLogger(DispatcherFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User user = (User) ((HttpServletRequest) request).getSession().getAttribute("user");
		
		if (user == null || !user.getRole().getRoleName().equals("dispatcher")) {
			LOGGER.info("attempt to visit dispatcher page without permission");
			((HttpServletResponse) response).sendRedirect("/Airline");
		} else {
			chain.doFilter(request, response);
		}	
	}

}
