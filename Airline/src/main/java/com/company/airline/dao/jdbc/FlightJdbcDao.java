package com.company.airline.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.company.airline.dao.FlightDao;
import com.company.airline.entity.Flight;
import com.company.airline.exception.DaoException;

public class FlightJdbcDao implements FlightDao {
	private static FlightJdbcDao flightJdbcDao = new FlightJdbcDao();
	private static final Logger LOGGER = Logger.getLogger(FlightJdbcDao.class);

	private static final String SELECT_FROM_DATE = "select * from flight where flight_date > '";
	private static final String ORDER_BY_DATE = "' order by flight_date";
	private static final String INSERT = "insert into flight (departure, destination, flight_date) values (?,?,?)";
	private static final String SELECT_BY_ID = "select * from flight where id = ";
	private static final String DELETE_BY_ID = "delete from flight where id = ";
	private static final String UPDATE_BY_ID = "update flight set departure = ?, destination = ?, flight_date = ? where id = ?";
	private static final String SELECT_IN_DATE_INTERVAL = "select * from flight where flight_date between '";
	private static final String DELETE_CREW_BY_ID = "delete from flight_user where flight_id = ";
	private static final String INSERT_CREW_BY_ID = "insert into flight_user (flight_id, user_id) values ";
	private static final String SELECT_BY_USER_ID_FROM_DATE1 = "select * from flight where id in "
			+ "(select flight_id from flight_user where user_id = ";
	private static final String SELECT_BY_USER_ID_FROM_DATE2 = ") and flight_date >= '";

	private FlightJdbcDao() {
	}

	public static FlightJdbcDao getFlightJdbcDao() {
		return flightJdbcDao;
	}

	@Override
	public void createFlight(Flight flight) throws DaoException {
		LOGGER.info("method createFlight started");
		try (Connection connection = DBHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT)) {
			statement.setString(1, flight.getDeparture());
			statement.setString(2, flight.getDestination());
			statement.setTimestamp(3, new Timestamp(flight.getDate().getTime()));
			if (statement.executeUpdate() == 0) {
				throw new SQLException("Creating flight failed, no rows affected");
			}
			LOGGER.info("flight " + flight + " created");
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while inserting flight", e);
		}
	}

	@Override
	public void updateFlight(Flight flight) throws DaoException {
		LOGGER.info("method updateFlight started");
		try (Connection connection = DBHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
			statement.setString(1, flight.getDeparture());
			statement.setString(2, flight.getDestination());
			statement.setTimestamp(3, new Timestamp(flight.getDate().getTime()));
			statement.setLong(4, flight.getId());
			if (statement.executeUpdate() == 0) {
				throw new SQLException("Updating flight failed, no rows affected");
			}
			LOGGER.info("flight " + flight + " updated");
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while updating flight", e);
		}
	}

	@Override
	public Flight getFlightById(Long id) throws DaoException {
		LOGGER.info("method getFlightById started, id - " + id);
		Flight flight = null;
		try (Connection connection = DBHelper.getConnection();
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_ID + id);
			if (rs.next()) {
				flight = new Flight(rs.getLong("id"), rs.getString("departure"), rs.getString("destination"),
						rs.getDate("flight_date"));
			} else {
				throw new SQLException("Flight is missing");
			}
			LOGGER.info("flight " + flight + " was taken");
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting flight", e);
		}
		return flight;
	}

	@Override
	public List<Flight> findFlightsFromDate(Date date) throws DaoException {
		LOGGER.info("method findFlightsFromDate started, date - " + new java.sql.Date(date.getTime()));
		List<Flight> flights = new ArrayList<Flight>();
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_FROM_DATE + new Timestamp(date.getTime()) + ORDER_BY_DATE);
			while (rs.next()) {
				flights.add(new Flight(rs.getLong("id"), rs.getString("departure"), rs.getString("destination"),
						rs.getDate("flight_date")));
			}
			LOGGER.info("flight were found " + flights);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting flights from date", e);
		}
		return flights;
	}

	@Override
	public void deleteFlightById(Long id) throws DaoException {
		LOGGER.info("method deleteFlightById started");
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			if (statement.executeUpdate(DELETE_BY_ID + id) == 0) {
				throw new SQLException("Deleting flight failed, no rows affected");
			}
			LOGGER.info("flight was deleted, id - " + id);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while deleting flight", e);
		}
	}
	
	/**
	 * @return flights or empty List if flights with given conditions are not exist
	 */
	@Override
	public List<Flight> findFlightsInDateInterval(Date start, Date end) throws DaoException {
		LOGGER.info("method findFlightsInDateInterval started");
		List<Flight> flights = new ArrayList<Flight>();
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_IN_DATE_INTERVAL + new Timestamp(start.getTime()) + "' and '"
					+ new Timestamp(end.getTime()) + ORDER_BY_DATE);
			while (rs.next()) {
				flights.add(new Flight(rs.getLong("id"), rs.getString("departure"), rs.getString("destination"),
						rs.getDate("flight_date")));
			}
			LOGGER.info("flight were found " + flights);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting flights in date interval", e);
		}
		return flights;
	}

	@Override
	public void deleteCrewById(Long id) throws DaoException {
		LOGGER.info("method deleteCrewById started, id - " + id);
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			statement.executeUpdate(DELETE_CREW_BY_ID + id);
			LOGGER.info("crew deleted, id - " + id);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while deleting crew", e);
		}
	}

	@Override
	public void saveCrewById(Long flightId, List<Long> userId) throws DaoException {
		LOGGER.info("method saveCrewById started, flightId - " + flightId + "users id - " + userId);
		if (userId.size() == 0) {
			return;
		}
		StringBuilder query = new StringBuilder(INSERT_CREW_BY_ID);
		for (Long id : userId) {											// creating query
			query.append("(").append(flightId).append(",").append(id).append(")").append(",");
		}
		query.deleteCharAt(query.length() - 1);  							// deleting last comma
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			statement.executeUpdate(new String(query));
			LOGGER.info("crew was saved flightId - " + flightId + "users id - " + userId);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while saving crew", e);
		}
	}
	
	/**
	 * @return flights with given userId or empty List if flights with given conditions are not exist
	 */
	@Override
	public List<Flight> findFlightsByUserIdFromDate(long id, Date date) throws DaoException {
		LOGGER.info("method findFlightsByUserIdFromDate started, userId - " + id + " date - "
				+ new java.sql.Date(date.getTime()));
		List<Flight> flights = new ArrayList<Flight>();
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_USER_ID_FROM_DATE1 + id + SELECT_BY_USER_ID_FROM_DATE2
					+ new Timestamp(date.getTime()) + ORDER_BY_DATE);
			while (rs.next()) {
				flights.add(new Flight(rs.getLong("id"), rs.getString("departure"), rs.getString("destination"),
						rs.getDate("flight_date")));
			}
			LOGGER.info("flight were found " + flights);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting flights by user id", e);
		}
		return flights;
	}
}
