package com.company.airline.dao.factory;

import com.company.airline.dao.FlightDao;
import com.company.airline.dao.RoleDao;
import com.company.airline.dao.UserDao;
import com.company.airline.dao.jdbc.FlightJdbcDao;
import com.company.airline.dao.jdbc.RoleJdbcDao;
import com.company.airline.dao.jdbc.UserJdbcDao;

public class JdbcDaoFactory implements DaoFactory {
	private static JdbcDaoFactory factory = new JdbcDaoFactory();
	
	private UserJdbcDao userJdbcDao = UserJdbcDao.getUserJdbcDao();
	private FlightJdbcDao flightJdbcDao = FlightJdbcDao.getFlightJdbcDao();
	private RoleJdbcDao roleJdbcDao = RoleJdbcDao.getRoleJdbcDao();
	
	private JdbcDaoFactory(){
	}
	
	public static JdbcDaoFactory getFactory(){
		return factory;
	}

	@Override
	public UserDao getUserDao() {
		return userJdbcDao;
	}

	@Override
	public FlightDao getFlightDao() {
		return flightJdbcDao;
	}
	
	@Override
	public RoleDao getRoleDao(){
		return roleJdbcDao;
	}

}
