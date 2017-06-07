package com.company.airline.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.company.airline.entity.Flight;
import com.company.airline.exception.DaoException;

public interface FlightDao {
	
	/**
	 * @return flights or empty List if flights with given conditions are not exist
	 */
	public List<Flight> findFlightsFromDate(Date date) throws DaoException;
	
	/**
	 * @return flights or empty List if flights with given conditions are not exist
	 */
	public List<Flight> findFlightsInDateInterval(Date start, Date end) throws DaoException;
	
	public Flight getFlightById(Long id) throws DaoException;
	
	public void createFlight(Flight flight) throws DaoException;
	
	public void updateFlight(Flight flight) throws DaoException;
	
	public void deleteFlightById(Connection connection, Long id) throws DaoException;
	
	public void deleteCrewById(Connection connection, Long id) throws DaoException;
	
	public void saveCrewById(Connection connection, Long flightId, List<Long> userId) throws DaoException;
	
	/**
	 * @return flights with given userId or empty List if flights with given conditions are not exist
	 */
	public List<Flight> findFlightsByUserIdFromDate(long id, Date date) throws DaoException;
}
