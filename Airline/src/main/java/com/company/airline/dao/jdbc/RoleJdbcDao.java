package com.company.airline.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.company.airline.dao.RoleDao;
import com.company.airline.entity.Role;
import com.company.airline.exception.DaoException;

public class RoleJdbcDao implements RoleDao {
	private static RoleJdbcDao roleJdbcDao = new RoleJdbcDao();
	private static final Logger LOGGER = Logger.getLogger(RoleJdbcDao.class);
	
	private static final String SELECT_BY_NAME = "select id from role where role_name = '";
	private static final String SELECT_BY_ID = "select role_name from role where id = ";
	private static final String SELECT_BY_USER_ID = "select * from role where id = (select role from user where id = ";
	
	private RoleJdbcDao(){
		
	}
	
	public static RoleJdbcDao getRoleJdbcDao(){
		return roleJdbcDao;
	}
	
	@Override
	public Role getRoleByName(String roleName) throws DaoException {
		LOGGER.info("method getRoleByName started, roleName - " + roleName);
		Role role = null;
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_NAME + roleName + "'");
			if (rs.next()) {
				role = new Role(rs.getInt(1), roleName);
			} else {
				throw new SQLException("Id is missing");
			}
			LOGGER.info("role was taken - " + role);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting role by name", e);
		}
		return role;
	}
	
	@Override
	public Role getRoleById(int id) throws DaoException {
		LOGGER.info("method getRoleById started, id - " + id);
		Role role = null;
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_ID + id);
			if (rs.next()) {
				role = new Role(id, rs.getString(1));
			} else {
				throw new SQLException("role_name is missing");
			}
			LOGGER.info("role was taken - " + role);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting role by id", e);
		}
		return role;
	}
	
	/**
	 * @return role of user with given id
	 */
	@Override
	public Role getRoleByUserId(long id) throws DaoException {
		LOGGER.info("method getRoleByUserId started, id - " + id);
		Role role = null;
		try (Connection connection = DBHelper.getConnection(); 
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_BY_USER_ID + id + ")");
			if (rs.next()) {
				role = new Role(rs.getInt("id"), rs.getString("role_name"));
			} else {
				throw new SQLException("role is missing");
			}
			LOGGER.info("role was taken - " + role);
		} catch (SQLException e) {
			LOGGER.warn("exception", e);
			throw new DaoException("An exception occurred while getting role by user id", e);
		}
		return role;
	}
}
