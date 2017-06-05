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

import com.company.airline.dao.RoleDao;
import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.entity.Role;
import com.company.airline.entity.User;
import com.company.airline.entity.UserBuilder;
import com.company.airline.exception.DaoException;

public class UserJdbcDao implements UserDao {
	private static UserJdbcDao userJdbcDao = new UserJdbcDao();
	private static final Logger LOGGER = Logger.getLogger(UserJdbcDao.class);
	
	private static final String INSERT = "insert into user (first_name, last_name, email, password, role) values (?,?,?,?,?)";
	private static final String SELECT_BY_EMAIL = "select * from user where email = '";
	private static final String SELECT_FOR_CREW_EXCEPT_DATE1 = 
		"select user.id as user_id, user.first_name, user.last_name, user.email, user.password, role.id as role_id, role_name " +
		"from user, role where user.role in (select role.id from role where role_name in ('pilot', 'navigator', 'radioman', 'stewardess'))" +
		"and user.id not in (select user_id from flight_user where flight_id in (select id from flight where flight_date = '";
	private static final String SELECT_FOR_CREW_EXCEPT_DATE2 = "'))and user.role = role.id order by user.first_name";
	private static final String SELECT_BY_FLIGHT_ID1 = 
		"select user.id as user_id, first_name, last_name, email, password, role.id as role_id, role_name from user, role where " +
		"user.id in (select user_id from flight_user where flight_id = ";
	private static final String SELECT_BY_FLIGHT_ID2 = ") and role = role.id order by role.id";
	private static final String SELECT_ALL_USERS_EXCEPT_ADMIN = 
		"select user.id as user_id, first_name, last_name, email, password, role.id as role_id, role_name from user, role where " + 
		"user.role != (select role.id from role where role_name = 'admin')  and user.role = role.id";
	private static final String SELECT_BY_ID = 
		"select user.id as user_id, first_name, last_name, email, password, role.id as role_id, role_name " + 
		"from user, role where user.role = role.id and user.id = ";
	private static final String UPDATE_ROLE_BY_ROLE_NAME = "update user set role = (select id from role where role_name = ?) where id = ?";
	
	private UserJdbcDao() {

	}

	public static UserJdbcDao getUserJdbcDao() {
		return userJdbcDao;
	}
	
	/**
	 * @return created user with auto-generated id
	 */
	public User createUser(UserBuilder builder) throws DaoException {
		LOGGER.info("method createUser started");
		User user = null;
		try (Connection connection = DBHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			RoleDao roleDao = DaoFactoryInstance.getFactory().getRoleDao();
			Role role = roleDao.getRoleByName(builder.getRole());			//getting role object using roleDao

			statement.setString(1, builder.getFirstName());
			statement.setString(2, builder.getLastName());
			statement.setString(3, builder.getEmail());
			statement.setString(4, builder.getPassword());
			statement.setInt(5, role.getId());

			if (statement.executeUpdate() == 0) {
				throw new SQLException("Creating user failed, no rows affected");
			}

			ResultSet generatedKeys = statement.getGeneratedKeys();			//getting auto-generated id for user
			long id;
			if (generatedKeys.next()) {
				id = generatedKeys.getLong(1);
			} else {
				throw new SQLException("Id is missing");
			}
			user = new User(id, builder.getFirstName(), builder.getLastName(), builder.getEmail(),
					builder.getPassword(), role);
			LOGGER.info("user created - " + user);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while inserting user", e);
		}
		return user;
	}

	/**
	 * @return user WITHOUT role (role = null) or null if user with given email is not exist
	 */
	@Override
	public User findUserByEmail(String email) throws DaoException {
		LOGGER.info("method findUserByEmail started, email - " + email);
		User user = null;
		try (Connection connection = DBHelper.getConnection(); Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_EMAIL + email + "'");
			if (rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("password"), null);
			}
			LOGGER.info("user was found - " + user);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while finding user by email", e);
		}
		return user;
	}

	/**
	 * Users for crew: pilot, navigator, radioman, stewardess
	 * @return users or empty List if users with given conditions are not exist
	 */
	@Override
	public List<User> findUsersForCrewExceptDate(Date date) throws DaoException {
		LOGGER.info("method findUsersForCrewExceptDate started, date - " + new java.sql.Date(date.getTime()));
		List<User> users = new ArrayList<User>();
		try (Connection connection = DBHelper.getConnection(); Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(
					SELECT_FOR_CREW_EXCEPT_DATE1 + new Timestamp(date.getTime()) + SELECT_FOR_CREW_EXCEPT_DATE2);
			while (rs.next()) {
				/*creating users with role from ResultSet*/
				users.add(new User(rs.getLong("user_id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("password"),
						new Role(rs.getInt("role_id"), rs.getString("role_name"))));	
			}															
			LOGGER.info("users were found - " + users);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while finding users for crew", e);
		}
		return users;
	}

	/**
	 * @return users or empty List if users with given conditions are not exist
	 */
	@Override
	public List<User> findUsersByFlightId(Long id) throws DaoException {
		LOGGER.info("method findUsersByFlightId started, id - " + id);
		List<User> users = new ArrayList<User>();
		try (Connection connection = DBHelper.getConnection(); Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_FLIGHT_ID1 + id + SELECT_BY_FLIGHT_ID2);
			while (rs.next()) {
				/*creating users with role from ResultSet*/
				users.add(new User(rs.getLong("user_id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("password"),
						new Role(rs.getInt("role_id"), rs.getString("role_name"))));
			}
			LOGGER.info("users were found - " + users);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while finding users by flight id", e);
		}
		return users;
	}

	@Override
	public List<User> findAllUsersExceptAdmin() throws DaoException {
		LOGGER.info("method findAllUsersExceptAdmin started");
		List<User> users = new ArrayList<User>();
		try (Connection connection = DBHelper.getConnection(); Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_USERS_EXCEPT_ADMIN);
			while (rs.next()) {
				/*creating users with role from ResultSet*/
				users.add(new User(rs.getLong("user_id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("password"),
						new Role(rs.getInt("role_id"), rs.getString("role_name"))));
			}
			LOGGER.info("users were found - " + users);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while finding users except admin", e);
		}
		return users;
	}

	@Override
	public User getUserById(long id) throws DaoException {
		LOGGER.info("method getUserById started, id - " + id);
		User user = null;
		try (Connection connection = DBHelper.getConnection(); Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_ID + id);
			if (rs.next()) {
				/*creating user with role from ResultSet*/
				user = new User(rs.getLong("user_id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("password"),
						new Role(rs.getInt("role_id"), rs.getString("role_name")));
			} else {
				throw new SQLException("User is missing");
			}
			LOGGER.info("user was found - " + user);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting user by id", e);
		}
		return user;
	}

	@Override
	public void updateUserRoleById(long id, String roleName) throws DaoException {
		LOGGER.info("method updateUserRoleById started, id - " + id + " roleName - " + roleName);
		try (Connection connection = DBHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE_BY_ROLE_NAME)) {
			statement.setString(1, roleName);
			statement.setLong(2, id);
			if (statement.executeUpdate() == 0) {
				throw new SQLException("Updating user role failed, no rows affected");
			}
			LOGGER.info("user role was updated, id - " + id + " roleName - " + roleName);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while updating user role", e);
		}
	}
}
