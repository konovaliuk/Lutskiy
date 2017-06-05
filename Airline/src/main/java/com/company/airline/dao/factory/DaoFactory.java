package com.company.airline.dao.factory;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.RoleDao;
import com.company.airline.dao.UserDao;

public interface DaoFactory {
	public UserDao getUserDao();
	public FlightDao getFlightDao();
	public RoleDao getRoleDao();
}
